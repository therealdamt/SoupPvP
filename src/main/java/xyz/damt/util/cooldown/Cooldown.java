package xyz.damt.util.cooldown;


import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public final class Cooldown {

    private final HashMap<UUID, Long> cooldownMap = new HashMap<>();

    public boolean isOnCooldown(Player p) {
        return this.cooldownMap.containsKey(p.getUniqueId()) && this.cooldownMap.get(p.getUniqueId()) >= System.currentTimeMillis();
    }

    public long getRemaining(Player p) {
        return this.cooldownMap.get(p.getUniqueId()) - System.currentTimeMillis();
    }

    public void placeOnCooldown(Player p, long duration) {
        this.cooldownMap.put(p.getUniqueId(), System.currentTimeMillis() + duration * 1000L);
    }

    public long getAllCooldowns(Player player) {
        return this.cooldownMap.get(player.getUniqueId());
    }

    public int getTime(Player p) {
        return (int) (this.getRemaining(p) / 1000L);
    }

    public void removeCooldown(Player p) {
        this.cooldownMap.remove(p.getUniqueId());
    }

}