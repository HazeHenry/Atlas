package me.hazehenry.atlas.function;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor @Getter
public enum QuestType {
    CHECK_BOX(false),

    BREAK_BLOCK(false),
    PLACE_BLOCK(false),

    KILL_MOB(false),
    KILL_PLAYER(true),

    EARN_EXPERIENCE(true),

    DEPOSIT_ITEM(false),
    ACQUIRE_ITEM(false),
    CONSUME_FOOD(false),
    CRAFT_ITEM(false),

    VISIT_LOCATION(true),

    COMPLETE_QUEST(true);

    final boolean chatType;

    public static QuestType getByName(String name) {
        for (QuestType tier : QuestType.values()) {
            if (tier.toString().equalsIgnoreCase(name)) {
                return tier;
            }
        }
        return null;
    }

    public static List<QuestType> questTypeList = new ArrayList<>(List.of(values()));
}
