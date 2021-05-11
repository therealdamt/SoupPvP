package xyz.damt.handlers;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.damt.Soup;

public class LocationHandler {

    private final Soup soup;
    private Location spawnLocation;

    public LocationHandler() {
        this.soup = JavaPlugin.getPlugin(Soup.class);
        this.reload();
    }

    public void reload() {
        World world = soup.getServer().getWorld(soup.getConfig().getString("location.spawn.world"));
        double x = soup.getConfig().getDouble("location.spawn.x");
        double y = soup.getConfig().getDouble("location.spawn.y");
        double z = soup.getConfig().getDouble("location.spawn.z");

        float yaw = soup.getConfig().getLong("location.spawn.yaw");
        float pitch = soup.getConfig().getLong("location.spawn.pitch");

        this.spawnLocation = new Location(world, x, y, z, yaw, pitch);
    }

    public void setSpawnLocation(Location location) {
        soup.getServer().getScheduler().runTaskAsynchronously(soup, () -> {
            soup.getConfig().set("location.spawn.world", location.getWorld().getName());
            soup.getConfig().set("location.spawn.x", location.getX());
            soup.getConfig().set("location.spawn.y", location.getY());
            soup.getConfig().set("location.spawn.z", location.getZ());
            soup.getConfig().set("location.spawn.yaw", location.getYaw());
            soup.getConfig().set("location.spawn.pitch", location.getPitch());

            soup.saveConfig();

            this.spawnLocation = location;
        });
    }

    public Location getSpawnLocation() {
        return this.spawnLocation;
    }

}
