package com.wgsoft.game.stonewar.objects.game;

public class Player {
    private CellColor color;
    public int money = 10;
    public int people = 10;

    public Player(CellColor color){
        this.color = color;
    }

    public CellColor getColor(){
        return color;
    }
}
