package xyz.damt.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.damt.util.CC;
import xyz.damt.util.framework.command.BaseCommand;

public class SetSpawnCommand extends BaseCommand {

    public SetSpawnCommand() {
        super("setspawn", "soup.admin.setspawn", "/setspawn");

        this.playerOnly = true;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        player.getWorld().setSpawnLocation(player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ());
        player.sendMessage(CC.translate("&7You have set the &bspawn location &7to your &blocation&7!"));
    }
}
