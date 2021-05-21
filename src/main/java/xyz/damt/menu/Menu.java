package xyz.damt.menu;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import xyz.damt.util.framework.listener.ListenerAdapter;

import java.util.HashMap;

public abstract class Menu extends ListenerAdapter {

    public abstract HashMap<Integer, ItemStack> getButtons(Player player);

    public void openMenu(Player player) {
        Inventory inventory = soup.getServer().createInventory(null, getMenuSize(), getMenuName());

        getButtons(player).keySet().forEach(integer -> {
            inventory.setItem(integer, getButtons(player).get(integer));
        });

        player.openInventory(inventory);
    }

    public void execute(Player player, InventoryAction action, ClickType clickType, Inventory inventory, ItemStack stack) {

    }

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player)) return;
        if (e.getClickedInventory() == null) return;
        if (!e.getClickedInventory().getTitle().equalsIgnoreCase(ChatColor.stripColor(getMenuName()))) return;
        if (e.getCurrentItem() != null) e.setCancelled(true);

        Player player = (Player) e.getWhoClicked();
        execute(player, e.getAction(), e.getClick(), e.getClickedInventory(), e.getCurrentItem());
    }

    public String getMenuName() {
        return "";
    }

    public int getMenuSize() {
        return 54;
    }


}
