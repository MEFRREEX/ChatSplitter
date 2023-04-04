package theoni.splitchat;

import cn.nukkit.Player;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.plugin.PluginManager;

import theoni.splitchat.listener.*;
import theoni.splitchat.commands.*;

import java.util.ArrayList;
import java.util.List;

public class Main extends PluginBase {

    public List<Player> spyers = new ArrayList<Player>();

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
        PluginManager mgr = this.getServer().getPluginManager();
        if (mgr.getPlugin("LuckChat") != null) {
            this.getServer().getLogger().warning("If you are using LuckChat for the chat format for the plugin to work properly, go to config.yml and change the ChatAsync parameter to false");
        }
    }
}
