package com.wgsoft.game.stonewar.objects.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;

import static com.wgsoft.game.stonewar.Const.*;

public class Map extends Widget {
    public Cell[][] cells;

    public Map(){
        cells = new Cell[0][0];
    }

    public void init(int width, int height){
        cells = new Cell[width][height];
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                cells[i][j] = new Cell();
                cells[i][j].color = CellColor.values()[MathUtils.random(CellColor.values().length-1)];
                cells[i][j].selected = cells[i][j].color == CellColor.GRAY;
                if(cells[i][j].color != CellColor.GRAY) {
                    cells[i][j].type = CellType.values()[MathUtils.random(CellType.values().length - 1)];
                }
            }
        }
    }

    @Override
    public float getMinWidth() {
        return getPrefWidth();
    }

    @Override
    public float getMinHeight() {
        return getPrefHeight();
    }

    @Override
    public float getPrefWidth() {
        float value;
        if(cells.length != 0 && cells[0].length != 0) {
            if (getParent().getWidth() / getParent().getHeight() > (float) cells.length / cells[0].length) {
                value = getParent().getWidth();
            }else {
                value = cells.length * getParent().getHeight() / cells[0].length;
            }
        }else {
            value = 0f;
        }
        return value*getScaleX();
    }

    @Override
    public float getPrefHeight() {
        float value;
        if(cells.length != 0 && cells[0].length != 0){
            if (getParent().getWidth() / getParent().getHeight() > (float) cells.length / cells[0].length) {
                value = cells[0].length*getParent().getWidth()/cells.length;
            }else {
                value = getParent().getHeight();
            }
        }else {
            value = 0f;
        }
        return value*getScaleY();
    }

    @Override
    public float getMaxWidth() {
        return getPrefWidth();
    }

    @Override
    public float getMaxHeight() {
        return getPrefHeight();
    }

    private void drawCell(Batch batch, int x, int y){
        if(cells[x][y].color != CellColor.NONE){
            String name = "";
            if(x > 0 && cells[x-1][y].color == cells[x][y].color){
                if(x < cells.length-1 && cells[x+1][y].color == cells[x][y].color){
                    name += "horizontal";
                }else{
                    name += "left";
                }
            }else if(x < cells.length-1 && cells[x+1][y].color == cells[x][y].color){
                name += "right";
            }else{
                name += "none";
            }
            name += "-";
            if(y > 0 && cells[x][y-1].color == cells[x][y].color){
                if(y < cells[0].length-1 && cells[x][y+1].color == cells[x][y].color){
                    name += "vertical";
                }else{
                    name += "bottom";
                }
            }else if(y < cells[0].length-1 && cells[x][y+1].color == cells[x][y].color){
                name += "top";
            }else{
                name += "none";
            }
            batch.draw(game.skin.getRegion("island/"+cells[x][y].color+"/block/"+name), getX()+x*getWidth()/cells.length, getY()+(y+1f-1f/GAME_CELL_ASPECT_RATIO)*getHeight()/cells[0].length, getWidth()/cells.length, 1f/GAME_CELL_ASPECT_RATIO*getHeight()/cells[0].length);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        for(int j = cells.length > 0 ? cells[0].length-1 : -1; j >= 0; j--){
            for(int i = 0; i < cells.length; i++){
                if(i >= cells.length-1 || j >= cells[0].length-1 || cells[i+1][j + 1].color == CellColor.NONE || cells[i][j].color == cells[i+1][j + 1].color || cells[i][j + 1].color != cells[i+1][j + 1].color || cells[i+1][j].color != cells[i+1][j + 1].color) {
                    if (i > 0 && j < cells[0].length - 1 && cells[i - 1][j + 1].color != CellColor.NONE && cells[i][j].color != cells[i - 1][j + 1].color && cells[i - 1][j].color == cells[i - 1][j + 1].color && cells[i][j + 1].color == cells[i - 1][j + 1].color) {
                        batch.draw(game.skin.getRegion("island/" + cells[i - 1][j + 1].color + "/angle/left-top"), getX() + (i - 0.5f) * getWidth() / cells.length, getY() + (j + 1.5f - 1f / GAME_CELL_ASPECT_RATIO) * getHeight() / cells[0].length, getWidth() / cells.length, 1f / GAME_CELL_ASPECT_RATIO * getHeight() / cells[0].length);
                    }
                    drawCell(batch, i, j);
                    if (i > 0 && j < cells[0].length - 1) {
                        if (cells[i][j + 1].color != CellColor.NONE && cells[i - 1][j].color != cells[i][j + 1].color && cells[i - 1][j + 1].color == cells[i][j + 1].color && cells[i][j].color == cells[i][j + 1].color) {
                            batch.draw(game.skin.getRegion("island/" + cells[i][j + 1].color + "/angle/right-top"), getX() + (i - 0.5f) * getWidth() / cells.length, getY() + (j + 1.5f - 1f / GAME_CELL_ASPECT_RATIO) * getHeight() / cells[0].length, getWidth() / cells.length, 1f / GAME_CELL_ASPECT_RATIO * getHeight() / cells[0].length);
                            drawCell(batch, i-1, j);
                        }
                        if (cells[i][j].color != CellColor.NONE && cells[i - 1][j + 1].color != cells[i][j].color && cells[i][j + 1].color == cells[i][j].color && cells[i - 1][j].color == cells[i][j].color) {
                            batch.draw(game.skin.getRegion("island/" + cells[i][j].color + "/angle/right-bottom"), getX() + (i - 0.5f) * getWidth() / cells.length, getY() + (j + 1.5f - 1f / GAME_CELL_ASPECT_RATIO) * getHeight() / cells[0].length, getWidth() / cells.length, 1f / GAME_CELL_ASPECT_RATIO * getHeight() / cells[0].length);
                        }
                        if (cells[i - 1][j].color != CellColor.NONE && cells[i][j + 1].color != cells[i - 1][j].color && cells[i][j].color == cells[i - 1][j].color && cells[i - 1][j + 1].color == cells[i - 1][j].color) {
                            batch.draw(game.skin.getRegion("island/" + cells[i - 1][j].color + "/angle/left-bottom"), getX() + (i - 0.5f) * getWidth() / cells.length, getY() + (j + 1.5f - 1f / GAME_CELL_ASPECT_RATIO) * getHeight() / cells[0].length, getWidth() / cells.length, 1f / GAME_CELL_ASPECT_RATIO * getHeight() / cells[0].length);
                        }
                    }
                }
            }
        }
        for(int i = 0; i < cells.length; i++){
            for(int j = 0; j < cells[0].length; j++){
                if(cells[i][j].color != CellColor.NONE) {
                    batch.draw(game.skin.getRegion("island/"+cells[i][j].color+"/icon/"+cells[i][j].type+cells[i][j].level), getX()+i*getWidth()/cells.length, getY()+j*getHeight()/cells[0].length, getWidth()/cells.length/2f, getHeight()/cells[0].length/2f, getWidth()/cells.length, getHeight()/cells[0].length, GAME_ICON_SCALE, GAME_ICON_SCALE, 0f);
                }
            }
        }
        for(int i = 0; i < cells.length; i++){
            for(int j = 0; j < cells[0].length; j++){
                if(cells[i][j].selected){
                    if((i == 0 || !cells[i-1][j].selected) && (j == 0 || !cells[i][j-1].selected)){
                        batch.draw(game.skin.getRegion("island/outline/out/right-top"), getX()+(i-0.5f)*getWidth()/cells.length, getY()+(j-0.5f)*getHeight()/cells[0].length, getWidth()/cells.length, getHeight()/cells[0].length);
                    }else if((i > 0 && cells[i-1][j].selected) && (j > 0 && cells[i][j-1].selected) && !cells[i-1][j-1].selected){
                        batch.draw(game.skin.getRegion("island/outline/in/right-top"), getX()+(i-0.5f)*getWidth()/cells.length, getY()+(j-0.5f)*getHeight()/cells[0].length, getWidth()/cells.length, getHeight()/cells[0].length);
                    }
                    if((i == 0 || !cells[i-1][j].selected) && (j == cells[0].length-1 || !cells[i][j+1].selected)){
                        batch.draw(game.skin.getRegion("island/outline/out/right-bottom"), getX()+(i-0.5f)*getWidth()/cells.length, getY()+(j+0.5f)*getHeight()/cells[0].length, getWidth()/cells.length, getHeight()/cells[0].length);
                    }else if((i > 0 && cells[i-1][j].selected) && (j < cells[0].length-1 && cells[i][j+1].selected) && !cells[i-1][j+1].selected){
                        batch.draw(game.skin.getRegion("island/outline/in/right-bottom"), getX()+(i-0.5f)*getWidth()/cells.length, getY()+(j+0.5f)*getHeight()/cells[0].length, getWidth()/cells.length, getHeight()/cells[0].length);
                    }
                    if((i == cells.length-1 || !cells[i+1][j].selected) && (j == cells[0].length-1 || !cells[i][j+1].selected)){
                        batch.draw(game.skin.getRegion("island/outline/out/left-bottom"), getX()+(i+0.5f)*getWidth()/cells.length, getY()+(j+0.5f)*getHeight()/cells[0].length, getWidth()/cells.length, getHeight()/cells[0].length);
                    }else if((i < cells.length-1 && cells[i+1][j].selected) && (j < cells[0].length-1 && cells[i][j+1].selected) && !cells[i+1][j+1].selected){
                        batch.draw(game.skin.getRegion("island/outline/in/left-bottom"), getX()+(i+0.5f)*getWidth()/cells.length, getY()+(j+0.5f)*getHeight()/cells[0].length, getWidth()/cells.length, getHeight()/cells[0].length);
                    }
                    if((i == cells.length-1 || !cells[i+1][j].selected) && (j == 0 || !cells[i][j-1].selected)){
                        batch.draw(game.skin.getRegion("island/outline/out/left-top"), getX()+(i+0.5f)*getWidth()/cells.length, getY()+(j-0.5f)*getHeight()/cells[0].length, getWidth()/cells.length, getHeight()/cells[0].length);
                    }else if((i < cells.length-1 && cells[i+1][j].selected) && (j > 0 && cells[i][j-1].selected) && !cells[i+1][j-1].selected){
                        batch.draw(game.skin.getRegion("island/outline/in/left-top"), getX()+(i+0.5f)*getWidth()/cells.length, getY()+(j-0.5f)*getHeight()/cells[0].length, getWidth()/cells.length, getHeight()/cells[0].length);
                    }
                }
            }
        }
        if(false) {
            for (int i = 0; i < cells.length; i++) {
                for (int j = 0; j < cells[0].length; j++) {
                    batch.draw(game.skin.getRegion("island/cross"), getX() + i * getWidth() / cells.length, getY() + j * getHeight() / cells[0].length, getWidth() / cells.length, getHeight() / cells[0].length);
                }
            }
        }
    }
}
