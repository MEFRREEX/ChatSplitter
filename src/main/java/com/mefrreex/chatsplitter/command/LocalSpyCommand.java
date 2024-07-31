package com.mefrreex.chatsplitter.command;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import com.mefrreex.chatsplitter.ChatSplitter;
import com.mefrreex.chatsplitter.utils.Language;

public class LocalSpyCommand extends Command {

    private final ChatSplitter main;

    public LocalSpyCommand(ChatSplitter main) {
        super("localspy", "Spying on a local chat");
        this.setAliases(new String[]{"lspy"});
        this.main = main;
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

        if (!main.getSpyers().contains(sender)) {
            main.getSpyers().add((Player) sender);
            sender.sendMessage(Language.get("command-spy-enable"));
        } else {
            main.getSpyers().remove(sender);
            sender.sendMessage(Language.get("command-spy-disable"));
        }
        return false;
    }
}