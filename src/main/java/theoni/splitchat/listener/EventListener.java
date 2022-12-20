package theoni.splitchat.listener;

import java.io.File;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerChatEvent;
import cn.nukkit.utils.Config;
import theoni.splitchat.Main;
import theoni.splitchat.commands.LocalSpyCommand;

public class EventListener implements Listener {

    Main plugin;
    Config config;
    Config messages;

    public EventListener(Main plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
        this.messages = new Config(new File(plugin.getDataFolder() + "/messages.yml"), Config.YAML);
    }

    @EventHandler
    public void onChat(PlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();
        if (!event.isCancelled()) {
            // Глобальный чат
            if (message.substring(0, 1).contains(config.getString("symbol"))) { 
                
                // Пермишен на отправку сообщения в глобальный чат
                if (!player.hasPermission("splitchat.chat.global") && config.getBoolean("enable-permissions")) {
                    player.sendMessage(messages.getString("permission.global"));
                    event.setCancelled();
                    return;
                }

                for (Player players : plugin.getServer().getOnlinePlayers().values()) {
                    String format = event.getFormat()
                        .replaceFirst(config.getString("symbol"), "")
                        .replace("%splitchat_prefix%", config.getString("prefix.global"))
                        .replace("%splitchat_distance%", String.valueOf(players.distance(player)));
                    players.sendMessage(format);
                    plugin.getServer().getLogger().info(format);
                }
                event.setCancelled();
            // Локльный чат
            } else {

                // Пермишен на отправку сообщения в глобальный чат
                if (!player.hasPermission("splitchat.chat.local") && config.getBoolean("enable-permissions")) {
                    player.sendMessage(messages.getString("permission.local"));
                    event.setCancelled();
                    return;
                }

                for (Player players : plugin.getServer().getOnlinePlayers().values()) {
                    if (player.distance(players) <= config.getInt("radius")) {
                        String format = event.getFormat()
                            .replace("%splitchat_prefix%", config.getString("prefix.local"))
                            .replace("%splitchat_distance%", String.valueOf((int) players.distance(player)));
                        players.sendMessage(format);
                        plugin.getServer().getLogger().info(format);
                    }
                    // Отправка локальных сообщений угроку с вклченным /localspy
                }
                if (LocalSpyCommand.spyer.size() > 0) {
                    for (Player spyer : LocalSpyCommand.spyer) {
                        if (player.distance(spyer) >= config.getInt("radius")) {
                            String spyformat = event.getFormat()
                                .replace("%splitchat_prefix%", config.getString("prefix.spy"))
                                .replace("%splitchat_distance%", String.valueOf(spyer.distance(player)));
                            spyer.sendMessage(spyformat);
                        }
                    }
                }
                event.setCancelled();
            }
        }
    }
}
