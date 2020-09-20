package com.wgsoft.game.stonewar.objects.matchsettings;

import static com.wgsoft.game.stonewar.Const.*;

public enum Difficulty {
    LOW("low"),
    MEDIUM("medium"),
    HIGH("high");

    private String name;

    Difficulty(String name){
        this.name = name;
    }

    @Override
    public String toString() {
        return game.bundle.get("match-settings.difficulty."+name);
    }
}
