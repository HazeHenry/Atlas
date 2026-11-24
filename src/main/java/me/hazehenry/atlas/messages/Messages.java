package me.hazehenry.atlas.messages;

import lombok.Getter;
import me.hazehenry.atlas.Atlas;
import me.hazehenry.atlas.utils.ChatUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Getter
public class Messages {

    public static String MENU_TITLE;

    public static String MENU_MAIN_ITEM_NAME;
    public static List<String> MENU_MAIN_ITEM_LORE;

    public static String MENU_CLOSE_ITEM_NAME;
    public static String MENU_PREVIOUS_ITEM_NAME;

    public static String QUEST_MENU_TITLE;

    public static String MENU_OVERVIEW_QUESTS_ITEM_NAME;
    public static String MENU_CREATE_QUEST_ITEM_NAME;
    public static String MENU_DELETE_QUEST_ITEM_NAME;

    public static String MENU_OVERVIEW_CHAPTERS_ITEM_NAME;
    public static String MENU_CREATE_CHAPTER_ITEM_NAME;
    public static String MENU_DELETE_CHAPTER_ITEM_NAME;

    public static String MENU_QUESTCREATE_RENAME_NAME;
    public static List<String> MENU_QUESTCREATE_RENAME_LORE;

    public static String MENU_QUESTCREATE_SETLORE_NAME;

    public static String MENU_QUESTCREATE_ADDREQ_NAME;
    public static String MENU_QUESTCREATE_DELREQ_NAME;

    public static String MENU_QUESTCREATE_ADDREWARD_NAME;
    public static String MENU_QUESTCREATE_DELREWARD_NAME;

    public static String MENU_QUESTCREATE_SETMATERIAL_NAME;
    public static List<String> MENU_QUESTCREATE_SETMATERIAL_LORE;

    public static String MENU_QUESTCREATE_TOGGLEGLOW_NAME;
    public static String MENU_QUESTCREATE_SETID_NAME;
    public static List<String> MENU_QUESTCREATE_SETID_LORE;

    public static String MENU_QUESTCREATE_ABANDON_NAME;
    public static List<String> MENU_QUESTCREATE_ABANDON_LORE;

    public static String MENU_QUESTCREATE_CONFIRMATION_NAME;
    public static List<String> MENU_QUESTCREATE_CONFIRMATION_LORE;

    public static void load(String language) {
        Atlas instance = Atlas.getInstance();

        File langFile = new File(instance.getDataFolder(), "lang/lang_" + language + ".yml");
        if (!langFile.exists()) {
            instance.saveResource("lang/lang_en.yml", false);
            instance.getLogger().warning(
                    "§cCould not find language file for " + language + ". Applying default english messages."
            );

            langFile = new File(instance.getDataFolder(), "lang/lang_en.yml");
        }

        FileConfiguration config = YamlConfiguration.loadConfiguration(langFile);

        MENU_TITLE = ChatUtils.process(config.getString("menu-title"));

        MENU_MAIN_ITEM_NAME = ChatUtils.process(config.getString("menu-main-item.name"));
        MENU_MAIN_ITEM_LORE = ChatUtils.process(new ArrayList<>(config.getStringList("menu-main-item.lore")));

        MENU_CLOSE_ITEM_NAME = ChatUtils.process(config.getString("menu-close-item-name"));
        MENU_PREVIOUS_ITEM_NAME = ChatUtils.process(config.getString("menu-previous-item-name"));


        QUEST_MENU_TITLE = ChatUtils.process(config.getString("quest-menu-title"));

        MENU_OVERVIEW_QUESTS_ITEM_NAME = ChatUtils.process(config.getString("menu-overview-quests-item-name"));
        MENU_CREATE_QUEST_ITEM_NAME = ChatUtils.process(config.getString("menu-create-quest-item-name"));
        MENU_DELETE_QUEST_ITEM_NAME = ChatUtils.process(config.getString("menu-delete-quest-item-name"));

        MENU_OVERVIEW_CHAPTERS_ITEM_NAME = ChatUtils.process(config.getString("menu-overview-chapters-item-name"));
        MENU_CREATE_CHAPTER_ITEM_NAME = ChatUtils.process(config.getString("menu-create-chapter-item-name"));
        MENU_DELETE_CHAPTER_ITEM_NAME = ChatUtils.process(config.getString("menu-delete-chapter-item-name"));

        MENU_QUESTCREATE_RENAME_NAME = ChatUtils.process(config.getString("menu-questcreate-rename.name"));
        MENU_QUESTCREATE_RENAME_LORE = ChatUtils.process(new ArrayList<>(config.getStringList("menu-questcreate-rename.lore")));

        MENU_QUESTCREATE_SETLORE_NAME = ChatUtils.process(config.getString("menu-questcreate-setlore-name"));

        MENU_QUESTCREATE_ADDREQ_NAME = ChatUtils.process(config.getString("menu-questcreate-addrequirement-name"));
        MENU_QUESTCREATE_DELREQ_NAME = ChatUtils.process(config.getString("menu-questcreate-delrequirement-name"));

        MENU_QUESTCREATE_ADDREWARD_NAME = ChatUtils.process(config.getString("menu-questcreate-addreward-name"));
        MENU_QUESTCREATE_DELREWARD_NAME = ChatUtils.process(config.getString("menu-questcreate-delreward-name"));

        MENU_QUESTCREATE_TOGGLEGLOW_NAME = ChatUtils.process(config.getString("menu-questcreate-toggleglowing-name"));

        MENU_QUESTCREATE_SETID_NAME = ChatUtils.process(config.getString("menu-questcreate-setid.name"));
        MENU_QUESTCREATE_SETID_LORE = ChatUtils.process(new ArrayList<>(config.getStringList("menu-questcreate-setid.lore")));

        MENU_QUESTCREATE_ABANDON_NAME = ChatUtils.process(config.getString("menu-questcreate-abandon.name"));
        MENU_QUESTCREATE_ABANDON_LORE = ChatUtils.process(new ArrayList<>(config.getStringList("menu-questcreate-abandon.lore")));

        MENU_QUESTCREATE_CONFIRMATION_NAME = ChatUtils.process(config.getString("menu-questcreate-confirmation.name"));
        MENU_QUESTCREATE_CONFIRMATION_LORE = ChatUtils.process(new ArrayList<>(config.getStringList("menu-questcreate-confirmation.lore")));

        MENU_QUESTCREATE_SETMATERIAL_NAME = ChatUtils.process(config.getString("menu-questcreate-setmaterial.name"));
        MENU_QUESTCREATE_SETMATERIAL_LORE = ChatUtils.process(new ArrayList<>(config.getStringList("menu-questcreate-setmaterial.lore")));

    }

}

