package xyz.damt.commands.sub.pay;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.damt.profiles.Profile;
import xyz.damt.util.CC;

public class PaySubCommand extends xyz.damt.util.framework.command.SubCommand {

    public PaySubCommand() {
        super("", "", "/pay <player> <amount>", "Pays a player a specified amount");

        this.playerOnly = true;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        Profile profile = soup.getProfileHandler().getProfileByUUID(player.getUniqueId());

        if (args.length != 2) {
            player.sendMessage(CC.translate("&c" + getUsage()));
            return;
        }

        if (args[0].isEmpty()) {
            player.sendMessage(CC.translate("&cPlease insert a valid player name!"));
            return;
        }

        OfflinePlayer offlinePlayer = soup.getConfig().getOfflinePlayer(args[0]);
        Profile targetProfile = soup.getProfileHandler().getProfileByUUID(offlinePlayer.getUniqueId());

        if (targetProfile == null) {
            player.sendMessage(CC.translate("&cSeems like the user you've specified has never joined the server!"));
            return;
        }

        if (args[1].isEmpty()) {
            player.sendMessage(CC.translate("&cPlease insert a valid amount!"));
            return;
        }

        if (!CC.isNumber(args[1])) {
            player.sendMessage(CC.translate("&cPlease input a valid amount!"));
            return;
        }

        int amount = Integer.parseInt(args[1]);

        if (profile.getCoins() < amount) {
            player.sendMessage(CC.translate("&cYou do not have enough coins to do this action!"));
            return;
        }

        profile.setCoins(profile.getCoins() - amount);
        targetProfile.setCoins(targetProfile.getCoins() + amount);

        player.sendMessage(soup.getConfigHandler().getMessageHandler().PAY_SENT.replace("{amount}", String.valueOf(amount))
        .replace("{player}", targetProfile.getOfflinePlayer().getName()));

        if (targetProfile.getPlayer() != null)
            targetProfile.getPlayer().sendMessage(soup.getConfigHandler().getMessageHandler().PAY_PAID.replace("{amount}", String.valueOf(amount))
            .replace("{player}", player.getName()));
    }
}
