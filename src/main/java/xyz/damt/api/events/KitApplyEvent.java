package xyz.damt.api.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import xyz.damt.kit.Kit;

public class KitApplyEvent extends Event implements Cancellable {

    private final HandlerList handlerList = new HandlerList();

    private final Player player;
    private Kit kit;

    private boolean cancelled;

    public KitApplyEvent(Player player, Kit kit) {
        this.player = player;
        this.kit = kit;

        this.cancelled = false;
    }

    public Kit getKit() {
        return this.kit;
    }

    public Player getPlayer() {
        return this.player;
    }

    public void setKit(Kit kit) {
        this.kit = kit;
    }

    public boolean isCancelled() {
        return this.cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

}
