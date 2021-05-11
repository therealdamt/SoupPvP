package xyz.damt;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import org.bson.Document;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.damt.config.ConfigHandler;
import xyz.damt.kit.KitHandler;
import xyz.damt.profiles.Profile;
import xyz.damt.profiles.ProfileHandler;
import xyz.damt.scoreboard.Adapter;
import xyz.damt.tasks.MongoSaveTask;
import xyz.damt.util.ConfigFile;
import xyz.damt.util.assemble.Assemble;
import xyz.damt.util.assemble.AssembleStyle;


@Getter
public final class Soup extends JavaPlugin {

    private ConfigHandler configHandler;
    private ProfileHandler profileHandler;
    private KitHandler kitHandler;
    //
    private ConfigFile kitsYML;
    private ConfigFile profilesYML;
    //
    private MongoClient client;
    private MongoDatabase mongoDatabase;
    private MongoCollection<Document> profiles;
    private MongoCollection<Document> kits;

    @Override
    public void onLoad() {
        this.saveDefaultConfig();
    }

    @Override
    public void onEnable() {
        this.configHandler = new ConfigHandler();

        if (!configHandler.getSettingsHandler().USE_MONGO) {
            this.kitsYML = new ConfigFile(getDataFolder(), "kits.yml");
            this.profilesYML = new ConfigFile(getDataFolder(), "profiles.yml");
        }

        this.loadDatabase();

        this.profileHandler = new ProfileHandler();
        this.profileHandler.loadAllProfiles();

        this.kitHandler = new KitHandler();
        this.kitHandler.loadAllKits();

        Assemble assemble = new Assemble(this, new Adapter());
        assemble.setAssembleStyle(AssembleStyle.KOHI);
        assemble.setTicks(2);

        if (configHandler.getSettingsHandler().USE_MONGO)
        new MongoSaveTask().runTaskTimerAsynchronously(this, 300 * 20L, 300 * 20L);
    }

    @Override
    public void onDisable() {
        this.profileHandler.getAllProfiles().forEach(Profile::save);
        this.kitHandler.getAllKits().forEach(kit -> kit.save(configHandler.getSettingsHandler().USE_MONGO));
    }

    private void loadDatabase() {
        if (!configHandler.getSettingsHandler().USE_MONGO) return;

        if (configHandler.getDatabase().MONGO_HAS_AUTH) {
            client = new MongoClient(
                    new ServerAddress(configHandler.getDatabase().MONGO_HOST, configHandler.getDatabase().MONGO_PORT),
                    MongoCredential.createCredential(
                            configHandler.getDatabase().MONGO_USERNAME,
                            configHandler.getDatabase().MONGO_AUTH_DATABASE,
                            configHandler.getDatabase().MONGO_PASSWORD.toCharArray()
                    ),
                    MongoClientOptions.builder().build()
            );
            client = new MongoClient(configHandler.getDatabase().MONGO_HOST, configHandler.getDatabase().MONGO_PORT);
        } else {
            client = new MongoClient(configHandler.getDatabase().MONGO_HOST, configHandler.getDatabase().MONGO_PORT);
        }

        mongoDatabase = client.getDatabase("soup");

        profiles = mongoDatabase.getCollection("profiles");
        kits = mongoDatabase.getCollection("kits");
    }

}
