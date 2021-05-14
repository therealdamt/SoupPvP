package xyz.damt.handlers;

import lombok.Getter;
import xyz.damt.Soup;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class ServerHandler {

    @Getter private final Set<UUID> buildPlayers;

    private final Soup soup = Soup.getInstance();

    public ServerHandler() {
        this.buildPlayers = new HashSet<>();
    }

    public void allowBuild(UUID uuid) {
        buildPlayers.add(uuid);
    }

    public void denyBuild(UUID uuid) {
        buildPlayers.remove(uuid);
    }

    public boolean canBuild(UUID uuid) {
        return buildPlayers.contains(uuid);
    }

}
