package xyz.damt.kit;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import lombok.Getter;
import lombok.Setter;
import org.bson.Document;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import xyz.damt.Soup;
import xyz.damt.util.CC;
import xyz.damt.util.Serializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Kit {

    private final Soup soup;

    private String kitName;

    private ItemStack[] contents;
    private ItemStack[] armorContents;

    private List<PotionEffect> effects;
    private ItemStack icon;

    public Kit(String kitName, ItemStack[] contents, ItemStack[] armorContents) {
        this.soup = Soup.getInstance();
        this.kitName = kitName;

        this.contents = contents;
        this.armorContents = armorContents;

        this.effects = new ArrayList<>();
        this.icon = new ItemStack(Material.DIAMOND_SWORD);

        this.soup.getKitHandler().getKitMap().put(kitName.toLowerCase(), this);
    }

    public void setKitName(String name) {
        this.soup.getKitHandler().getKitMap().remove(kitName);

        this.kitName = name;
        this.soup.getKitHandler().getKitMap().put(kitName, this);
    }

    public void applyToUser(Player player) {
        player.getInventory().setContents(contents);
        player.getInventory().setArmorContents(armorContents);

        effects.forEach(player::addPotionEffect);
    }

    public void addEffect(PotionEffect effect) {
        this.effects.add(effect);
    }

    public void removeEffects(PotionEffect effect) {
        this.effects.remove(effect);
    }

    public void save(boolean mongo) {
        if (!mongo) {
            soup.getKitsYML().getConfig().set(kitName + ".contents", Serializer.itemStackArrayToBase64(contents));
            soup.getKitsYML().getConfig().set(kitName + ".armor", Serializer.itemStackArrayToBase64(armorContents));
            soup.getKitsYML().getConfig().set(kitName + ".effects", CC.serializePotionEffects(effects));
            soup.getKitsYML().getConfig().set(kitName + ".icon", icon.getType().toString());

            try {
                soup.getKitsYML().save();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return;
        }

        Document document = soup.getKits().find(new Document("_id", kitName)).first();

        if (document == null) {
            Document newDocument = new Document("_id", kitName).append("contents", Serializer.itemStackArrayToBase64(contents))
                    .append("armor", Serializer.itemStackArrayToBase64(armorContents)).append("effects", CC.serializePotionEffects(effects))
                    .append("icon", icon.getType().toString());
            soup.getKits().insertOne(newDocument);
            return;
        }

        updateDocument(document, "contents", Serializer.itemStackArrayToBase64(contents));
        updateDocument(document, "armor", Serializer.itemStackArrayToBase64(armorContents));
        updateDocument(document, "effects", CC.serializePotionEffects(effects));
        updateDocument(document, "icon", icon.getType().toString());
    }

    private void updateDocument(Document document, String key, Object value) {
        if (document != null) {
            document.put(key, value);
            soup.getServer().getScheduler().runTaskAsynchronously(soup, () -> {
                soup.getProfiles().replaceOne(Filters.eq("_id", kitName)
                        , document, new ReplaceOptions().upsert(true));
            });
        }
    }

}
