package xyz.damt.config.other;

import org.bukkit.configuration.file.FileConfiguration;
import xyz.damt.util.CC;

public class MessageHandler {

    public final String DEATH_MESSAGE;

    public MessageHandler(FileConfiguration soup) {
        this.DEATH_MESSAGE = CC.translate(soup.getString("messages.death-message"));
    }

}
