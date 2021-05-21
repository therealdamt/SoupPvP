package xyz.damt.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.damt.menu.menus.StatisticsMenu;
import xyz.damt.util.framework.command.BaseCommand;

public class StatsCommand extends BaseCommand {

    public StatsCommand() {
        super("stats", "", "/stats");

        this.playerOnly = true;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        new StatisticsMenu().openMenu(player);
    }
}
