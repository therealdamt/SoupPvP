package xyz.damt.profiles;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import xyz.damt.util.CC;
import xyz.damt.util.framework.listener.ListenerAdapter;

public class ProfileListener extends ListenerAdapter {

    @EventHandler
    public void onAsyncPlayerJoin(AsyncPlayerPreLoginEvent e) {
        Player player = soup.getServer().getPlayer(e.getUniqueId());

        if (player != null) {
            e.setKickMessage(CC.translate("&cYou are already online!"));
            e.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_OTHER);
            return;
        }

        if (!soup.getProfileHandler().hasProfile(e.getUniqueId())) {
            new Profile(e.getUniqueId(), soup.getConfigHandler().getSettingsHandler().USE_MONGO).save();
        }
    }

}
