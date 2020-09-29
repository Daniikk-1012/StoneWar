package com.wgsoft.game.stonewar.objects.game;

public class Cell{
    public CellColor color = CellColor.NONE;
    public CellType type = CellType.PLATFORM;
    public int level = 1;
    public boolean selected = false;
}