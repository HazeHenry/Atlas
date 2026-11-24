package me.hazehenry.atlas.function.helper;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class Chapter {

    private String id;
    private String name;
    private Material material;

    private List<Quest> quests;

    private Map<Integer, Map<Integer, Quest>> questPositions = new HashMap<>();
}
