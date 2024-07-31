package com.mefrreex.chatsplitter.listener;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerChatEvent;
import cn.nukkit.utils.Config;
import com.mefrreex.chatsplitter.ChatSplitter;
import com.mefrreex.chatsplitter.utils.Language;

public class ChatListener implements Listener {

    private final ChatSplitter main;
    private final Config config;

    public ChatListener(ChatSplitter plugin) {
        this.main = plugin;
        this.config = plugin.getConfig();
    }

    @EventHandler(ignoreCancelled = true)
    public void onChat(PlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();
        
        // Global chat
        if (message.substring(0, 1).equals(config.getString("symbol"))) {

            // Global chat permission check
            if (!player.hasPermission("splitchat.chat.global") && config.getBoolean("enable-permissions")) {
                player.sendMessage(Language.get("generic-no-permission-global"));
                event.setCancelled();
                return;
            }

            for (Player players : main.getServer().getOnlinePlayers().values()) {

                String format = event.getFormat()
                    .replaceFirst(config.getString("symbol"), "")
                    .replace("{splitchat_prefix}", config.getString("prefix.global"))
                    .replace("{splitchat_distance}", String.valueOf((int) players.distance(player)));

                players.sendMessage(format);
                main.getServer().getLogger().info(format);
            }
            event.setCancelled();

        // Local chat
        } else {

            // Local chat permission check
            if (!player.hasPermission("splitchat.chat.local") && config.getBoolean("enable-permissions")) {
                player.sendMessage(Language.get("generic-no-permission-local"));
                event.setCancelled();
                return;
            }

            for (Player players : main.getServer().getOnlinePlayers().values()) {
                if (player.distance(players) <= config.getInt("radius")) {

                    String format = event.getFormat()
                        .replace("{splitchat_prefix}", config.getString("prefix.local"))
                        .replace("{splitchat_distance}", String.valueOf((int) players.distance(player)));

                    players.sendMessage(format);
                    main.getServer().getLogger().info(format);
                }
            }

            // Sending local messages to spyers
            for (Player spyer : main.getSpyers()) {
                if (player.distance(spyer) >= config.getInt("radius")) {
                    String spyformat = event.getFormat()
                        .replace("{splitchat_prefix}", config.getString("prefix.spy"))
                        .replace("{splitchat_distance}", String.valueOf((int) spyer.distance(player)));
                    spyer.sendMessage(spyformat);
                }
            }
            event.setCancelled();
        }
    }
}
