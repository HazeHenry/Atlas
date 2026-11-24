package me.hazehenry.atlas.menus.questmenu;

import me.hazehenry.atlas.function.QuestType;
import me.hazehenry.atlas.function.helper.Quest;
import me.hazehenry.atlas.managers.AtlasManager;
import me.hazehenry.atlas.managers.ItemBuilder;
import me.hazehenry.atlas.messages.Messages;
import me.hazehenry.atlas.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class QuestMenu implements InventoryHolder {

    private final Player player;
    private final Inventory inventory;
    private Quest quest;

    public QuestMenu(Player player) {
        this.player = player;
        this.inventory = Bukkit.createInventory(this, 5 * 9, Messages.QUEST_MENU_TITLE);
        if (quest == null) {
            quest = new Quest();

            List<String> desc = new ArrayList<>();
            desc.add("&7This is a template quest.");
            desc.add("&aMake sure to edit it with the tools on the left.");
            quest.setDescription(desc);
        }
    }

    public void open() {
        if (AtlasManager.getEditingQuest().containsKey(player.getUniqueId())) {
            quest = AtlasManager.getEditingQuest().get(player.getUniqueId());
        } else {
            AtlasManager.getEditingQuest().put(player.getUniqueId(), quest);
        }

        build();
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

        inventory.setItem(13, new ItemStack(Material.MAGENTA_STAINED_GLASS_PANE));
        inventory.setItem(22, new ItemStack(Material.MAGENTA_STAINED_GLASS_PANE));
        inventory.setItem(31, new ItemStack(Material.MAGENTA_STAINED_GLASS_PANE));

        inventory.setItem(24, new ItemBuilder(quest.getMaterial())
                .name(ChatUtils.process(quest.getName()))
                .lore(ChatUtils.process(previewDescription(quest)))
                .glow(quest.isGlowing())
                .build());

        inventory.setItem(10, new ItemBuilder(Material.PAPER)
                .name(Messages.MENU_QUESTCREATE_RENAME_NAME)
                .lore(Messages.MENU_QUESTCREATE_RENAME_LORE)
                .build());

        inventory.setItem(11, new ItemBuilder(Material.BOOK)
                .name(Messages.MENU_QUESTCREATE_SETLORE_NAME)
                .lore(descriptionManager(quest, AtlasManager.getEditingLine().getOrDefault(player.getUniqueId(), 0)))
                .build());

        inventory.setItem(12, new ItemBuilder(Material.NETHER_STAR)
                .name(Messages.MENU_QUESTCREATE_TOGGLEGLOW_NAME)
                .build());

        inventory.setItem(19, new ItemBuilder(Material.HEART_OF_THE_SEA)
                .name(Messages.MENU_QUESTCREATE_ADDREQ_NAME)
                .lore(requirementManager(AtlasManager.getEditingReq().getOrDefault(player.getUniqueId(), 0)))
                .build());

        inventory.setItem(20, new ItemBuilder(Material.SHULKER_SHELL)
                .name(Messages.MENU_QUESTCREATE_DELREQ_NAME)
                .lore(requirementRemoveManager(quest, AtlasManager.getEditingRemoveReq().getOrDefault(player.getUniqueId(), 0)))
                .build());

        inventory.setItem(21, new ItemBuilder(Material.OAK_LOG)
                .name(Messages.MENU_QUESTCREATE_SETMATERIAL_NAME)
                .lore(Messages.MENU_QUESTCREATE_SETMATERIAL_LORE)
                .build());

        inventory.setItem(28, new ItemBuilder(Material.GOLD_INGOT)
                 .name(Messages.MENU_QUESTCREATE_ADDREWARD_NAME)
                 .build());

        inventory.setItem(29, new ItemBuilder(Material.BUNDLE)
                .name(Messages.MENU_QUESTCREATE_DELREWARD_NAME)
                .hideToolTip()
                .build());

        inventory.setItem(38, new ItemBuilder(Material.BARRIER)
                .name(Messages.MENU_QUESTCREATE_ABANDON_NAME)
                .lore(Messages.MENU_QUESTCREATE_ABANDON_LORE)
                .build());

        inventory.setItem(40, new ItemBuilder(Material.NETHERITE_INGOT)
                .name(Messages.MENU_QUESTCREATE_SETID_NAME)
                .lore(Messages.MENU_QUESTCREATE_SETID_LORE)
                .build());

        inventory.setItem(42, new ItemBuilder(Material.EMERALD)
                .name(Messages.MENU_QUESTCREATE_CONFIRMATION_NAME)
                .lore(Messages.MENU_QUESTCREATE_CONFIRMATION_LORE)
                .build());

    }

    private List<String> previewDescription(Quest quest) {
        List<String> list = new ArrayList<>(quest.getDescription());

        list.add("");
        list.add("§3§lREQUIREMENTS");
        for (int i = 0; i < quest.getRequirements().size(); i++) {
            list.add(" §7" + fancyConvert(quest.getRequirements().get(i).getType())
                    + ((quest.getRequirements().get(i).getType() != QuestType.CHECK_BOX)
                    ? ": §a" + extra(quest.getRequirements().get(i).getType(), quest, i) : ""));
        }

        list.add("");
        list.add("§6§lREWARDS");
        for (int i = 0; i < quest.getRewards().size(); i++) {
            list.add(" " + quest.getRewards().get(i).getName());
        }

        return list;
    }

    private List<String> descriptionManager(Quest quest, int selectedLine) {
        List<String> stringList = new ArrayList<>();

        stringList.add("§8Atlas");
        stringList.add("");
        stringList.add("§7You can add, reorder and modify quest");
        stringList.add("§7description easily with the functions.");
        stringList.add("");
        stringList.add("§3§lDESCRIPTION");

        for (int i = 0; i < quest.getDescription().size(); i++) {
            if (selectedLine == i) {
                stringList.add(ChatUtils.process(" §a§l⬤ §r" + quest.getDescription().get(i)));
            } else {
                stringList.add(ChatUtils.process(" §7§l⬤ §r" + quest.getDescription().get(i)));
            }
        }

        stringList.add("");
        stringList.add("§7----------");
        stringList.add("");
        stringList.add("§6§lCONTROLS");
        stringList.add("§eLeft Click §7- §fEdit selected line.");
        stringList.add("§eRight Click §7- §fCycle down lines.");
        stringList.add("§eMiddle Click / Drop §7- §fAdd a new line.");
        stringList.add("§eSNEAK + Left Click §7- §fMove selected line up.");
        stringList.add("§eSNEAK + Right Click §7- §fMove selected line down.");

        return stringList;
    }

    private List<String> requirementManager(int selectedLine) {
        List<String> stringList = new ArrayList<>();

        stringList.add("§8Atlas");
        stringList.add("");
        stringList.add("§7You can add quest requirements here.");
        stringList.add("§7Many of these, notably block break & place,");
        stringList.add("§7item deposit, acquire and craft, food consume");
        stringList.add("§7use your held item. Make sure it's the right one.");
        stringList.add("§7with the right amount. Visit location will use your");
        stringList.add("§7current location, specifically teleporting there");
        stringList.add("§7will trigger it, ordinary walking won't work.");
        stringList.add("");
        stringList.add("§3§lTYPES");

        for (int i = 0; i < QuestType.questTypeList.size(); i++) {
            if (selectedLine == i) {
                stringList.add(ChatUtils.process(" §E" + QuestType.questTypeList.get(i)));
            } else {
                stringList.add(ChatUtils.process(" §7" + QuestType.questTypeList.get(i)));
            }
        }

        stringList.add("");
        stringList.add("§7----------");
        stringList.add("");
        stringList.add("§6§lCONTROLS");
        stringList.add("§eLeft Click §7- §fAdd requirement.");
        stringList.add("§eRight Click §7- §fCycle down lines.");

        return stringList;
    }

    private List<String> requirementRemoveManager(Quest quest, int selectedLine) {
        List<String> stringList = new ArrayList<>();

        stringList.add("§8Atlas");
        stringList.add("");
        stringList.add("§7You can remove the quest's requirements here.");
        stringList.add("");
        stringList.add("§3§lREQUIREMENTS");

        if (!quest.getRequirements().isEmpty()) {
            for (int i = 0; i < quest.getRequirements().size(); i++) {
                QuestType type = quest.getRequirements().get(i).getType();
                if (selectedLine == i) {
                    stringList.add(ChatUtils.process(" §E" + type + " " + extra(type, quest, i)));
                } else {
                    stringList.add(ChatUtils.process(" §7" + type + " " + extra(type, quest, i)));
                }
            }
        } else {
            stringList.add("§8No requirement here yet.");
        }

        stringList.add("");
        stringList.add("§7----------");
        stringList.add("");
        stringList.add("§6§lCONTROLS");
        stringList.add("§eLeft Click §7- §fRemove selected requirement.");
        stringList.add("§eRight Click §7- §fCycle down lines.");

        return stringList;
    }

    private String fancyConvert(QuestType type) {
        return Arrays.stream(type.toString().toLowerCase().split("_"))
                .map(k -> Character.toUpperCase(k.charAt(0)) + k.substring(1))
                .collect(Collectors.joining(" "));
    }

    private String extra(QuestType type, Quest quest, int i) {
        return switch (type) {
            case KILL_PLAYER -> String.valueOf(quest.getRequirements().get(i).getKills());
            case EARN_EXPERIENCE -> String.valueOf(quest.getRequirements().get(i).getXpLevels());
            case CRAFT_ITEM, DEPOSIT_ITEM, ACQUIRE_ITEM, CONSUME_FOOD ->
                    quest.getRequirements().get(i).getItem().getItemMeta().getDisplayName();
            case BREAK_BLOCK, PLACE_BLOCK -> String.valueOf(quest.getRequirements().get(i).getBlock());
            case COMPLETE_QUEST -> quest.getRequirements().get(i).getQuestId();
            case VISIT_LOCATION ->
                    quest.getRequirements().get(i).getLocation().getX() + "," + quest.getRequirements().get(i).getLocation().getY() + "," + quest.getRequirements().get(i).getLocation().getZ();
            case KILL_MOB -> quest.getRequirements().get(i).getEntity().getName();
            default -> "";
        };

    }
}
