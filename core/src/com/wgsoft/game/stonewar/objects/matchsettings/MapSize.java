package com.wgsoft.game.stonewar.objects.matchsettings;

public class MapSize {
    private int width;
    private int height;
    public MapSize(int width, int height){
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public String toString() {
        return width+"x"+height;
    }
}
