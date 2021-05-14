package xyz.damt.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.damt.util.CC;
import xyz.damt.util.framework.command.BaseCommand;

public class BuildCommand extends BaseCommand {

    public BuildCommand() {
        super("build", "soup.admin.build", "/build");

        this.playerOnly = true;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        if (soup.getServerHandler().canBuild(player.getUniqueId())) {
            soup.getServerHandler().denyBuild(player.getUniqueId());
            player.sendMessage(CC.translate("&7You have &bdisabled &7your &bbuild mode&7!"));
        } else {
            soup.getServerHandler().allowBuild(player.getUniqueId());
            player.sendMessage(CC.translate("&7You have &benabled &7your &bbuild mode&7!"));
        }
    }
}
