package com.mefrreex.chatsplitter.utils.language;

import cn.nukkit.command.CommandSender;
import com.mefrreex.chatsplitter.utils.language.adapter.TranslationAdapter;
import org.jetbrains.annotations.NotNull;
import lombok.Getter;

import java.util.Objects;

/**
 * Provides methods for retrieving translated strings using a TranslationAdapter.
 */
public class Language {

    /**
     * The active translation adapter used for translations.
     */
    @Getter
    private static TranslationAdapter translationAdapter;

    /**
     * Initializes the translation adapter for the system.
     *
     * @param translationAdapter The translation adapter to use. Must not be null
     */
    public static void init(@NotNull TranslationAdapter translationAdapter) {
        Language.translationAdapter = Objects.requireNonNull(translationAdapter, "TranslationAdapter cannot be null");;
    }

    /**
     * Retrieves a translated string based on the given key.
     *
     * @param key The translation key. Must not be null or empty
     * @param replacements Optional replacement parameters for the translation
     * @return The translated string
     */
    public static String get(@NotNull String key, Object... replacements) {
        return translate(key, null, null, replacements);
    }

    /**
     * Retrieves a translated string based on the given key and sender.
     *
     * @param key The translation key. Must not be null or empty
     * @param sender The command sender whose language is used for translation
     * @param replacements Optional replacement parameters for the translation
     * @return The translated string
     */
    public static String get(@NotNull String key, CommandSender sender, Object... replacements) {
        return translate(key, sender, null, replacements);
    }

    /**
     * Retrieves a translated string based on the given key and language.
     *
     * @param key The translation key. Must not be null or empty
     * @param language The language code to use for translation
     * @param replacements Optional replacement parameters for the translation
     * @return The translated string
     */
    public static String get(@NotNull String key, String language, Object... replacements) {
        return translate(key, null, language, replacements);
    }

    private static String translate(@NotNull String key, CommandSender sender, String language, Object... replacements) {
        Objects.requireNonNull(key, "Translation key cannot be null");

        if (translationAdapter == null) {
            throw new IllegalStateException("TranslationAdapter is not initialized");
        }

        if (sender != null) {
            return translationAdapter.translate(key, sender, replacements);
        } else if (language != null && !language.isEmpty()) {
            return translationAdapter.translate(key, language, replacements);
        } else {
            return translationAdapter.translate(key, replacements);
        }
    }
}
