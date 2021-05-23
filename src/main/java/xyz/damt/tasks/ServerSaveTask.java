package xyz.damt.tasks;

import org.bukkit.scheduler.BukkitRunnable;
import xyz.damt.Soup;
import xyz.damt.guild.Guild;
import xyz.damt.kit.Kit;
import xyz.damt.profiles.Profile;
import xyz.damt.util.CC;

public class ServerSaveTask extends BukkitRunnable {

    private final Soup soup;

    public ServerSaveTask(Soup soup) {
        this.soup = soup;
    }

    @Override
    public void run() {
        if (!soup.getConfigHandler().getSettingsHandler().ANNOUNCE_AUTO_SAVE) {
            for (Profile profile : soup.getProfileHandler().getAllProfiles()) {
                soup.getServer().getScheduler().runTaskAsynchronously(soup, profile::save);
            }

            for (Kit kit : soup.getKitHandler().getAllKits()) {
                soup.getServer().getScheduler().runTaskAsynchronously(soup, () -> kit.save(soup.getConfigHandler().getSettingsHandler().USE_MONGO));
            }

            if (soup.getConfigHandler().getSettingsHandler().GUILDS_IS_ENABLED) {
                for (Guild guild : soup.getGuildHandler().getAllGuilds()) {
                    soup.getServer().getScheduler().runTaskAsynchronously(soup, () -> guild.save(soup.getConfigHandler().getSettingsHandler().USE_MONGO));
                }
            }
            return;
        }

        soup.getServer().broadcastMessage(CC.translate("&7[&aSoup&7] &aAuto Saving...."));

        for (Profile profile : soup.getProfileHandler().getAllProfiles()) {
            soup.getServer().getScheduler().runTaskAsynchronously(soup, profile::save);
        }

        for (Kit kit : soup.getKitHandler().getAllKits()) {
            soup.getServer().getScheduler().runTaskAsynchronously(soup, () -> kit.save(soup.getConfigHandler().getSettingsHandler().USE_MONGO));
        }

        if (soup.getConfigHandler().getSettingsHandler().GUILDS_IS_ENABLED) {
            for (Guild guild : soup.getGuildHandler().getAllGuilds()) {
                soup.getServer().getScheduler().runTaskAsynchronously(soup, () -> guild.save(soup.getConfigHandler().getSettingsHandler().USE_MONGO));
            }
        }

        soup.getServer().broadcastMessage(CC.translate("&7[&aSoup&7] &aAuto Save Completed!"));
    }
}
