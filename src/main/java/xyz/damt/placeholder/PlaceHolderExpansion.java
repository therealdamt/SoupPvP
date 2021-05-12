package xyz.damt.placeholder;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import xyz.damt.Soup;
import xyz.damt.profiles.Profile;

public class PlaceHolderExpansion extends PlaceholderExpansion {

    private final Soup soup;

    public PlaceHolderExpansion(Soup soup) {
        this.soup = soup;
    }

    @Override
    public String getIdentifier() {
        return "soup";
    }

    @Override
    public String getAuthor() {
        return "damt";
    }

    @Override
    public String getVersion() {
        return "1.0";
    }

    @Override
    public boolean canRegister() {
        return soup.getConfigHandler().getSettingsHandler().USE_PLACEHOLDER_API;
    }

    @Override
    public String onPlaceholderRequest(Player p, String params) {
        Profile playerProfile = soup.getProfileHandler().getProfileByUUID(p.getUniqueId());

        if (params.equalsIgnoreCase("kills")) {
            return String.valueOf(playerProfile.getKills());
        }

        if (params.equalsIgnoreCase("deaths")) {
            return String.valueOf(playerProfile.getDeaths());
        }

        if (params.equalsIgnoreCase("soups")) {
            return String.valueOf(playerProfile.getSoupsUsed());
        }

        if (params.equalsIgnoreCase("coins")) {
            return String.valueOf(playerProfile.getCoins());
        }

        return null;
    }
}
