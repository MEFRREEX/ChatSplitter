package com.mefrreex.chatsplitter.command;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.Config;
import com.mefrreex.chatsplitter.ChatSplitter;

public class LocalSpyCommand extends Command {

    private final ChatSplitter main;
    private final Config messages;

    public LocalSpyCommand(ChatSplitter main) {
        super("localspy", "Spying on a local chat");
        this.setAliases(new String[]{"lspy"});

        this.messages = new Config(main.getDataFolder() + "/messages.yml", Config.YAML);
        this.main = main;
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        // Проверка на наличие пермишена
        if (!testPermission(sender)) {
            sender.sendMessage(messages.getString("permission.command"));
            return false;
        }
        // Проверка на игрока
        if (!(sender instanceof Player)) {
            sender.sendMessage(messages.getString("ingame"));
            return false;
        }

        // Вкл./выкл. просмотра локальных сообщений
        if (!main.getSpyers().contains(sender)) {
            main.getSpyers().add((Player) sender);
            sender.sendMessage(messages.getString("spy.enable"));
        } else {
            main.getSpyers().remove(sender);
            sender.sendMessage(messages.getString("spy.disable"));
        }
        return false;
    }
}