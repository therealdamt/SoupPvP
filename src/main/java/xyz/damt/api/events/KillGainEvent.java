package xyz.damt.api.events;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import xyz.damt.profiles.Profile;

public class KillGainEvent extends Event {

    private final HandlerList handlerList = new HandlerList();

    private final Profile killerProfile;
    private final Profile targetProfile;
    private final int kills;

    public KillGainEvent(Profile killerProfile, Profile targetProfile, int kills) {
        this.killerProfile = killerProfile;
        this.targetProfile = targetProfile;
        this.kills = kills;
    }

    public int getKills() {
        return kills;
    }

    public Profile getKillerProfile() {
        return killerProfile;
    }

    public Profile getTargetProfile() {
        return targetProfile;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

}
