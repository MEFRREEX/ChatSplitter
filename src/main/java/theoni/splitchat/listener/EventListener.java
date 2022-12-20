package theoni.splitchat.listener;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerChatEvent;
import cn.nukkit.utils.Config;
import theoni.splitchat.Main;

public class EventListener implements Listener {

    Main plugin;
    Config config;
    Config mobs;

    public EventListener(Main plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
    }

    @EventHandler
    public void onChat(PlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();
        if (!event.isCancelled()) {
            if (message.substring(0, 1).contains(config.getString("symbol"))) { 
                String format = event.getFormat()
                    .replaceFirst(config.getString("symbol"), "")
                    .replace("%splitchat_prefix%", config.getString("prefix.global"));
                for (Player players : plugin.getServer().getOnlinePlayers().values()) {
                    players.sendMessage(format);
                    plugin.getServer().getLogger().info(format);
                }
                event.setCancelled();
            } else {
                for (Player players : plugin.getServer().getOnlinePlayers().values()) {
                    if (player.distance(players) <= config.getInt("distance")) {
                        String format = event.getFormat()
                            .replace("%splitchat_prefix%", config.getString("prefix.local"));
                        players.sendMessage(format);
                        plugin.getServer().getLogger().info(format);
                    }
                }
                event.setCancelled();
            }
        }
    }
}
