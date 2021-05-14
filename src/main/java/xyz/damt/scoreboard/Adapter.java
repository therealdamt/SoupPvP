package xyz.damt.scoreboard;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.damt.Soup;
import xyz.damt.profiles.Profile;
import xyz.damt.util.CC;
import xyz.damt.util.assemble.AssembleAdapter;
import xyz.damt.util.cooldown.DurationFormatter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Adapter implements AssembleAdapter {

    private final Soup soup;

    public Adapter(Soup soup) {
        this.soup = soup;
    }

    @Override
    public String getTitle(Player player) {
        return soup.getConfigHandler().getScoreboardHandler().SCOREBOARD_TITLE;
    }

    @Override
    public List<String> getLines(Player player) {
        Profile playerProfile = soup.getProfileHandler().getProfileByUUID(player.getUniqueId());

        if (playerProfile == null) return Arrays.asList("null");

        if (playerProfile.getTimerCooldown().isOnCooldown(player)) {
            return CC.translate(soup.getConfigHandler().getScoreboardHandler().IN_COMBAT.stream().map(string -> string.replace("{coins}",
                    String.valueOf(playerProfile.getCoins())).replace("{kills}", String.valueOf(playerProfile.getKills()))
                    .replace("{deaths}", String.valueOf(playerProfile.getDeaths()))
                    .replace("{used}", String.valueOf(playerProfile.getSoupsUsed()))
                    .replace("{time}", DurationFormatter.getRemaining(playerProfile.getTimerCooldown().getRemaining(player), true))
                    .replace("{player}", player.getName())).collect(Collectors.toList()));
        }

        return CC.translate(soup.getConfigHandler().getScoreboardHandler().NORMAL.stream().map(string -> string.replace("{coins}",
                String.valueOf(playerProfile.getCoins())).replace("{kills}", String.valueOf(playerProfile.getKills()))
                .replace("{deaths}", String.valueOf(playerProfile.getDeaths()))
                .replace("{used}", String.valueOf(playerProfile.getSoupsUsed()))
                .replace("{player}", player.getName())).collect(Collectors.toList()));
    }
}
