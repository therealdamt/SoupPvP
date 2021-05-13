package xyz.damt.menu.menus;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import xyz.damt.menu.Menu;
import xyz.damt.profiles.Profile;
import xyz.damt.util.CC;
import xyz.damt.util.ItemBuilder;

import java.util.Arrays;
import java.util.HashMap;

public class StatisticsMenu extends Menu {

    public StatisticsMenu(Player player) {
        super(player);
        this.menuName = player.getName() + "'s stats";
    }

    @Override
    public HashMap<Integer, ItemStack> getButtons(Player player) {
        final HashMap<Integer, ItemStack> itemMap = new HashMap<>();
        Profile profile = soup.getProfileHandler().getProfileByUUID(player.getUniqueId());

        ItemStack kills = new ItemBuilder(Material.DIAMOND_SWORD).name("&b&lKills").lore(Arrays.asList(" ",
                "&bKills&7: " + profile.getKills())).build();
        itemMap.put(20, kills);

        ItemStack deaths = new ItemBuilder(Material.REDSTONE_BLOCK).name("&c&lDeaths").lore(Arrays.asList(" ",
                "&cDeaths&7: " + profile.getDeaths())).build();
        itemMap.put(22, deaths);

        ItemStack coins = new ItemBuilder(Material.GOLD_INGOT).name("&6&lCoins").lore(Arrays.asList(" ",
                "&6Coins&7: " + profile.getCoins())).build();
        itemMap.put(24, coins);

        ItemStack soupsUsed = new ItemBuilder(Material.MUSHROOM_SOUP).name("&b&lSoups Used").lore(Arrays.asList(" ",
                "&bSoups Used&7: " + profile.getSoupsUsed())).build();
        itemMap.put(40, soupsUsed);

        return itemMap;
    }

}
