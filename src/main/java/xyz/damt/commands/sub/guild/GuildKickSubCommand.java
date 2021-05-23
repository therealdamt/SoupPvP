package xyz.damt.commands.sub.guild;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.damt.guild.Guild;
import xyz.damt.profiles.Profile;
import xyz.damt.util.CC;

public class GuildKickSubCommand extends xyz.damt.util.framework.command.SubCommand {

    public GuildKickSubCommand() {
        super("kick", null, "/guild kick <player>", "Kicks a player outside of your faction!");

        this.playerOnly = true;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        Profile playerProfile = soup.getProfileHandler().getProfileByUUID(player.getUniqueId());
        Guild guild = playerProfile.getGuild();

        if (guild == null) {
            player.sendMessage(CC.translate("&cYou must be inside of a guild to do this command!"));
            return;
        }

        if (!guild.isLeader(player.getUniqueId())) {
            player.sendMessage(CC.translate("&cYou must be the leader of the guild to do this command!"));
            return;
        }

        if (args.length != 2) {
            player.sendMessage(CC.translate("&c" + getUsage()));
            return;
        }

        if (args[1].isEmpty()) {
            player.sendMessage(CC.translate("&cPlease insert a valid player name!"));
            return;
        }

        OfflinePlayer targetOfflinePlayer = soup.getServer().getOfflinePlayer(args[1]);
        Profile profile = soup.getProfileHandler().getProfileByUUID(targetOfflinePlayer.getUniqueId());

        if (profile == null) {
            player.sendMessage(CC.translate("&cThe user " + targetOfflinePlayer.getName() + " has never logged onto this server before!"));
            return;
        }

        if (!playerProfile.getGuild().equals(guild) || profile.getGuild() == null) {
            player.sendMessage(CC.translate("&cThat user is not inside of your guild!"));
            return;
        }

        if (!guild.getLeader().equals(player.getUniqueId())) {
            player.sendMessage(CC.translate("&cYou cannot kick yourself!"));
            return;
        }

        guild.getMembers().remove(targetOfflinePlayer.getUniqueId());
        profile.setGuild(null);

        if (targetOfflinePlayer.isOnline())
            soup.getServer().getPlayer(targetOfflinePlayer.getUniqueId()).sendMessage(CC.translate("&cYou have been removed from the guild " + guild.getName()));

        guild.getOnlineMembers().forEach(member -> member.sendMessage(CC.translate("&7The user &b" + targetOfflinePlayer.getName() + " &7has been &bremoved &7from the guild!")));
     }

}
