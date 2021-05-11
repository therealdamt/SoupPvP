package xyz.damt.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import xyz.damt.util.framework.listener.ListenerAdapter;

public class ServerListener extends ListenerAdapter {

    @EventHandler
    public void onServerBlockBreakEvent(BlockBreakEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onServerBlockPlaceEvent(BlockPlaceEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onEntitySpawnEvent(EntitySpawnEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onFoodLevelChangeEvent(FoodLevelChangeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onPlayerPortalEvent(PlayerPortalEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onCreateSpawnEvent(CreatureSpawnEvent e) {
        e.setCancelled(true);
    }

}
