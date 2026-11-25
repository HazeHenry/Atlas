package me.hazehenry.atlas.function.helper;

import lombok.Getter;
import lombok.Setter;
import me.hazehenry.atlas.function.QuestType;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.jspecify.annotations.Nullable;

@Getter @Setter
public class Requirement {

    private QuestType type;
    private boolean checkBox = false;

    @Nullable
    private ItemStack item;

    @Nullable
    private Entity entity;

    private int kills;

    @Nullable
    private Location location;

    @Nullable
    private String questId;

    private int xpLevels;

    @Nullable
    private ItemStack block;
}
