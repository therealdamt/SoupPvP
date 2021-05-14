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
import xyz.damt.api.SoupAPI;
import xyz.damt.commands.*;
import xyz.damt.commands.admin.DebugCommand;
import xyz.damt.config.ConfigHandler;
import xyz.damt.handlers.ServerHandler;
import xyz.damt.kit.KitHandler;
import xyz.damt.listeners.ServerListener;
import xyz.damt.listeners.SoupListener;
import xyz.damt.placeholder.PlaceHolderExpansion;
import xyz.damt.profiles.Profile;
import xyz.damt.profiles.ProfileHandler;
import xyz.damt.profiles.ProfileListener;
import xyz.damt.scoreboard.Adapter;
import xyz.damt.tasks.MongoSaveTask;
import xyz.damt.util.CC;
import xyz.damt.util.ConfigFile;
import xyz.damt.util.assemble.Assemble;
import xyz.damt.util.assemble.AssembleStyle;
import xyz.damt.util.framework.command.BaseCommand;
import xyz.damt.util.framework.listener.ListenerAdapter;


@Getter
public final class Soup extends JavaPlugin {

    @Getter private static Soup instance;
    private SoupAPI soupAPI;

    private ConfigHandler configHandler;
    private ProfileHandler profileHandler;
    private ServerHandler serverHandler;
    private KitHandler kitHandler;
    //
    private ConfigFile kitsYML;
    private ConfigFile profilesYML;
    private ConfigFile messagesYML;
    //
    private MongoClient client;
    private MongoDatabase mongoDatabase;
    private MongoCollection<Document> profiles;
    private MongoCollection<Document> kits;

    @Override
    public void onLoad() {
        instance = this;

        this.saveDefaultConfig();
        this.messagesYML = new ConfigFile(getDataFolder(), "messages.yml");
    }

    @Override
    public void onEnable() {
        this.configHandler = new ConfigHandler();
        this.serverHandler = new ServerHandler();

        if (!configHandler.getSettingsHandler().USE_MONGO) {
            this.kitsYML = new ConfigFile(getDataFolder(), "kits.yml");
            this.profilesYML = new ConfigFile(getDataFolder(), "profiles.yml");
        }

        if (configHandler.getSettingsHandler().USE_PLACEHOLDER_API) {
            if (new PlaceHolderExpansion(this).register()) {
                this.getServer().getConsoleSender().sendMessage(CC.translate("&7[&bSoup&7] &bHooked into PlaceHolderAPI!"));
            } else {
                this.getServer().getConsoleSender().sendMessage(CC.translate("&7[&bSoup&7] &cFailed to hook into PlaceHolderAPI!"));
            }
        }

        this.loadDatabase();

        this.profileHandler = new ProfileHandler();
        this.profileHandler.loadAllProfiles();

        this.kitHandler = new KitHandler();
        this.kitHandler.loadAllKits();

        Assemble assemble = new Assemble(this, new Adapter(this));
        assemble.setAssembleStyle(AssembleStyle.KOHI);
        assemble.setTicks(2);

        if (configHandler.getSettingsHandler().USE_MONGO)
        new MongoSaveTask(this).runTaskTimerAsynchronously(this, 300 * 20L, 300 * 20L);

        this.soupAPI = new SoupAPI();

        // Commands
        new KitCommand();
        new KitsCommand();
        new PayCommand();
        new StatsCommand();
        new DebugCommand();
        new BalanceCommand();
        new BuildCommand();
        // Listeners
        new ProfileListener();
        new ServerListener();
        new SoupListener();
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
