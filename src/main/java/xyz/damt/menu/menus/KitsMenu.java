package xyz.damt.menu.menus;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import xyz.damt.kit.Kit;
import xyz.damt.menu.Menu;
import xyz.damt.profiles.Profile;
import xyz.damt.util.CC;
import xyz.damt.util.ItemBuilder;

import java.util.HashMap;

public class KitsMenu extends Menu {

    public KitsMenu(Player player) {
        super(player);
    }

    @Override
    public void execute(Player player, InventoryAction action, ClickType clickType, Inventory inventory, ItemStack stack) {
        String kitName = ChatColor.stripColor(stack.getItemMeta().getDisplayName());
        Profile playerProfile = soup.getProfileHandler().getProfileByUUID(player.getUniqueId());

        Kit kit = soup.getKitHandler().getKitByName(kitName);

        if (kit == null) {
            player.sendMessage(CC.translate("&cLooks like this is an invalid kit!"));
            return;
        }

        if (playerProfile.getTimerCooldown().isOnCooldown(player)) {
            player.sendMessage(CC.translate("&cYou must wait until you are off combat timer to choose a kit!"));
            player.closeInventory();
            return;
        }

        kit.applyToUser(player);
        player.sendMessage(soup.getConfigHandler().getMessageHandler().APPLIED_KIT.replace("{kit}", kit.getKitName()));
    }

    @Override
    public HashMap<Integer, ItemStack> getButtons(Player player) {
        final HashMap<Integer, ItemStack> buttons = new HashMap<>();

        int i = 0;
        for (Kit kit : soup.getKitHandler().getAllKits()) {
            ItemStack stack = new ItemBuilder(kit.getIcon().getType()).name(CC.translate("&a" + kit.getKitName())).build();
            if (!buttons.containsValue(stack)) {
                buttons.put(i, stack);
                i++;
            }
        }

        return buttons;
    }


    @Override
    public String getMenuName() {
        return "Kits Menu";
    }
}
