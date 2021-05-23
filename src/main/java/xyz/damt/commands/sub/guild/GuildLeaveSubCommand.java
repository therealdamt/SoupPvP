package xyz.damt.commands.sub.guild;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.damt.guild.Guild;
import xyz.damt.profiles.Profile;
import xyz.damt.util.CC;

public class GuildLeaveSubCommand extends xyz.damt.util.framework.command.SubCommand {

    public GuildLeaveSubCommand() {
        super("leave", null, "/guild leave", "Leaves the guild you are currently in!");
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

        if (guild.isLeader(player.getUniqueId())) {
            player.sendMessage(CC.translate("&cYou cannot leave the guild as you are the leader, if you'd like to disband the guild please do /guild disband"));
            return;
        }

        guild.getMembers().remove(player.getUniqueId());
        profile.setGuild(null);

        player.sendMessage(CC.translate("&7You have &bleft &7the guild &b" + guild.getName()));
        guild.getOnlineMembers().forEach(member -> member.sendMessage(CC.translate("&7The user &b" + player.getName() + " &7has &bleft &7the guild!")));
    }

}
