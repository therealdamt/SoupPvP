package xyz.damt.commands.admin;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.damt.guild.Guild;
import xyz.damt.profiles.Profile;
import xyz.damt.util.CC;
import xyz.damt.util.framework.command.BaseCommand;

public class DebugCommand extends BaseCommand {

    public DebugCommand() {
        super("debug", "soup.admin.debug", "/debug");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        sender.sendMessage(CC.translate("&7The debug information has been printed out in &bconsole&7."));
        System.out.println("Profile(s) Info: ");

        soup.getProfileHandler().getAllProfiles().forEach(profile -> {
            System.out.println("-------------------");
            System.out.println("Profile UUID: " + profile.getUuid().toString());
            System.out.println("Profile Coins: " + profile.getCoins());
            System.out.println("Profile Deaths: " + profile.getDeaths());
            System.out.println("Profile Kills: " + profile.getKills());
            System.out.println("Profile Soups: " + profile.getSoupsUsed());
        });

        System.out.println("-------------------");
        System.out.println("Kits Registered: ");
        soup.getKitHandler().getAllKits().forEach(kit -> {
            System.out.println("- " + kit.getKitName());
        });
    }

}
