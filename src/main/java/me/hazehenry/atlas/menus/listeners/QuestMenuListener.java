package me.hazehenry.atlas.menus.listeners;

import me.hazehenry.atlas.Atlas;
import me.hazehenry.atlas.function.QuestType;
import me.hazehenry.atlas.function.helper.Quest;
import me.hazehenry.atlas.function.helper.Requirement;
import me.hazehenry.atlas.managers.AtlasManager;
import me.hazehenry.atlas.menus.questmenu.QuestMenu;
import me.hazehenry.atlas.menus.questmenu.function.ChatType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class QuestMenuListener implements Listener {

    private HashMap<UUID, Integer> confirmAbandon = new HashMap<>();

    private HashMap<UUID, ChatType> chatTypeMap = new HashMap<>();
    private HashMap<UUID, String> chatTypeReason = new HashMap<>();

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        String message = e.getMessage();

        if (!chatTypeMap.containsKey(p.getUniqueId())) return;
        Quest quest = AtlasManager.getEditingQuest().get(p.getUniqueId());
        int line = AtlasManager.getEditingLine().get(p.getUniqueId());

        e.setCancelled(true);

        if (!message.equalsIgnoreCase("cancel")) {
            switch (chatTypeMap.get(p.getUniqueId())) {
                case DISPLAYNAME:
                    quest.setName(message);
                    break;
                case ID:
                    quest.setId(message);
                    break;
                case DESCRIPTION:
                    List<String> description = quest.getDescription();
                    if (chatTypeReason.get(p.getUniqueId()).equalsIgnoreCase("add")) {
                        if (!message.equalsIgnoreCase("%empty%")) {
                            description.add(line + 1, message);
                        } else {
                            description.add(line + 1, "");
                        }
                        line++;
                    } else {
                        if (!message.equalsIgnoreCase("%empty%")) {
                            description.set(line, message);
                        } else {
                            description.set(line, "");
                        }
                    }

                    quest.setDescription(description);
                    break;
                case REQUIREMENT:
                    QuestType type = QuestType.getByName(chatTypeReason.get(p.getUniqueId()));
                    Requirement requirement = new Requirement();
                    requirement.setType(type);

                    switch (type) {
                        case COMPLETE_QUEST:
                            requirement.setQuestId(message);
                            break;
                        case EARN_EXPERIENCE:
                            try {
                                requirement.setXpLevels(Integer.parseInt(message));
                            } catch (NumberFormatException ignored) {}
                            break;
                        case KILL_PLAYER:
                            try {
                                requirement.setKills(Integer.parseInt(message));
                            } catch (NumberFormatException ignored) {}
                            break;
                    }

                    quest.addRequirement(requirement);
            }
        }

        chatTypeMap.remove(p.getUniqueId());
        chatTypeReason.remove(p.getUniqueId());
        AtlasManager.getEditingLine().put(p.getUniqueId(), line);
        AtlasManager.getEditingQuest().put(p.getUniqueId(), quest);
        Bukkit.getScheduler().runTask(Atlas.getInstance(), () -> new QuestMenu(p).open());
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (!(e.getInventory().getHolder() instanceof QuestMenu)) return;

        Player p = (Player) e.getWhoClicked();
        int slot = e.getRawSlot();

        e.setCancelled(true);
        Quest quest = AtlasManager.getEditingQuest().get(p.getUniqueId());
        int line = AtlasManager.getEditingLine().computeIfAbsent(p.getUniqueId(), k -> 0);
        int reqIndex = AtlasManager.getEditingReq().computeIfAbsent(p.getUniqueId(), k -> 0);
        int reqRemIndex = AtlasManager.getEditingRemoveReq().computeIfAbsent(p.getUniqueId(), k -> 0);

        if (slot == 10) {
            p.closeInventory();
            chatTypeMap.put(p.getUniqueId(), ChatType.DISPLAYNAME);
            p.sendMessage("§eSet Quest Display Name §7- §7Type in chat to set, type §ccancel §7to cancel.");
            return;
        }

        if (slot == 11) {
            List<String> description = quest.getDescription();

            String lineOther;
            String lineCurrent;

            switch (e.getClick()) {
                case RIGHT:
                    if (line == description.size() - 1) {
                        line = 0;
                    } else {
                        line++;
                    }
                    break;
                case LEFT:
                    chatTypeMap.put(p.getUniqueId(), ChatType.DESCRIPTION);
                    chatTypeReason.put(p.getUniqueId(), "modify");
                    p.closeInventory();
                    p.sendMessage("§eEditing line. §7- §7Type in chat to modify, type §ccancel §7to cancel. For an empty line, type §b%empty%§7.");
                    p.sendMessage("§7Line: §r" + description.get(line));
                    return;
                case MIDDLE, DROP:
                    chatTypeMap.put(p.getUniqueId(), ChatType.DESCRIPTION);
                    chatTypeReason.put(p.getUniqueId(), "add");
                    p.closeInventory();
                    p.sendMessage("§eEditing line. §7- §7Type in chat to add, type §ccancel §7to cancel. For an empty line, type §b%empty%§7.");
                    return;
                case SHIFT_LEFT:
                    try {
                        lineOther = description.get(line - 1);
                        lineCurrent = description.get(line);

                        description.set(line - 1, lineCurrent);
                        description.set(line, lineOther);

                        line--;
                    } catch (IndexOutOfBoundsException ignored) {
                        p.playSound(p, Sound.ENTITY_VILLAGER_NO,1f,1f);
                    }
                    break;
                case SHIFT_RIGHT:
                    try {
                        lineOther = description.get(line + 1);
                        lineCurrent = description.get(line);

                        description.set(line + 1, lineCurrent);
                        description.set(line, lineOther);

                        line++;
                    } catch (IndexOutOfBoundsException ignored) {
                        p.playSound(p, Sound.ENTITY_VILLAGER_NO,1f,1f);
                    }
                    break;
            }

            AtlasManager.getEditingLine().put(p.getUniqueId(), line);
        }

//        List<QuestType> nonChatTypes = List.of(
//                QuestType.CHECK_BOX,
//                QuestType.BREAK_BLOCK,
//                QuestType.ACQUIRE_ITEM,
//                QuestType.CRAFT_ITEM,
//                QuestType.CONSUME_FOOD,
//                QuestType.DEPOSIT_ITEM,
//                QuestType.KILL_MOB,
//                QuestType.PLACE_BLOCK,
//                QuestType.VISIT_LOCATION
//        );

        if (slot == 19) {
            switch (e.getClick()) {
                case LEFT:
                    if (!QuestType.questTypeList.get(reqIndex).isChatType()) {
                        Requirement requirement = new Requirement();

                        switch (QuestType.questTypeList.get(reqIndex)) {
                            case CRAFT_ITEM, DEPOSIT_ITEM, ACQUIRE_ITEM,
                                 CONSUME_FOOD:
                                p.sendMessage("§eAdding requirement. §7- §7Using your held item.");
                                requirement.setType(QuestType.questTypeList.get(reqIndex));

                                ItemStack item = p.getInventory().getItemInMainHand();
                                if (item.getType() == Material.AIR) {
                                    p.sendMessage("§cThe item cannot be nothing.");
                                    return;
                                }

                                requirement.setItem(item);
                                break;
                            case PLACE_BLOCK, BREAK_BLOCK:
                                p.sendMessage("§eAdding requirement. §7- §7Using your held block.");
                                requirement.setType(QuestType.questTypeList.get(reqIndex));
                                ItemStack block = p.getInventory().getItemInMainHand();

                                if (!block.getType().isBlock()) {
                                    p.sendMessage("§cThe item must be a block.");
                                    return;
                                }

                                requirement.setBlock(block);
                                break;
                            case CHECK_BOX:
                                p.sendMessage("§eAdding requirement. §7- §7Check box.");
                                requirement.setType(QuestType.questTypeList.get(reqIndex));
                                requirement.setCheckBox(true);
                                break;
                        }

                        quest.addRequirement(requirement);
                        AtlasManager.getEditingQuest().put(p.getUniqueId(), quest);
                        new QuestMenu(p).open();
                        return;
                    }

                    chatTypeMap.put(p.getUniqueId(), ChatType.REQUIREMENT);
                    chatTypeReason.put(p.getUniqueId(), QuestType.questTypeList.get(reqIndex).toString());
                    p.closeInventory();
                    p.sendMessage("§eAdding requirement. §7- §7Type in chat to set, type §ccancel §7to cancel.§7.");
                    return;
                case RIGHT:
                    if (reqIndex == QuestType.questTypeList.size() - 1) {
                        reqIndex = 0;
                    } else {
                        reqIndex++;
                    }
                    break;
            }

            AtlasManager.getEditingReq().put(p.getUniqueId(), reqIndex);
        }

        if (slot == 20) {
            switch (e.getClick()) {
                case LEFT:
                    List<Requirement> reqList = quest.getRequirements();
                    if (reqList.isEmpty()) break;

                    reqList.remove(reqRemIndex);
                    quest.setRequirements(reqList);
                    reqRemIndex = 0;
                    break;
                case RIGHT:
                    if (reqRemIndex >= quest.getRequirements().size() - 1) {
                        reqRemIndex = 0;
                    } else {
                        reqRemIndex++;
                    }
                    break;
            }

            AtlasManager.getEditingRemoveReq().put(p.getUniqueId(), reqRemIndex);
        }

        if (slot == 12) {
            boolean glowing = quest.isGlowing();
            quest.setGlowing(!glowing);
            p.playSound(p, Sound.ENTITY_EXPERIENCE_ORB_PICKUP,1f,1f);
        }

        if (slot == 21) {
            Material material = p.getInventory().getItemInMainHand().getType();
            if (material == Material.AIR) {
                p.playSound(p, Sound.ENTITY_VILLAGER_NO,1f,1f);
                return;
            }

            quest.setMaterial(material);
            p.playSound(p, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f,1f);
        }

        if (slot == 38) {
            int tries = confirmAbandon.getOrDefault(p.getUniqueId(), 1);

            switch (tries) {
                case 1:
                    p.sendMessage("§cAre you sure you want to delete your current quest?");
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            confirmAbandon.remove(p.getUniqueId());
                        }
                    }.runTaskLater(Atlas.getInstance(), 15 * 20L);
                    break;
                case 2:
                    p.sendMessage("§cThe quest is unrecoverable. Make sure you thought it through.");
                    break;
                case 3:
                    p.sendMessage("§cThis is the FINAL confirmation. Do you really want to delete the quest?");
                    break;
                case 4:
                    AtlasManager.getEditingQuest().remove(p.getUniqueId());
                    confirmAbandon.remove(p.getUniqueId());
                    p.playSound(p, Sound.ENTITY_PLAYER_LEVELUP, 1f,1f);
                    p.sendMessage("§aQuest successfully deleted.");
                    p.closeInventory();
                    return;
            }

            p.playSound(p, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f,1f);
            tries++;
            confirmAbandon.put(p.getUniqueId(), tries);
        }

        AtlasManager.getEditingQuest().put(p.getUniqueId(), quest);
        new QuestMenu(p).open();
    }
}
