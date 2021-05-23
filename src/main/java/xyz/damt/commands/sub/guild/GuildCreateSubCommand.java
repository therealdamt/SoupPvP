package xyz.damt.commands.sub.guild;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.damt.guild.Guild;
import xyz.damt.profiles.Profile;
import xyz.damt.util.CC;

public class GuildCreateSubCommand extends xyz.damt.util.framework.command.SubCommand {

    public GuildCreateSubCommand() {
        super("create", null, "/guild create <name>", "Creates a guild with your desired name!");
        this.playerOnly = true;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        if (args.length != 2) {
            player.sendMessage(CC.translate("&c" + getUsage()));
            return;
        }

        Profile profile = soup.getProfileHandler().getProfileByUUID(player.getUniqueId());

        if (profile.getGuild() != null) {
            player.sendMessage(CC.translate("&cYou are already inside of a guild!"));
            return;
        }

        if (args[1].isEmpty()) {
            player.sendMessage(CC.translate("&cPlease input a valid guild name!"));
            return;
        }

        Guild guild = new Guild(player.getUniqueId(), args[1]);
        profile.setGuild(guild);
        
        player.sendMessage(CC.translate("&7You have created a guild with the name &b" + guild.getName()));
    }
}
