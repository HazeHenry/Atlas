package me.hazehenry.atlas.menus;

import me.hazehenry.atlas.managers.ItemBuilder;
import me.hazehenry.atlas.messages.Messages;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class AtlasMenu implements InventoryHolder {

    private final Player player;
    private final Inventory inventory;

    public AtlasMenu(Player player) {
        this.player = player;
        this.inventory = Bukkit.createInventory(this, 5 * 9, Messages.MENU_TITLE);

        build();
    }

    public void open() {
        player.openInventory(inventory);
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    public void build() {
        int[] border = {0,1,2,3,4,5,6,7,8,9,17,18,26,27,35,36,37,38,39,41,42,43,44};

        for (int i : border) {
            inventory.setItem(i, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
        }

//        inventory.setItem(36, new ItemBuilder(Material.ARROW)
//                .name(Messages.MENU_PREVIOUS_ITEM_NAME)
//                .build());

        inventory.setItem(40, new ItemBuilder(Material.BARRIER)
                .name(Messages.MENU_CLOSE_ITEM_NAME)
                .build());

        // chapters

        inventory.setItem(20, new ItemBuilder(Material.DIAMOND)
                .name(Messages.MENU_CREATE_CHAPTER_ITEM_NAME)
                .build());

        inventory.setItem(11, new ItemBuilder(Material.SLIME_BALL)
                .name(Messages.MENU_OVERVIEW_CHAPTERS_ITEM_NAME)
                .build());

        inventory.setItem(29, new ItemBuilder(Material.FIRE_CHARGE)
                .name(Messages.MENU_DELETE_CHAPTER_ITEM_NAME)
                .build());


        // quests

        inventory.setItem(15, new ItemBuilder(Material.ENDER_EYE)
                .name(Messages.MENU_OVERVIEW_QUESTS_ITEM_NAME)
                .build());

        inventory.setItem(24, new ItemBuilder(Material.NETHER_STAR)
                .name(Messages.MENU_CREATE_QUEST_ITEM_NAME)
                .build());

        inventory.setItem(33, new ItemBuilder(Material.FEATHER)
                .name(Messages.MENU_DELETE_QUEST_ITEM_NAME)
                .build());


        inventory.setItem(22, new ItemBuilder(Material.WRITABLE_BOOK)
                .name(Messages.MENU_MAIN_ITEM_NAME)
                .lore(Messages.MENU_MAIN_ITEM_LORE)
                .build());
    }
}
