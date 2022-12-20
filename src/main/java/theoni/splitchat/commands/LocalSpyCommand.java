package theoni.splitchat.commands;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.Config;
import theoni.splitchat.Main;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class LocalSpyCommand extends Command {

    Main plugin;
    Config config;
    Config messages;
    public static List<Player> spyer;

    public LocalSpyCommand(Main plugin) {
        super("localspy", "Spying on a local chat");
        this.setAliases(new String[]{"lspy"});

        this.plugin = plugin;
        this.config = plugin.getConfig();
        this.messages = new Config(new File(plugin.getDataFolder() + "/messages.yml"), Config.YAML);
        spyer = new ArrayList<Player>();
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        // Проверка на наличие пермишена
        if (!sender.hasPermission("splitchat.command.spy")) {
            sender.sendMessage(messages.getString("permission.command"));
            return false;
        }
        // Проверка на игрока
        if (!(sender instanceof Player)) {
            sender.sendMessage(messages.getString("ingame"));
            return false;
        }

        // Вкл./выкл. просмотра локальных сообщений
        if (spyer.contains(sender)) {
            spyer.remove(sender);
            sender.sendMessage(messages.getString("spy.disable"));
        } else {
            spyer.add((Player) sender);
            sender.sendMessage(messages.getString("spy.enable"));
        }
        return false;
    }
}