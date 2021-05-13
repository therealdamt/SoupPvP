package xyz.damt.config;

import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.damt.Soup;
import xyz.damt.config.other.MessageHandler;
import xyz.damt.config.other.ScoreboardHandler;
import xyz.damt.config.other.SettingsHandler;
import xyz.damt.config.other.DatabaseHandler;

public class ConfigHandler {

    private final Soup soup;

    @Getter private DatabaseHandler database;
    @Getter private SettingsHandler settingsHandler;
    @Getter private MessageHandler messageHandler;
    @Getter private ScoreboardHandler scoreboardHandler;

    public ConfigHandler() {
        this.soup = JavaPlugin.getPlugin(Soup.class);
        this.reload();
    }

    public void reload() {
        this.database = new DatabaseHandler(soup.getConfig());
        this.messageHandler = new MessageHandler(soup.getMessagesYML().getConfig());
        this.settingsHandler = new SettingsHandler(soup.getConfig());
        this.scoreboardHandler = new ScoreboardHandler(soup.getConfig());
    }

}
