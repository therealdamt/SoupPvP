package xyz.damt.commands.sub.guild;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.damt.guild.Guild;
import xyz.damt.profiles.Profile;
import xyz.damt.util.CC;

public class GuildInfoSubCommand extends xyz.damt.util.framework.command.SubCommand {

    public GuildInfoSubCommand() {
        super("info", null, "/guild info", "Showcases all info of your guild");

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

        OfflinePlayer leader = soup.getServer().getOfflinePlayer(guild.getLeader());
        String leaderName = leader.isOnline() ? ChatColor.GREEN + leader.getName() : ChatColor.RED + leader.getName();

        StringBuilder members = new StringBuilder();
        guild.getMembers().forEach(member -> {
            OfflinePlayer memberPlayer = soup.getServer().getOfflinePlayer(member);
            String memberName = memberPlayer.isOnline() ? ChatColor.GREEN + memberPlayer.getName() : ChatColor.RED + memberPlayer.getName();

            members.append(memberName).append(", ");
        });

        player.sendMessage(CC.translate("&7&m--------------------------------"));
        player.sendMessage(CC.translate("&b&lGuild Name&7: " + guild.getName()));
        player.sendMessage(CC.translate("&b&lGuild Leader&7: " + leaderName));
        player.sendMessage(" ");
        player.sendMessage(CC.translate("&b&lGuild Online Count&7: " + guild.getOnlineMembers().size()));
        player.sendMessage(CC.translate("&b&lGuild Members&7: " + members.toString()));
        player.sendMessage(CC.translate("&7&m--------------------------------"));
    }

}
