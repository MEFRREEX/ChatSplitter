package com.mefrreex.chatsplitter.utils.language.adapter.impl;

import cn.nukkit.command.CommandSender;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;
import com.mefrreex.chatsplitter.utils.language.adapter.TranslationAdapter;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class TranslationAdapterYaml implements TranslationAdapter {

    private final Map<String, String> messages = new HashMap<>();

    public TranslationAdapterYaml(Plugin plugin) {
        String language = plugin.getConfig().getString("language", "eng").toLowerCase();
        plugin.saveResource("lang/" + language + ".yml");

        File file = new File(plugin.getDataFolder() + "/lang/" + language + ".yml");
        if (!file.exists()) {
            throw new RuntimeException("Localization file for language " + language + " not found");
        }

        Config config = new Config(file, Config.YAML);
        config.getAll().forEach((key, value) -> {
            if (value instanceof String message) {
                messages.put(key, message);
            }
        });
    }

    @Override
    public String translate(String key, Object... replacements) {
        return this.translate(key, (String) null, replacements);
    }

    @Override
    public String translate(String key, CommandSender sender, Object... replacements) {
        return this.translate(key, (String) null, replacements);
    }

    @Override
    public String translate(String key, String language, Object... replacements) {
        String message = TextFormat.colorize(messages.getOrDefault(key, key));

        int i = 0;
        for (Object replacement : replacements) {
            message = message.replace("[" + i + "]", String.valueOf(replacement));
            i++;
        }

        return message;
    }
}
