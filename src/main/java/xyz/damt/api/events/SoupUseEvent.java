package xyz.damt.api.events;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import xyz.damt.profiles.Profile;

public class SoupUseEvent extends Event implements Cancellable {

    private final HandlerList handlerList = new HandlerList();

    private ItemStack soup;
    private final Profile profile;

    private boolean cancel;

    public SoupUseEvent(ItemStack soup, Profile profile) {
        this.soup = soup;
        this.profile = profile;

        this.cancel = false;
    }

    public ItemStack getSoup() {
        return soup;
    }

    public void setSoup(ItemStack soup) {
        this.soup = soup;
    }

    public Profile getProfile() {
        return profile;
    }

    @Override
    public boolean isCancelled() {
        return cancel;
    }

    @Override
    public void setCancelled(boolean b) {
        cancel = b;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
