package xyz.damt.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.damt.commands.sub.kit.KitCreateSubCommand;
import xyz.damt.util.CC;
import xyz.damt.util.framework.command.BaseCommand;

public class KitCommand extends BaseCommand {

    public KitCommand() {
        super("kit", "soup.admin.kit", "/kit");

        this.getSubCommands().add(new KitCreateSubCommand());

        this.playerOnly = true;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        player.sendMessage(CC.translate("&cCorrect Usage:"));
        this.getSubCommands().forEach(subCommand -> {
            player.sendMessage(CC.translate("&7- &b" + subCommand.getUsage()));
        });
    }
}
