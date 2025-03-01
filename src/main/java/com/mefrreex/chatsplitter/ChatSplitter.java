package com.mefrreex.chatsplitter;

import cn.nukkit.Player;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.plugin.PluginBase;
import com.mefrreex.chatsplitter.command.LocalSpyCommand;
import com.mefrreex.chatsplitter.listener.ChatListener;
import com.mefrreex.chatsplitter.service.ChatService;
import com.mefrreex.chatsplitter.service.ChatServiceImpl;
import com.mefrreex.chatsplitter.utils.Language;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ChatSplitter extends PluginBase {

    @Getter
    private static ChatSplitter instance;
    private final List<Player> spyers = new ArrayList<>();
    private ChatService chatService;

    @Override
    public void onLoad() {
        ChatSplitter.instance = this;
        this.saveDefaultConfig();
    }

    public void onEnable() {
        Language.init(this);
        this.checkChatPlugin();

        this.chatService = new ChatServiceImpl();
        this.chatService.setLocalChatRadius(this.getConfig().getInt("local-chat-radius"));
        this.chatService.setGlobalChatSymbol(this.getConfig().getString("global-chat-symbol"));
        this.chatService.setLocalChatPrefix(this.getConfig().getString("chat-prefix.local"));
        this.chatService.setGlobalChatPrefix(this.getConfig().getString("chat-prefix.global"));
        this.chatService.setSpyChatPrefix(this.getConfig().getString("chat-prefix.spy"));
        this.chatService.setEnableNoRecipientsMessage(this.getConfig().getBoolean("enable-no-recipients-message"));
        this.chatService.setEnablePermissions(this.getConfig().getBoolean("enable-permissions"));

        this.getServer().getPluginManager().registerEvents(new ChatListener(chatService), this);
        this.getServer().getCommandMap().register("ChatSplitter", new LocalSpyCommand(this));
    }

    private void checkChatPlugin() {
        Plugin luckChat = this.getServer().getPluginManager().getPlugin("LuckChat");
        if (luckChat != null && luckChat.getConfig().getBoolean("ChatAsync")) {
            this.getLogger().error("Using LuckChat requires disabling the ChatAsync parameter in config.yml of the LuckChat plugin");
        }
    }
}
