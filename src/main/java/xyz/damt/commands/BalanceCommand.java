package xyz.damt.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.damt.profiles.Profile;
import xyz.damt.util.framework.command.SubCommand;

public class BalanceCommand extends SubCommand {

    public BalanceCommand() {
        super("balance", "", "/balance", "Showcases your balance");

        this.playerOnly = true;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        Profile playerProfile = soup.getProfileHandler().getProfileByUUID(player.getUniqueId());

        player.sendMessage(soup.getConfigHandler().getMessageHandler().BALANCE_MESSAGE
        .replace("{amount}", String.valueOf(playerProfile.getCoins())));
    }
}
