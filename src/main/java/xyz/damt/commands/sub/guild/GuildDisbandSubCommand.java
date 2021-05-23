package xyz.damt.commands.sub.guild;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.damt.guild.Guild;
import xyz.damt.profiles.Profile;
import xyz.damt.util.CC;

public class GuildDisbandSubCommand extends xyz.damt.util.framework.command.SubCommand {

    public GuildDisbandSubCommand() {
        super("disband ", null, "/guild disband", "Disbands your current guild");

        this.playerOnly = true;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        Profile profile = soup.getProfileHandler().getProfileByUUID(player.getUniqueId());
        Guild guild = profile.getGuild();

        if (guild == null) {
            player.sendMessage(CC.translate("&cYou must be in a guild to do this command!"));
            return;
        }

        if (!guild.isLeader(player.getUniqueId())) {
            player.sendMessage(CC.translate("&cYou must be the leader of the guild to do this command!"));
            return;
        }

        guild.disband();
        soup.getServer().broadcastMessage(CC.translate("&7The guild &b" + guild.getName() + " &7has been &bdisbanded&7!"));
    }

}
