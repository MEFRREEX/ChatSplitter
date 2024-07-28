package com.mefrreex.chatsplitter;

import cn.nukkit.Player;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.plugin.PluginManager;
import com.mefrreex.chatsplitter.command.LocalSpyCommand;
import com.mefrreex.chatsplitter.listener.ChatListener;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ChatSplitter extends PluginBase {

    private final List<Player> spyers = new ArrayList<>();

    public void onEnable() {
        this.checkChatPlugin();
        this.saveDefaultConfig();
        this.saveResource("messages.yml");
        this.register();
    }

    private void register() {
        this.getServer().getPluginManager().registerEvents(new ChatListener(this), this);
        this.getServer().getCommandMap().register("SplitChat", new LocalSpyCommand(this));
    }

    private void checkChatPlugin() {
        PluginManager pluginManager = this.getServer().getPluginManager();
        Plugin plugin = pluginManager.getPlugin("LuckChat");
        if (plugin != null && plugin.getConfig().getBoolean("ChatAsync")) {
            throw new RuntimeException("Using LuckChat requires disabling the ChatAsync parameter in config.yml of the LuckChat plugin");
        }
    }
}
