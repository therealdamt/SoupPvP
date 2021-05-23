package xyz.damt.guild;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.bson.Document;
import org.bukkit.entity.Player;
import xyz.damt.Soup;
import xyz.damt.util.CC;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
public class Guild {

    private final UUID leader;
    private final Soup soup = Soup.getInstance();

    private String name;
    private List<UUID> members;

    public Guild(UUID leader, String name) {
        this.leader = leader;
        this.name = name;

        this.members = new ArrayList<>();

        soup.getGuildHandler().getGuildMap().put(name.toLowerCase(), this);
    }

    public boolean isLeader(UUID uuid) {
        return leader.equals(uuid);
    }

    public void disband() {
        soup.getProfileHandler().getProfileByUUID(leader).setGuild(null);
        members.forEach(member -> soup.getProfileHandler().getProfileByUUID(member).setGuild(null));

        soup.getGuildHandler().getGuildMap().remove(name.toLowerCase());
    }

    public List<Player> getOnlineMembers() {
        List<Player> players = new ArrayList<>();
        getAllMembersIncludingLeader().forEach(uuid -> {
            if (soup.getServer().getPlayer(uuid) != null) players.add(soup.getServer().getPlayer(uuid));
        });
        return players;
    }

    public List<UUID> getAllMembersIncludingLeader() {
        List<UUID> all = new ArrayList<>(members);
        all.add(leader);
        return all;
    }

    @SneakyThrows
    public void save(boolean mongo) {
        if (!mongo) {
            soup.getGuildsYML().getConfig().set(name + ".leader", leader.toString());
            soup.getGuildsYML().getConfig().set(name + ".members", CC.listUUIDToString(members));
            soup.getGuildsYML().save();
            return;
        }

        Document document = soup.getGuilds().find(new Document("_id", name)).first();

        if (document == null) {
            Document newDocument = new Document("_id", name).append("members", CC.listUUIDToString(members))
                    .append("leader", leader.toString());
            soup.getGuilds().insertOne(newDocument);
            return;
        }

        updateDocument(document, "members", CC.listUUIDToString(members));
        updateDocument(document, "leader", leader.toString());
    }

    private void updateDocument(Document document, String key, Object value) {
        if (document != null) {
            document.put(key, value);
            soup.getServer().getScheduler().runTaskAsynchronously(soup, () -> {
                soup.getProfiles().replaceOne(Filters.eq("_id", name)
                        , document, new ReplaceOptions().upsert(true));
            });
        }
    }
}
