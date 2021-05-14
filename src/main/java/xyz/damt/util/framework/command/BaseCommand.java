package xyz.damt.util.framework.command;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.damt.Soup;

import java.util.*;

@Getter @Setter
public abstract class BaseCommand implements CommandExecutor {

    private final String name;
    private final String permission;
    private final String usage;

    protected boolean playerOnly = false;

    private final Set<SubCommand> subCommands = new HashSet<>();
    public final Soup soup = Soup.getInstance();

    public BaseCommand(String name, String permission, String usage) {
        this.name = name;
        this.permission = permission;
        this.usage = usage;

        soup.getCommand(getName()).setExecutor(this);
    }

    public abstract void execute(CommandSender sender, String[] args);

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(playerOnly && !(commandSender instanceof Player)) {
            commandSender.sendMessage(ChatColor.RED + "This command can only be executed by players.");
            return false;
        }

        if(permission != null && !commandSender.hasPermission(permission)) {
            commandSender.sendMessage("No Permission.");
            return false;
        }

        if(strings == null || strings.length == 0) {
            execute(commandSender, strings);
            return false;
        }

        getSubCommands().forEach(subCommand -> {
            if (subCommand.getName() == null) {
                subCommand.execute(commandSender, strings);
            }
        });

        String subCommandName = strings[0];
        SubCommand subCommand = getSubCommand(subCommandName);

        if(subCommand == null) {
            commandSender.sendMessage("Invalid Value");
            return false;
        }

        if(subCommand.getPermission() != null && !commandSender.hasPermission(subCommand.getPermission())){
            commandSender.sendMessage("No Permission");
            return false;
        }

        subCommand.execute(commandSender, strings);

        return false;
    }

    public SubCommand getSubCommand(String name){
        return subCommands.stream().filter(command -> command.getName().equals(name)).findAny().orElse(null);
    }

    public void sendUsage(CommandSender sender){
        sender.sendMessage(ChatColor.RED + usage);
    }


}
