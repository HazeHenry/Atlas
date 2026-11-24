package me.hazehenry.atlas.menus.listeners;

import me.hazehenry.atlas.menus.AtlasMenu;
import me.hazehenry.atlas.menus.questmenu.QuestMenu;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class AtlasMenuListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (!(e.getInventory().getHolder() instanceof AtlasMenu)) return;

        Player p = (Player) e.getWhoClicked();
        int slot = e.getRawSlot();

        e.setCancelled(true);

        if (slot == 40) p.closeInventory();

        if (slot == 24) new QuestMenu(p).open();
    }
}
