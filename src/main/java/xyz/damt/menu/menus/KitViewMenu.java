package xyz.damt.menu.menus;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import xyz.damt.kit.Kit;
import xyz.damt.menu.Menu;
import xyz.damt.util.CC;
import xyz.damt.util.ItemBuilder;

import java.util.HashMap;

public class KitViewMenu extends Menu {

    private final Kit kit;

    public KitViewMenu(Kit kit) {
        this.kit = kit;
    }

    @Override
    public void execute(Player player, InventoryAction action, ClickType clickType, Inventory inventory, ItemStack stack) {
        if (stack.getItemMeta() == null || stack.getItemMeta().getDisplayName() == null) return;
        if (!stack.getItemMeta().getDisplayName().equalsIgnoreCase(CC.translate("&c&lReturn To Kits Menu"))) return;

        player.closeInventory();
        new KitsMenu().openMenu(player);
    }

    @Override
    public HashMap<Integer, ItemStack> getButtons(Player player) {
        final HashMap<Integer, ItemStack> buttons = new HashMap<>();

        for (int i = 0; i < kit.getContents().length; i++) {
            buttons.put(i, kit.getContents()[i]);
        }

        for (int i = 45; i < kit.getArmorContents().length; i++) {
             buttons.put(i, kit.getArmorContents()[i]);
        }

        buttons.put(53, new ItemBuilder(Material.BOOK).name(CC.translate("&c&lReturn To Kits Menu"))
                .lore("&7Click this to return to the kits menu!").build());

        return buttons;
    }

    @Override
    public String getMenuName() {
        return kit.getKitName() + "'s Preview";
    }

    @Override
    public int getMenuSize() {
        return 54;
    }
}
