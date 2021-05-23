package xyz.damt.commands.sub.guild;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.damt.guild.Guild;
import xyz.damt.profiles.Profile;
import xyz.damt.util.CC;

public class GuildAcceptSubCommand extends xyz.damt.util.framework.command.SubCommand {

    public GuildAcceptSubCommand() {
        super("accept", null, "/guild accept <guild>", "Accepts a guild invite");

        this.playerOnly = true;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        Profile profile = soup.getProfileHandler().getProfileByUUID(player.getUniqueId());

        if (profile.getGuild() != null) {
            player.sendMessage(CC.translate("&cYou are already inside of a guild!"));
            return;
        }

        if (args.length != 2) {
            player.sendMessage(CC.translate("&c" + getUsage()));
            return;
        }

        if (args[1].isEmpty()) {
            player.sendMessage(CC.translate("&cPlease input a valid guild name!"));
            return;
        }

        Guild guild = soup.getGuildHandler().getGuildByName(args[1]);

        if (guild == null) {
            player.sendMessage(CC.translate("&cThat guild does not seem to exist!"));
            return;
        }

        if (!profile.getGuildInvites().contains(guild)) {
            player.sendMessage(CC.translate("&cSeems like the guild you specified did not invite you yet!"));
            return;
        }

        guild.getMembers().add(player.getUniqueId());

        profile.setGuild(guild);
        profile.getGuildInvites().remove(guild);

        guild.getOnlineMembers().forEach(member -> member.sendMessage(CC.translate("&7The user &b" + player.getName() + " &7has &bjoined &7the guild!")));
    }

}
