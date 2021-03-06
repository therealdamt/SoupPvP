package xyz.damt.profiles;

import lombok.Getter;
import org.bson.Document;
import xyz.damt.Soup;

import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;
import java.util.function.Consumer;

public class ProfileHandler {

    private final Soup soup;
    @Getter private final HashMap<UUID, Profile> profileHashMap;

    public ProfileHandler(Soup soup) {
        this.soup = soup;
        this.profileHashMap = new HashMap<>();
    }

    public void loadAllProfiles() {
        soup.getServer().getScheduler().runTaskAsynchronously(soup, () -> {
            if (!soup.getConfigHandler().getSettingsHandler().USE_MONGO) {
                soup.getProfilesYML().getConfig().getConfigurationSection("").getKeys(false).forEach(s -> {
                    new Profile(UUID.fromString(s), false);
                });
                return;
            }

           soup.getProfiles().find().forEach((Consumer<? super Document>) document -> {
                new Profile(UUID.fromString(document.getString("_id")), true);
           });
        });
    }

    public Profile getProfileByUUID(UUID uuid) {
        return profileHashMap.get(uuid);
    }

    public boolean hasProfile(UUID uuid) {
        return getAllProfiles().stream().anyMatch(profile -> profile.getUuid().equals(uuid));
    }

    public Collection<Profile> getAllProfiles() {
        return profileHashMap.values();
    }


}
