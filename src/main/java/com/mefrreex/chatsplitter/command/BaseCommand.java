package com.mefrreex.chatsplitter.command;

import cn.nukkit.command.Command;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.utils.ConfigSection;
import com.mefrreex.chatsplitter.ChatSplitter;
import lombok.Getter;

@Getter
public abstract class BaseCommand extends Command {

    private static final String BASE_PERMISSION = "chatsplitter.command.";

    public BaseCommand(String name) {
        this(name, ChatSplitter.getInstance().getConfig().getSection("commands." + name));
    }

    public BaseCommand(String name, ConfigSection data) {
        super(data.getString("name", name));
        this.setAliases(data.getStringList("aliases").toArray(new String[]{}));
        this.setDescription(data.getString("description"));
        this.setUsage(data.getString("usage"));
        this.setPermission(BASE_PERMISSION + name);
        this.getCommandParameters().put("default", new CommandParameter[0]);
    }
}
