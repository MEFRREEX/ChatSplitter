package com.mefrreex.chatsplitter.command.impl;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import com.mefrreex.chatsplitter.ChatSplitter;
import com.mefrreex.chatsplitter.command.BaseCommand;
import com.mefrreex.chatsplitter.utils.language.Language;

public class SpyCommand extends BaseCommand {

    public SpyCommand() {
        super("spy");
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (!this.testPermission(sender)) {
            return false;
        }

        if (!(sender instanceof Player player)) {
            sender.sendMessage(Language.get("command-in-game", sender));
            return false;
        }

        if (!ChatSplitter.CHAT_SPY.contains(player)) {
            ChatSplitter.CHAT_SPY.add(player);
            player.sendMessage(Language.get("command-spy-enable", player));
        } else {
            ChatSplitter.CHAT_SPY.remove(player);
            player.sendMessage(Language.get("command-spy-disable", player));
        }
        return true;
    }
}