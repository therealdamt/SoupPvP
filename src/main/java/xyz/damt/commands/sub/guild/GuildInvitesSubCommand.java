package xyz.damt.commands.sub.guild;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.damt.profiles.Profile;
import xyz.damt.util.CC;

public class GuildInvitesSubCommand extends xyz.damt.util.framework.command.SubCommand {

    public GuildInvitesSubCommand() {
        super("invites", null, "/guild invites", "Showcases all your current guild invites");
        this.playerOnly = true;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        Profile profile = soup.getProfileHandler().getProfileByUUID(player.getUniqueId());

        if (profile.getGuildInvites().isEmpty()) {
            player.sendMessage(CC.translate("&cYou do not have any guild invites!"));
            return;
        }

        StringBuilder builder = new StringBuilder();
        profile.getGuildInvites().forEach(guild -> {
            builder.append("&b").append(guild.getName()).append("&7,");
        });

        player.sendMessage(CC.translate("&7Available &bGuild Invites&7:"));
        player.sendMessage(CC.translate(builder.toString()));
    }
}
