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
import xyz.damt.handlers.LocationHandler;
import xyz.damt.kit.Kit;
import xyz.damt.kit.KitHandler;
import xyz.damt.profiles.Profile;
import xyz.damt.profiles.ProfileHandler;
import xyz.damt.scoreboard.Adapter;
import xyz.damt.tasks.MongoSaveTask;
import xyz.damt.util.assemble.Assemble;
import xyz.damt.util.assemble.AssembleStyle;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class Soup extends JavaPlugin {

    @Getter private ConfigHandler configHandler;
    @Getter private ProfileHandler profileHandler;
    @Getter private LocationHandler locationHandler;
    @Getter private KitHandler kitHandler;
    //
    @Getter private MongoClient client;
    @Getter private MongoDatabase mongoDatabase;
    @Getter private MongoCollection<Document> profiles;
    @Getter private MongoCollection<Document> kits;

    @Override
    public void onLoad() {
        this.saveDefaultConfig();
    }

    @Override
    public void onEnable() {
        this.objects();

        this.profileHandler = new ProfileHandler();
        this.profileHandler.loadAllProfiles();

        this.kitHandler = new KitHandler();
        this.kitHandler.loadAllKits();

        Assemble assemble = new Assemble(this, new Adapter());
        assemble.setAssembleStyle(AssembleStyle.KOHI);
        assemble.setTicks(2);

        new MongoSaveTask().runTaskTimerAsynchronously(this, 300 * 20L, 300 * 20L);
    }

    @Override
    public void onDisable() {
        this.profileHandler.getAllProfiles().forEach(Profile::save);
        this.kitHandler.getAllKits().forEach(Kit::save);
    }

    private void objects() {
        this.locationHandler = new LocationHandler();
        this.configHandler = new ConfigHandler();
        this.loadDatabase();
    }

    private void loadDatabase() {
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

        System.setProperty("DEBUG.GO", "true");
        System.setProperty("DB.TRACE", "true");
        Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
        mongoLogger.setLevel(Level.WARNING);
    }

}
