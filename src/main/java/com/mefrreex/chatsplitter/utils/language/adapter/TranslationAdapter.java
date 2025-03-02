package com.mefrreex.chatsplitter.utils.language.adapter;

import cn.nukkit.command.CommandSender;

/**
 * Provides an interface to translate strings for a specific player
 */
public interface TranslationAdapter {

    /**
     * Translates the message by its key.
     *
     * @param key The translation key
     * @param replacements The translation parameters
     * @return The translated string
     */
    String translate(String key, Object... replacements);

    /**
     * Translates the message by its key.
     *
     * @param key The translation key
     * @param sender The sender or a player
     * @param replacements The translation parameters
     * @return The translated string
     */
    String translate(String key, CommandSender sender, Object... replacements);

    /**
     * Translates the message by its key.
     *
     * @param key The translation key
     * @param language The language to be translated
     * @param replacements The translation parameters
     * @return The translated string
     */
    String translate(String key, String language, Object... replacements);

    /**
     * Translates the message by its key.
     * If no translation for this key is found, the key itself will be returned.
     *
     * @param key The translation key
     * @param sender The player
     * @param replacements The translation parameters
     * @return The translated string
     */
    default String translateOrGetKey(String key, CommandSender sender, Object... replacements) {
        String translated = this.translate(key, sender, replacements);
        if (translated == null || translated.equals(key)) {
            return key;
        }
        return translated;
    }
}
