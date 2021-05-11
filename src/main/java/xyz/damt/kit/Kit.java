package xyz.damt.kit;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import lombok.Getter;
import lombok.Setter;
import org.bson.Document;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import xyz.damt.Soup;
import xyz.damt.util.CC;
import xyz.damt.util.Serializer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Getter @Setter
public class Kit {

    private final Soup soup;
    private final Executor kitThread;

    private String kitName;
    private ItemStack[] contents;
    private ItemStack[] armorContents;
    private List<PotionEffect> effects;

    public Kit(String kitName, ItemStack[] armorContents, ItemStack[] contents) {
        this.soup = JavaPlugin.getPlugin(Soup.class);

        this.kitThread = soup.getKitsThread();
        this.kitName = kitName;
        this.contents = contents;
        this.armorContents = armorContents;
        this.effects = new ArrayList<>();

        this.soup.getKitHandler().getKitMap().put(kitName, this);
    }

    public void setKitName(String name) {
        this.soup.getKitHandler().getKitMap().remove(kitName);

        this.kitName = name;
        this.soup.getKitHandler().getKitMap().put(kitName, this);
    }

    public void applyToUser(Player player) {
        player.getInventory().setArmorContents(armorContents);
        player.getInventory().setContents(contents);

        effects.forEach(player::addPotionEffect);
    }

    public void addEffect(PotionEffect effect) {
        this.effects.add(effect);
    }

    public void removeEffects(PotionEffect effect) {
        this.effects.remove(effect);
    }

    public void save() {
        kitThread.execute(() -> {
            Document document = soup.getKits().find(new Document("_id", kitName)).first();

            if (document == null) {
                Document newDocument = new Document("_id", kitName).append("contents", Serializer.itemStackArrayToBase64(contents))
                        .append("armor", Serializer.itemStackArrayToBase64(armorContents)).append("effects", CC.serializePotionEffects(effects));
                soup.getKits().insertOne(newDocument);
                return;
            }

            updateDocument(document, "contents", Serializer.itemStackArrayToBase64(contents));
            updateDocument(document, "armor", Serializer.itemStackArrayToBase64(armorContents));
            updateDocument(document, "effects", CC.serializePotionEffects(effects));
        });
    }

    private void updateDocument(Document document, String key, Object value) {
        if (document != null) {
            document.put(key, value);
            kitThread.execute(() ->
                    soup.getProfiles().replaceOne(Filters.eq("_id", kitName)
                            , document, new ReplaceOptions().upsert(true)));
        }

    }

}
