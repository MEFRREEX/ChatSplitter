package com.mefrreex.chatsplitter.command.impl;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import com.mefrreex.chatsplitter.ChatSplitter;
import com.mefrreex.chatsplitter.command.BaseCommand;
import com.mefrreex.chatsplitter.utils.Language;

public class SpyCommand extends BaseCommand {

    public SpyCommand() {
        super("spy");
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (!this.testPermission(sender)) {
            return false;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage(Language.get("command-in-game"));
            return false;
        }

        if (!ChatSplitter.CHAT_SPY.contains(sender)) {
            ChatSplitter.CHAT_SPY.add((Player) sender);
            sender.sendMessage(Language.get("command-spy-enable"));
        } else {
            ChatSplitter.CHAT_SPY.remove(sender);
            sender.sendMessage(Language.get("command-spy-disable"));
        }
        return true;
    }
}