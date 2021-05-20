package xyz.damt.tasks;

import org.bukkit.scheduler.BukkitRunnable;
import xyz.damt.Soup;
import xyz.damt.kit.Kit;
import xyz.damt.profiles.Profile;
import xyz.damt.util.CC;

public class MongoSaveTask extends BukkitRunnable {

    private final Soup soup;

    public MongoSaveTask(Soup soup) {
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
            return;
        }

        soup.getServer().broadcastMessage(CC.translate("&7[&aSoup&7] &aAuto Saving...."));

        for (Profile profile : soup.getProfileHandler().getAllProfiles()) {
            soup.getServer().getScheduler().runTaskAsynchronously(soup, profile::save);
        }

        for (Kit kit : soup.getKitHandler().getAllKits()) {
            soup.getServer().getScheduler().runTaskAsynchronously(soup, () -> kit.save(soup.getConfigHandler().getSettingsHandler().USE_MONGO));
        }

        soup.getServer().broadcastMessage(CC.translate("&7[&aSoup&7] &aAuto Save Completed!"));
    }
}
