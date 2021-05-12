package xyz.damt;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.damt.api.SoupAPI;
import xyz.damt.api.events.CoinGainEvent;
import xyz.damt.api.events.DeathGainEvent;
import xyz.damt.api.events.KillGainEvent;
import xyz.damt.api.events.SoupUseEvent;
import xyz.damt.profiles.Profile;
import xyz.damt.util.CC;

public class APIExample implements Listener {

    //Getting The API
    private final SoupAPI soupAPI = JavaPlugin.getPlugin(Soup.class).getSoupAPI();

    //Events
    @EventHandler
    public void onCoinsGainEvent(CoinGainEvent e) {
        e.setCoinsAmount(e.getCoinsAmount() * 2);
        e.getPlayerProfile().getPlayer().sendMessage(CC.translate("&aYou were lucky so we increased your coins input by 2 times!"));
    }

    @EventHandler
    public void onDeathGainEvent(DeathGainEvent e) {
        e.getKilledProfile().getPlayer().sendMessage(CC.translate("&cRip you died I guess?"));
    }

    @EventHandler
    public void onKillGainEvent(KillGainEvent e) {
        e.getKillerProfile().getPlayer().sendMessage(CC.translate("&aYay! You killed someone called: " + e.getTargetProfile().getOfflinePlayer().getName()));
    }

    @EventHandler
    public void onSoupUseEvent(SoupUseEvent e) {
        Profile profile = e.getProfile();

        if (profile.getSoupsUsed() >= 20) {
            e.setCancelled(true);
            e.getProfile().getPlayer().sendMessage(CC.translate("&cYou are not allowed ot use this soup due to you exceeding the maximum soup uses limit!"));
        }
    }

    public void apiExampleUsage(Player player) {
        soupAPI.getDeaths(player.getUniqueId());
        soupAPI.getKills(player.getUniqueId());
        soupAPI.getSoupsUsed(player.getUniqueId());
        soupAPI.getCoins(player.getUniqueId());

        soupAPI.getProfile(player.getUniqueId()).setSoupsUsed(100000);
        soupAPI.getProfile(player.getUniqueId()).setDeaths(1000);
        soupAPI.getProfile(player.getUniqueId()).setCoins(1000);
    }

}
