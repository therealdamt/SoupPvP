package xyz.damt.api.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import xyz.damt.profiles.Profile;

public class DeathGainEvent extends Event {

    private final HandlerList handlerList = new HandlerList();

    private final Profile killerProfile;
    private final Profile killedProfile;
    private final int deaths;

    public DeathGainEvent(Profile killerProfile, Profile killedProfile, int deaths) {
        this.killerProfile = killerProfile;
        this.killedProfile = killedProfile;
        this.deaths = deaths;
    }

    public int getDeaths() {
        return deaths;
    }

    public Profile getKillerProfile() {
        return killerProfile;
    }

    public Profile getKilledProfile() {
        return killedProfile;
    }


    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
