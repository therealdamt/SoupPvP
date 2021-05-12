package xyz.damt.api.events;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import xyz.damt.profiles.Profile;

public class CoinGainEvent extends Event implements Cancellable {

    private final HandlerList handlerList = new HandlerList();
    private final Profile playerProfile;

    private int coinsAmount;
    private boolean cancel;

    public CoinGainEvent(Profile playerProfile, int coinsAmount) {
        this.playerProfile = playerProfile;
        this.coinsAmount = coinsAmount;

        this.cancel = false;
    }

    public Profile getPlayerProfile() {
        return playerProfile;
    }

    public int getCoinsAmount() {
        return coinsAmount;
    }

    public void setCoinsAmount(int coinsAmount) {
        this.coinsAmount = coinsAmount;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    @Override
    public boolean isCancelled() {
        return cancel;
    }

    @Override
    public void setCancelled(boolean b) {
        this.cancel = b;
    }
}
