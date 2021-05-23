package xyz.damt.commands.sub.guild;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.damt.guild.Guild;
import xyz.damt.profiles.Profile;
import xyz.damt.util.CC;

public class GuildInviteSubCommand extends xyz.damt.util.framework.command.SubCommand {

    public GuildInviteSubCommand() {
        super("invite", null, "/guild invite <player>", "Invites a player to your guild");

        this.playerOnly = true;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        Profile profile = soup.getProfileHandler().getProfileByUUID(player.getUniqueId());
        Guild guild = profile.getGuild();

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
            player.sendMessage(CC.translate("&cPlease input a valid player name!"));
            return;
        }

        Player target = soup.getServer().getPlayer(args[1]);

        if (target == null) {
            player.sendMessage(CC.translate("&cThe player you mentioned seems to not exist!"));
            return;
        }

        Profile targetProfile = soup.getProfileHandler().getProfileByUUID(target.getUniqueId());

        if (targetProfile.getGuild() != null) {
            player.sendMessage(CC.translate("&cThis user is already inside of a guild!"));
            return;
        }

        targetProfile.getGuildInvites().add(guild);

        target.sendMessage(CC.translate("&7You have been invited to the guild &b" + guild.getName() + " &7if you'd like to join this guild please do &b" +
                 "/guild accept " + guild.getName()));
        guild.getOnlineMembers().forEach(player1 -> player1.sendMessage(CC.translate("&7The user &b" + target.getName() + " &7has been invited to the &bguild&7!")));
    }

}
