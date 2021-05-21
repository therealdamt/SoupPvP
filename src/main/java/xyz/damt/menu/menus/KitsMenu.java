package xyz.damt.menu.menus;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import xyz.damt.api.events.KitApplyEvent;
import xyz.damt.kit.Kit;
import xyz.damt.menu.Menu;
import xyz.damt.profiles.Profile;
import xyz.damt.util.CC;
import xyz.damt.util.ItemBuilder;

import java.util.Arrays;
import java.util.HashMap;

public class KitsMenu extends Menu {

    @Override
    public void execute(Player player, InventoryAction action, ClickType clickType, Inventory inventory, ItemStack stack) {
        if (stack.getItemMeta() == null || stack.getItemMeta().getDisplayName() == null) return;

        String kitName = ChatColor.stripColor(stack.getItemMeta().getDisplayName());
        Profile playerProfile = soup.getProfileHandler().getProfileByUUID(player.getUniqueId());

        Kit kit = soup.getKitHandler().getKitByName(kitName);

        if (kit == null) {
            player.sendMessage(CC.translate("&cLooks like this is an invalid kit!"));
            return;
        }

        if (clickType.isRightClick()) {
            new KitViewMenu(kit).openMenu(player);
            return;
        }

        if (playerProfile.getTimerCooldown().isOnCooldown(player)) {
            player.sendMessage(CC.translate("&cYou must wait until you are off combat timer to choose a kit!"));
            player.closeInventory();
            return;
        }

        KitApplyEvent kitApplyEvent = new KitApplyEvent(player, kit);
        soup.getServer().getPluginManager().callEvent(kitApplyEvent);

        if (kitApplyEvent.isCancelled()) return;

        kitApplyEvent.getKit().applyToUser(player);
        player.sendMessage(soup.getConfigHandler().getMessageHandler().APPLIED_KIT
                .replace("{kit}", kitApplyEvent.getKit().getKitName()));
    }

    @Override
    public HashMap<Integer, ItemStack> getButtons(Player player) {
        final HashMap<Integer, ItemStack> buttons = new HashMap<>();

        int i = 0;
        for (Kit kit : soup.getKitHandler().getAllKits()) {
            ItemStack stack = new ItemBuilder(kit.getIcon().getType()).name(CC.translate("&b" + kit.getKitName()))
                    .lore(Arrays.asList(
                            " ",
                            CC.translate("&bArmor Pieces Count&7: " + kit.getArmorContents().length),
                            CC.translate("&bStorage Contents Count&7: " + kit.getContents().length),
                            " ",
                            CC.translate("&7Left Click To Select || Right Click To View")
                    )).build();
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
