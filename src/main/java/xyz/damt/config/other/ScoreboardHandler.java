package xyz.damt.config.other;

import org.bukkit.configuration.file.FileConfiguration;
import xyz.damt.util.CC;

import java.util.List;

public class ScoreboardHandler {

    public final List<String> IN_COMBAT;
    public final List<String> NORMAL;
    public final String SCOREBOARD_TITLE;

    public ScoreboardHandler(FileConfiguration soup) {
        this.IN_COMBAT = soup.getStringList("scoreboard.in-combat");
        this.NORMAL = soup.getStringList("scoreboard.normal");
        this.SCOREBOARD_TITLE = soup.getString(CC.translate("scoreboard.title"));
    }

}
