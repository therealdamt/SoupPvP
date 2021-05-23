package xyz.damt.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.damt.commands.sub.guild.*;
import xyz.damt.util.CC;
import xyz.damt.util.framework.command.BaseCommand;

public class GuildCommand extends BaseCommand {

    public GuildCommand() {
        super("guild", "", "/guild");

        this.getSubCommands().add(new GuildCreateSubCommand());
        this.getSubCommands().add(new GuildDisbandSubCommand());
        this.getSubCommands().add(new GuildInviteSubCommand());
        this.getSubCommands().add(new GuildAcceptSubCommand());
        this.getSubCommands().add(new GuildInfoSubCommand());
        this.getSubCommands().add(new GuildInvitesSubCommand());
        this.getSubCommands().add(new GuildKickSubCommand());
        this.getSubCommands().add(new GuildUnInviteSubCommand());
        this.getSubCommands().add(new GuildLeaveSubCommand());
        this.getSubCommands().add(new GuildChatSubCommand());

        this.playerOnly = true;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        if (!soup.getConfigHandler().getSettingsHandler().GUILDS_IS_ENABLED) {
            player.sendMessage(CC.translate("&cLooks like the owner disabled guilds!"));
            return;
        }

        player.sendMessage(CC.translate("&7&m--------------&b&lGuild Help Commands&7&m--------------"));
        this.getSubCommands().forEach(subCommand -> {
            player.sendMessage(CC.translate("&7- &b" + subCommand.getUsage() + "&7- " + subCommand.getDescription()));
        });
        player.sendMessage(CC.translate("&7&m--------------&b&lGuild Help Commands&7&m--------------"));
    }

}
