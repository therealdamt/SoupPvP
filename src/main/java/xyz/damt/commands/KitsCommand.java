package xyz.damt.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.damt.menu.menus.KitsMenu;
import xyz.damt.util.framework.command.BaseCommand;

public class KitsCommand extends BaseCommand {

    public KitsCommand() {
        super("kits", "", "/kits");

        this.playerOnly = true;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        new KitsMenu().openMenu(player);
    }

}
