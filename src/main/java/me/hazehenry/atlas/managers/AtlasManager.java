package me.hazehenry.atlas.managers;

import lombok.Getter;
import me.hazehenry.atlas.function.helper.Quest;

import java.util.*;

public class AtlasManager {

    @Getter
    public static HashMap<UUID, Quest> editingQuest = new HashMap<>();

    @Getter
    public static HashMap<UUID, Integer> editingLine = new HashMap<>();

    @Getter
    public static HashMap<UUID, Integer> editingReq = new HashMap<>();

    @Getter
    public static HashMap<UUID, Integer> editingRemoveReq = new HashMap<>();

    public static List<Quest> allQuests = new ArrayList<>();
}
