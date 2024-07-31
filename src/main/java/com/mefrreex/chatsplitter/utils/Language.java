package com.mefrreex.chatsplitter.utils;

import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;
import com.mefrreex.chatsplitter.ChatSplitter;

import java.util.HashMap;
import java.util.Map;

public class Language {

    private static final Map<String, String> messages = new HashMap<>();

    /**
     * Load language
     */
    public static void init(ChatSplitter main) {
        String language = main.getConfig().getString("language", "eng").toLowerCase();
        main.saveResource("lang/" + language + ".yml");

        Config config = new Config(main.getDataFolder() + "/lang/" + language + ".yml", Config.YAML);
        config.getAll().forEach((key, value) -> {
            if (value instanceof String message) {
                messages.put(key, message);
            }
        });
    }

    /**
     * Get message
     * @param key Message key
     * @param replacements Message parameters
     * @return String message
     */
    public static String get(String key, Object... replacements) {
        String message = TextFormat.colorize(messages.getOrDefault(key, key));

        int i = 0;
        for (Object replacement : replacements) {
            message = message.replace("[" + i + "]", String.valueOf(replacement));
            i++;
        }

        return message;
    }
}
