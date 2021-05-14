package xyz.damt.commands.sub.kit;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.damt.kit.Kit;
import xyz.damt.util.CC;

public class KitCreateSubCommand extends xyz.damt.util.framework.command.SubCommand {

    public KitCreateSubCommand() {
        super("create", "soup.admin.kit", "/kit create <kitName>", "Creates a kit based on your inventory");

        this.playerOnly = true;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        if (args.length != 2) {
            player.sendMessage(CC.translate("&c" + getUsage()));
            return;
        }

        if (args[1].isEmpty()) {
            player.sendMessage(CC.translate("&cPlease insert a valid kit name!"));
            return;
        }

        Kit possibleExistingKit = soup.getKitHandler().getKitByName(args[1]);
        if (possibleExistingKit != null) {
            player.sendMessage(CC.translate("&cSeems like that kit already exists!"));
            return;
        }

        new Kit(args[1], player.getInventory().getContents(), player.getInventory().getArmorContents());
        player.sendMessage(CC.translate("&7Created the kit &b" + args[1] + ""));
    }

}
