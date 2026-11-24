package me.hazehenry.atlas.function.helper;

import lombok.Getter;
import lombok.Setter;
import me.hazehenry.atlas.function.helper.reward.Reward;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class Quest {

    private String id;
    private String name = "&5Placeholder";
    private List<String> description = new ArrayList<>();
    private Material material = Material.BOOK;
    private boolean glowing = false;

    private List<Requirement> requirements = new ArrayList<>();

    public void addRequirement(Requirement requirement) {
        requirements.add(requirement);
    }

    public void removeRequirement(Requirement requirement) {
        requirements.remove(requirement);
    }

    private List<Reward> rewards = new ArrayList<>();

    public void addReward(Reward reward) {
        rewards.add(reward);
    }

    public void removeReward(Reward reward) {
        rewards.remove(reward);
    }

}
