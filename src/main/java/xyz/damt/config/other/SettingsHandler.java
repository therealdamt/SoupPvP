package xyz.damt.config.other;

import org.bukkit.configuration.file.FileConfiguration;

public class SettingsHandler {

    public final boolean ANNOUNCE_AUTO_SAVE;
    public final int KILL_REWARD;
    public final int DEATH_LOSS;
    public final double HEALTH_INCREASE;
    public final boolean USE_MONGO;
    public final boolean USE_PLACEHOLDER_API;
    public final int MAX_NUMBER_OF_PLAYERS_PER_GUILD;
    public final boolean GUILDS_IS_ENABLED;

    public SettingsHandler(FileConfiguration soup) {
        this.ANNOUNCE_AUTO_SAVE = soup.getBoolean("settings.announce-auto-save");
        this.KILL_REWARD = soup.getInt("settings.kill-reward");
        this.DEATH_LOSS = soup.getInt("settings.death-loss");
        this.HEALTH_INCREASE = soup.getInt("settings.health-increase");
        this.USE_MONGO = soup.getBoolean("settings.mongo");
        this.USE_PLACEHOLDER_API = soup.getBoolean("settings.use-placeholder-api");
        this.GUILDS_IS_ENABLED = soup.getBoolean("settings.enable-guilds");
        this.MAX_NUMBER_OF_PLAYERS_PER_GUILD = soup.getInt("settings.max-number-of-players-per-guild");
    }

}
