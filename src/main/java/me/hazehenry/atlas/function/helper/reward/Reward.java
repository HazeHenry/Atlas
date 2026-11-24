package me.hazehenry.atlas.function.helper.reward;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Reward {

    private String name;
    private RewardType type;
    private Object value;
}
