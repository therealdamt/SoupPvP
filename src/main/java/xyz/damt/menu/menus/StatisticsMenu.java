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
import java.util.Collections;
import java.util.HashMap;

public class StatisticsMenu extends Menu {

    @Override
    public HashMap<Integer, ItemStack> getButtons(Player player) {
        final HashMap<Integer, ItemStack> itemMap = new HashMap<>();
        Profile profile = soup.getProfileHandler().getProfileByUUID(player.getUniqueId());

        ItemStack kills = new ItemBuilder(Material.DIAMOND_SWORD).name("&b&lKills").lore(Collections.singletonList(
                "&b" + profile.getKills())).build();
        itemMap.put(20, kills);

        ItemStack deaths = new ItemBuilder(Material.REDSTONE_BLOCK).name("&c&lDeaths").lore(Collections.singletonList(
                "&c" + profile.getDeaths())).build();
        itemMap.put(22, deaths);

        ItemStack coins = new ItemBuilder(Material.GOLD_INGOT).name("&6&lCoins").lore(Collections.singletonList(
                "&6" + profile.getCoins())).build();
        itemMap.put(24, coins);

        ItemStack soupsUsed = new ItemBuilder(Material.MUSHROOM_SOUP).name("&b&lSoups Used").lore(Collections.singletonList(
                "&b" + profile.getSoupsUsed())).build();
        itemMap.put(40, soupsUsed);

        return itemMap;
    }

    @Override
    public String getMenuName() {
        return "Statistics Menu";
    }
}
