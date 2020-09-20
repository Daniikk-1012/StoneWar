package com.wgsoft.game.stonewar.objects.matchsettings;

import static com.wgsoft.game.stonewar.Const.*;

public enum Diplomacy {
    ON,
    OFF;
    @Override
    public String toString() {
        return this==ON?game.bundle.get("match-settings.diplomacy.on"):game.bundle.get("match-settings.diplomacy.off");
    }
}
