package xyz.damt.commands;

import org.bukkit.command.CommandSender;
import xyz.damt.commands.sub.pay.PaySubCommand;
import xyz.damt.util.CC;
import xyz.damt.util.framework.command.BaseCommand;

public class PayCommand extends BaseCommand {

    public PayCommand() {
        super("pay", "", "/pay");

        this.getSubCommands().add(new PaySubCommand());

        this.playerOnly = true;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        sender.sendMessage(CC.translate("&c/pay <player> <amount>"));
    }
}
