package xyz.damt.profiles;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import lombok.Getter;
import lombok.Setter;
import org.bson.Document;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import xyz.damt.Soup;
import xyz.damt.guild.Guild;
import xyz.damt.util.cooldown.Cooldown;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class Profile {

    private final Cooldown timerCooldown = new Cooldown();

    private final UUID uuid;
    private final Soup soup;
    private final boolean mongo;

    private int coins;
    private int kills;
    private int deaths;
    private int soupsUsed;
    private Guild guild;
    private boolean guildChat;

    private List<Guild> guildInvites;

    public Profile(UUID uuid, boolean mongo) {
        this.uuid = uuid;
        this.mongo = mongo;

        this.soup = Soup.getInstance();
        this.guildInvites = new ArrayList<>();

        soup.getProfileHandler().getProfileHashMap().put(uuid, this);
        this.load();
    }

    public void load() {
        soup.getServer().getScheduler().runTaskAsynchronously(soup, () -> {
            if (!mongo) {
                if (!soup.getProfilesYML().getConfig().contains(uuid.toString())) return;

                this.kills = soup.getProfilesYML().getConfig().getInt(uuid.toString() + ".kills");
                this.deaths = soup.getProfilesYML().getConfig().getInt(uuid.toString() + ".deaths");
                this.coins = soup.getProfilesYML().getConfig().getInt(uuid.toString() + ".coins");
                this.soupsUsed = soup.getProfilesYML().getInt(uuid.toString() + ".used");
                this.guild = soup.getGuildHandler().getGuildByName(soup.getProfilesYML().getString(uuid.toString() + ".guild"));
                return;
            }

            Document document = soup.getProfiles().find(new Document("_id", uuid.toString())).first();
            if (document == null) return;

            this.kills = document.getInteger("kills");
            this.deaths = document.getInteger("deaths");
            this.coins = document.getInteger("coins");
            this.soupsUsed = document.getInteger("used");
            this.guild = soup.getGuildHandler().getGuildByName(document.getString("guild"));
        });
    }

    public void save() {
        if (!mongo) {
            soup.getProfilesYML().getConfig().set(uuid.toString() + ".kills", kills);
            soup.getProfilesYML().getConfig().set(uuid.toString() + ".deaths", deaths);
            soup.getProfilesYML().getConfig().set(uuid.toString() + ".coins", coins);
            soup.getProfilesYML().getConfig().set(uuid.toString() + ".used", soupsUsed);
            soup.getProfilesYML().getConfig().set(uuid.toString() + ".guild", guild == null ? "null" : guild.getName());

            try {
                soup.getProfilesYML().save();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }

        Document document = soup.getProfiles().find(new Document("_id", uuid.toString())).first();

        if (document == null) {
            Document newDocument = new Document("_id", uuid.toString()).append("kills", 0).append("deaths", 0).append("coins", 0)
                    .append("guild", guild == null ? "null" : guild.getName());
            soup.getProfiles().insertOne(newDocument);
            return;
        }

        updateDocument(document, "kills", kills);
        updateDocument(document, "deaths", deaths);
        updateDocument(document, "coins", coins);
        updateDocument(document, "used", soupsUsed);
        updateDocument(document, "guild", guild == null ? "null" : guild.getName());
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
            soup.getServer().getScheduler().runTaskAsynchronously(soup, () -> {
                soup.getProfiles().replaceOne(Filters.eq("_id", uuid.toString())
                        , document, new ReplaceOptions().upsert(true));
            });
        }
    }

}
