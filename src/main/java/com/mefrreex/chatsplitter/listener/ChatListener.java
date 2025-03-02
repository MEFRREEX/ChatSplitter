package com.mefrreex.chatsplitter.listener;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerChatEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import com.mefrreex.chatsplitter.ChatSplitter;
import com.mefrreex.chatsplitter.chat.ChatChannelType;
import com.mefrreex.chatsplitter.chat.ChatPermissions;
import com.mefrreex.chatsplitter.chat.ChatPlaceholders;
import com.mefrreex.chatsplitter.service.ChatService;
import com.mefrreex.chatsplitter.utils.language.Language;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ChatListener implements Listener {

    private final ChatService chatService;

    private final Set<Player> globalChatSenders = new HashSet<>();

    public ChatListener(ChatService chatService) {
        this.chatService = chatService;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        globalChatSenders.remove(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onPrePlayerChat(PlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();

        ChatChannelType chatChannel = chatService.getDefaultChannelType(player);

        // Check if the message is global
        if (message.startsWith(chatService.getGlobalChatSymbol()) || chatChannel == ChatChannelType.GLOBAL) {
            if (chatChannel == ChatChannelType.LOCAL) {
                if (message.trim().length() <= 1) {
                    event.setCancelled(true);
                    return;
                }
                event.setMessage(message.substring(1));
            }
            // We must save the player to the collection, because when handling
            // onPostPlayerChat we can't check if the message is global.
            globalChatSenders.add(player);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPostPlayerChat(PlayerChatEvent event) {
        Player player = event.getPlayer();

        // Global chat handling
        if (globalChatSenders.contains(player)) {
            globalChatSenders.remove(player);

            if (!this.testPermission(player, ChatChannelType.GLOBAL)) {
                event.setCancelled(true);
                return;
            }
            event.setFormat(event.getFormat().replace(ChatPlaceholders.PREFIX, chatService.getGlobalChatPrefix()));
            return;
        }

        // Local chat handling
        if (!this.testPermission(player, ChatChannelType.LOCAL)) {
            event.setCancelled();
            return;
        }

        String format = event.getFormat();

        event.setFormat(format.replace(ChatPlaceholders.PREFIX, chatService.getLocalChatPrefix()));

        Set<CommandSender> recipients = event.getRecipients().stream()
                .filter(recipient -> this.isLocalChatAvailable(player, recipient))
                .collect(Collectors.toSet());

        boolean noneMatch = recipients.stream().noneMatch(recipient ->
                !player.equals(recipient) &&
                        this.isLocalChatAvailable(player, recipient) &&
                        !(recipient instanceof ConsoleCommandSender));

        if (chatService.isEnableNoRecipientsMessage() && noneMatch) {
            player.sendMessage(Language.get("generic-no-players", player));
        }

        event.setRecipients(recipients);

        // Send messages to all local chat spies
        for (Player spy : ChatSplitter.CHAT_SPY) {
            if (!recipients.contains(spy)) {
                spy.sendMessage(format
                        .replace(ChatPlaceholders.PREFIX, chatService.getSpyChatPrefix())
                        .replace(ChatPlaceholders.DISTANCE, String.valueOf((int) spy.distance(player))));
            }
        }
    }

    private boolean isLocalChatAvailable(Player sender, CommandSender recipient) {
        if (recipient instanceof Player player) {
            return player.getLevel().getName().equals(sender.getLevel().getName()) &&
                    player.distance(sender) <= chatService.getLocalChatRadius();
        }
        return recipient instanceof ConsoleCommandSender;
    }

    private boolean testPermission(Player player, ChatChannelType chatChannel) {
        if (chatService.isEnablePermissions()) {
            String permission = chatChannel == ChatChannelType.LOCAL ? ChatPermissions.LOCAL : ChatPermissions.GLOBAL;
            if (!player.hasPermission(permission)) {
                player.sendMessage(Language.get("generic-no-permission-" + chatChannel.name().toLowerCase()));
                return false;
            }
        }
        return true;
    }
}