package xyz.damt.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import xyz.damt.profiles.Profile;
import xyz.damt.util.CC;
import xyz.damt.util.framework.listener.ListenerAdapter;

public class GuildListener extends ListenerAdapter {

    @EventHandler
    public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player)) return;
        if (!(e.getEntity() instanceof Player)) return;

        Player player = (Player) e.getDamager();
        Player entity = (Player) e.getEntity();

        Profile playerProfile = soup.getProfileHandler().getProfileByUUID(player.getUniqueId());
        Profile targetProfile = soup.getProfileHandler().getProfileByUUID(entity.getUniqueId());

        if (!playerProfile.getGuild().equals(targetProfile.getGuild())) return;

        e.setCancelled(true);
        player.sendMessage(CC.translate("&b" + entity.getName() + " &7is inside of your guild!"));
    }

    @EventHandler
    public void onPlayerChatEvent(AsyncPlayerChatEvent e) {
        Player player = e.getPlayer();

        Profile profile = soup.getProfileHandler().getProfileByUUID(player.getUniqueId());

        if (profile.getGuild() == null) return;
        if (!profile.isGuildChat()) return;

        e.setCancelled(true);
        profile.getGuild().getOnlineMembers().forEach(member -> {
            member.sendMessage(CC.translate("&7[&b" + profile.getGuild().getName() + "&7] &b" + player.getName() + " &7➟ &b" + e.getMessage()));
        });
    }

}
