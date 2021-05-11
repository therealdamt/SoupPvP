package xyz.damt.util.framework.command;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.command.CommandSender;

@Getter @Setter
public abstract class SubCommand {

    private final String name;
    private final String permission;
    private final String usage;
    private final String description;

    protected boolean playerOnly = false;

    public SubCommand(String name, String permission, String usage, String description) {
        this.name = name;
        this.permission = permission;
        this.usage = usage;
        this.description = description;
    }

    public abstract void execute(CommandSender sender, String[] args);

    public void sendUsage(CommandSender sender){
        sender.sendMessage(usage);
    }

}
