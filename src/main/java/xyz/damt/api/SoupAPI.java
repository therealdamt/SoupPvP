package xyz.damt.api;

import xyz.damt.Soup;
import xyz.damt.guild.Guild;
import xyz.damt.kit.Kit;
import xyz.damt.profiles.Profile;

import java.util.UUID;

public class SoupAPI {

    private final Soup soup;

    public SoupAPI() {
        this.soup = Soup.getInstance();
    }

    public int getKills(UUID uuid) {
        return soup.getProfileHandler().getProfileByUUID(uuid).getKills();
    }

    public int getDeaths(UUID uuid) {
        return soup.getProfileHandler().getProfileByUUID(uuid).getDeaths();
    }

    public int getCoins(UUID uuid) {
        return soup.getProfileHandler().getProfileByUUID(uuid).getCoins();
    }

    public int getSoupsUsed(UUID uuid) {
        return soup.getProfileHandler().getProfileByUUID(uuid).getSoupsUsed();
    }

    public Profile getProfile(UUID uuid) {
        return soup.getProfileHandler().getProfileByUUID(uuid);
    }

    public Kit getKitByName(String name) {
        return soup.getKitHandler().getKitByName(name);
    }

    public Guild getGuildByName(String name) {
        return soup.getGuildHandler().getGuildByName(name);
    }

    public Guild getGuild(UUID uuid) {
        return soup.getProfileHandler().getProfileByUUID(uuid).getGuild();
    }

}
