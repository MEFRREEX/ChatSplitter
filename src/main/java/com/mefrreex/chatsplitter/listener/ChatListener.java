package com.mefrreex.chatsplitter.listener;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerChatEvent;
import com.mefrreex.chatsplitter.chat.ChatChannelType;
import com.mefrreex.chatsplitter.service.ChatService;
import com.mefrreex.chatsplitter.utils.Language;

import java.util.Set;
import java.util.stream.Collectors;

public class ChatListener implements Listener {

    private final ChatService chatService;

    public ChatListener(ChatService chatService) {
        this.chatService = chatService;
    }

    @EventHandler
    public void onPlayerChat(PlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();

        ChatChannelType chatChannel = chatService.getDefaultChannelType(player);

        if (message.startsWith(chatService.getGlobalChatSymbol()) || chatChannel == ChatChannelType.GLOBAL) {
            if (chatChannel == ChatChannelType.LOCAL) {
                if (message.length() <= 1) {
                    event.setCancelled();
                    return;
                }
                event.setMessage(message.substring(1));
            }
            event.setFormat(event.getFormat().replace("{splitchat_prefix}", chatService.getGlobalChatPrefix()));
            return;
        }

        event.setFormat(event.getFormat().replace("{splitchat_prefix}", chatService.getLocalChatPrefix()));

        Set<CommandSender> recipients = event.getRecipients().stream()
                .filter(recipient -> this.isLocalChatAvailable(player, recipient))
                .collect(Collectors.toSet());

        boolean noneMatch = recipients.stream().noneMatch(recipient ->
                !player.equals(recipient) &&
                        this.isLocalChatAvailable(player, recipient) &&
                        !(recipient instanceof ConsoleCommandSender)
        );

        if (chatService.isEnableNoRecipientsMessage() && noneMatch) {
            player.sendMessage(Language.get("generic-no-players", player));
        }

        event.setRecipients(recipients);
    }

    private boolean isLocalChatAvailable(Player sender, CommandSender recipient) {
        if (recipient instanceof Player player) {
            return player.distance(sender) <= chatService.getLocalChatRadius();
        }
        return recipient instanceof ConsoleCommandSender;
    }
}
