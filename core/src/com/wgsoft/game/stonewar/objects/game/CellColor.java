package com.wgsoft.game.stonewar.objects.game;

public enum CellColor {
    RED("red"),
    BLUE("blue"),
    GREEN("green"),
    YELLOW("yellow"),
    GRAY("gray"),
    NONE("none");

    private String name;

    CellColor(String name){
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
