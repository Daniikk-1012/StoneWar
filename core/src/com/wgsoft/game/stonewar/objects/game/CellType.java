package com.wgsoft.game.stonewar.objects.game;

public enum CellType {
    PLATFORM("platform"),
    FISH_PLATFORM("fish"),
    HOME("home"),
    MINE("mine"),
    ORE("ore"),
    SHIELD("shield"),
    SWORD("sword");
    private String name;

    CellType(String name){
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
