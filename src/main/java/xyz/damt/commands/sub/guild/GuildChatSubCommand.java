package xyz.damt.commands.sub.guild;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.damt.profiles.Profile;
import xyz.damt.util.CC;

public class GuildChatSubCommand extends xyz.damt.util.framework.command.SubCommand {

    public GuildChatSubCommand() {
        super("chat", null, "/guild chat", "Enabled or disables guild chat");

        this.playerOnly = true;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        Profile playerProfile = soup.getProfileHandler().getProfileByUUID(player.getUniqueId());

        if (playerProfile.getGuild() == null) {
            player.sendMessage(CC.translate("&cYou must be in a guild to do this command!"));
            return;
        }

        if (playerProfile.isGuildChat()) {
            playerProfile.setGuildChat(false);
            player.sendMessage(CC.translate("&7You have &bdisabled &7guild chat!"));
        } else {
            playerProfile.setGuildChat(true);
            player.sendMessage(CC.translate("&7You have &benabled &7guild chat!"));
        }
    }
}
