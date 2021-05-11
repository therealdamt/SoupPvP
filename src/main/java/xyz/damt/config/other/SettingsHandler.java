package xyz.damt.config.other;

import org.bukkit.configuration.file.FileConfiguration;
public class SettingsHandler {

    public final boolean ANNOUNCE_AUTO_SAVE;
    public final int KILL_REWARD;
    public final int DEATH_LOSS;
    public final boolean USE_MONGO;

    public SettingsHandler(FileConfiguration soup) {
        this.ANNOUNCE_AUTO_SAVE = soup.getBoolean("settings.announce-auto-save");
        this.KILL_REWARD = soup.getInt("settings.kill-reward");
        this.DEATH_LOSS = soup.getInt("settings.death-loss");
        this.USE_MONGO = soup.getBoolean("settings.mongo");
    }

}
