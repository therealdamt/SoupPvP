package xyz.damt.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import xyz.damt.profiles.Profile;
import xyz.damt.util.framework.listener.ListenerAdapter;

public class SoupListener extends ListenerAdapter {

    @EventHandler
    public void onPlayerDeathEvent(PlayerDeathEvent e) {
        e.setDeathMessage(null);

        Player killer = e.getEntity().getKiller();
        Player target = e.getEntity();

        Profile killerProfile = soup.getProfileHandler().getProfileByUUID(killer.getUniqueId());
        Profile targetProfile = soup.getProfileHandler().getProfileByUUID(target.getUniqueId());

        killerProfile.setKills(killerProfile.getKills() + 1);
        targetProfile.setDeaths(targetProfile.getDeaths() + 1);

        killerProfile.setCoins(killerProfile.getCoins() + soup.getConfigHandler().getSettingsHandler().KILL_REWARD);

        if (targetProfile.getCoins() < soup.getConfigHandler().getSettingsHandler().DEATH_LOSS) {
            targetProfile.setCoins(0);
            return;
        }

        targetProfile.setCoins(targetProfile.getCoins() - soup.getConfigHandler().getSettingsHandler().DEATH_LOSS);

        soup.getServer().getOnlinePlayers().forEach(player ->
            player.sendMessage(soup.getConfigHandler().getMessageHandler().DEATH_MESSAGE.replace("{player}", player.getName())
            .replace("{killer}", killer.getName())));
    }

    @EventHandler
    public void onPlayerRespawnEvent(PlayerRespawnEvent e) {
        e.setRespawnLocation(e.getPlayer().getWorld().getSpawnLocation());
    }

    @EventHandler
    public void onEntityDamageEntityEvent(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        if (!(e.getDamager() instanceof Player)) return;

        Player damager = (Player) e.getDamager();
        Player target = (Player) e.getEntity();

        Profile damagerProfile = soup.getProfileHandler().getProfileByUUID(damager.getUniqueId());
        Profile targetProfile = soup.getProfileHandler().getProfileByUUID(target.getUniqueId());

        damagerProfile.getTimerCooldown().placeOnCooldown(damager, 30);
        targetProfile.getTimerCooldown().placeOnCooldown(target, 30);
    }

}
