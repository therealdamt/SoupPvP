package xyz.damt.guild;

import lombok.Getter;
import org.bson.Document;
import xyz.damt.Soup;
import xyz.damt.util.CC;

import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;
import java.util.function.Consumer;

@Getter
public class GuildHandler {

    private final HashMap<String, Guild> guildMap;
    private final Soup soup;

    public GuildHandler(Soup soup) {
        this.guildMap = new HashMap<>();
        this.soup = soup;
    }

    public void loadAllGuilds() {
        soup.getServer().getScheduler().runTaskAsynchronously(soup, () -> {

            if (!soup.getConfigHandler().getSettingsHandler().USE_MONGO) {
                soup.getGuildsYML().getConfig().getConfigurationSection("").getKeys(false).forEach(s -> {
                    Guild guild = new Guild(UUID.fromString(soup.getGuildsYML().getConfig().getString(s + ".leader")), s);
                    guild.setMembers(CC.listStringToUUID(soup.getGuildsYML().getConfig().getStringList(s + ".members")));
                });
                return;
            }

            soup.getGuilds().find().forEach((Consumer<? super Document>)  document -> {
                Guild guild = new Guild(UUID.fromString(document.getString("leader")), document.getString("_id"));
                guild.setMembers(CC.listStringToUUID(document.getList("members", String.class)));
            });

        });
    }

    public Guild getGuildByName(String guild) {
        return guildMap.get(guild.toLowerCase());
    }

    public Collection<Guild> getAllGuilds() {
        return guildMap.values();
    }

}
