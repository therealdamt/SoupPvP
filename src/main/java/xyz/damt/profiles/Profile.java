package xyz.damt.profiles;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import lombok.Getter;
import lombok.Setter;
import org.bson.Document;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.damt.Soup;
import xyz.damt.util.cooldown.Cooldown;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.Executor;

@Getter
@Setter
public class Profile {

    private final Executor profileThread;
    private final Cooldown timerCooldown = new Cooldown();

    private final UUID uuid;
    private final Soup soup;
    private final boolean mongo;

    private int coins;
    private int kills;
    private int deaths;

    public Profile(UUID uuid, boolean mongo) {
        this.uuid = uuid;
        this.mongo = mongo;

        this.soup = JavaPlugin.getPlugin(Soup.class);

        this.profileThread = soup.getProfileThread();

        soup.getProfileHandler().getProfileHashMap().put(uuid, this);
        this.load();
    }

    public void load() {
        profileThread.execute(() -> {
            if (!mongo) {
                if (!soup.getProfilesYML().getConfig().contains(uuid.toString())) return;

                this.kills = soup.getProfilesYML().getConfig().getInt(uuid.toString() + ".kills");
                this.deaths = soup.getProfilesYML().getConfig().getInt(uuid.toString() + ".deaths");
                this.coins = soup.getProfilesYML().getConfig().getInt(uuid.toString() + ".coins");
                return;
            }

            Document document = soup.getProfiles().find(new Document("_id", uuid.toString())).first();
            if (document == null) return;

            this.kills = document.getInteger("kills");
            this.deaths = document.getInteger("deaths");
            this.coins = document.getInteger("coins");
        });
    }

    public void save() {
        profileThread.execute(() -> {
            if (!mongo) {
                if (!soup.getProfilesYML().getConfig().contains(uuid.toString())) {

                    soup.getProfilesYML().getConfig().set(uuid.toString() + ".kills", 0);
                    soup.getProfilesYML().getConfig().set(uuid.toString() + ".deaths", 0);
                    soup.getProfilesYML().getConfig().set(uuid.toString() + ".coins", 0);

                    try {
                        soup.getProfilesYML().save();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return;
                }

                soup.getProfilesYML().getConfig().set(uuid.toString() + ".kills", kills);
                soup.getProfilesYML().getConfig().set(uuid.toString() + ".deaths", deaths);
                soup.getProfilesYML().getConfig().set(uuid.toString() + ".coins", coins);
                try {
                    soup.getProfilesYML().save();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            }

            Document document = soup.getProfiles().find(new Document("_id", uuid.toString())).first();

            if (document == null) {
                Document newDocument = new Document("_id", uuid.toString()).append("kills", 0).append("deaths", 0).append("coins", 0);
                soup.getProfiles().insertOne(newDocument);
                return;
            }

            updateDocument(document, "kills", kills);
            updateDocument(document, "deaths", deaths);
            updateDocument(document, "coins", coins);
        });
    }

    public Player getPlayer() {
        return soup.getServer().getPlayer(uuid);
    }

    public OfflinePlayer getOfflinePlayer() {
        return soup.getServer().getOfflinePlayer(uuid);
    }

    private void updateDocument(Document document, String key, Object value) {
        if (document != null) {
            document.put(key, value);
            profileThread.execute(() ->
                    soup.getProfiles().replaceOne(Filters.eq("_id", uuid.toString())
                            , document, new ReplaceOptions().upsert(true)));
        }

    }

}
