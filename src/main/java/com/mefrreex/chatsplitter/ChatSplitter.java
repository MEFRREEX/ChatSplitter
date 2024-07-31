package com.mefrreex.chatsplitter;

import cn.nukkit.Player;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.plugin.PluginManager;
import com.mefrreex.chatsplitter.command.LocalSpyCommand;
import com.mefrreex.chatsplitter.listener.ChatListener;
import com.mefrreex.chatsplitter.utils.Language;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ChatSplitter extends PluginBase {

    @Getter
    private static ChatSplitter instance;

    private final List<Player> spyers = new ArrayList<>();

    @Override
    public void onLoad() {
        ChatSplitter.instance = this;
        this.saveDefaultConfig();
    }

    public void onEnable() {
        Language.init(this);
        this.checkChatPlugin();
        this.register();
    }

    private void register() {
        this.getServer().getPluginManager().registerEvents(new ChatListener(this), this);
        this.getServer().getCommandMap().register("ChatSplitter", new LocalSpyCommand(this));
    }

    private void checkChatPlugin() {
        PluginManager pluginManager = this.getServer().getPluginManager();
        Plugin plugin = pluginManager.getPlugin("LuckChat");

        if (plugin != null && plugin.getConfig().getBoolean("ChatAsync")) {
            throw new RuntimeException("Using LuckChat requires disabling the ChatAsync parameter in config.yml of the LuckChat plugin");
        }
    }
}
