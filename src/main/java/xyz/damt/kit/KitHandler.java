package xyz.damt.kit;

import lombok.Getter;
import org.bson.Document;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.damt.Soup;
import xyz.damt.util.CC;
import xyz.damt.util.Serializer;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.function.Consumer;

public class KitHandler {

    private final Soup soup;
    @Getter private final HashMap<String, Kit> kitMap;

    public KitHandler() {
        this.soup = JavaPlugin.getPlugin(Soup.class);
        this.kitMap = new HashMap<>();
    }

    public void loadAllKits() {
        soup.getServer().getScheduler().runTaskAsynchronously(soup, () -> {
            if (!soup.getConfigHandler().getSettingsHandler().USE_MONGO) {
                soup.getKitsYML().getConfig().getConfigurationSection("").getKeys(false).forEach(s -> {
                    Kit kit = null;
                    try {
                        kit = new Kit(s, Serializer.itemStackArrayFromBase64(soup.getConfig().getString(s + ".contents")),
                                Serializer.itemStackArrayFromBase64(soup.getConfig().getString(s + ".armor")));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    assert kit != null;
                    kit.setEffects(CC.deserializePotionEffects(soup.getConfig().getStringList(s + ".effects")));
                });
                return;
            }

           soup.getKits().find().forEach((Consumer<? super Document>) document -> {
               Kit kit = null;
               try {
                   kit = new Kit(document.getString("_id"), Serializer.itemStackArrayFromBase64(document.getString("contents")),
                           Serializer.itemStackArrayFromBase64(document.getString("armor")));
               } catch (IOException e) {
                   e.printStackTrace();
               }
               assert kit != null;
               kit.setEffects(CC.deserializePotionEffects(document.getList("effects", String.class)));
           });
        });
    }

    public Collection<Kit> getAllKits() {
        return kitMap.values();
    }

    public Kit getKitByName(String name) {
        return kitMap.get(name);
    }

}
