package com.wgsoft.game.stonewar.objects.mainmenu;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;

import static com.wgsoft.game.stonewar.Const.*;

public class MenuBubble extends Actor {
    private TextureRegion region;
    private float percentX, percentY;
    private float speed;

    public MenuBubble(boolean initial){
        region = game.skin.getRegion("menu-bubble/"+COLOR_BUBBLES[MathUtils.random(COLOR_BUBBLES.length-1)]);
        float size = MathUtils.random(MIN_COLOR_BUBBLE_SIZE, MAX_COLOR_BUBBLE_SIZE);
        setSize(size, size);
        percentX = MathUtils.random();
        if(initial){
            percentY = MathUtils.random();
        }else{
            percentY = 0f;
        }
        speed = MathUtils.random(MIN_MENU_BUBBLE_SPEED, MAX_MENU_BUBBLE_SPEED);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(region, getX(), getY(), getWidth(), getHeight());
    }

    public void setPositionFromPercent(){
        setPosition(percentX*(getStage().getWidth()+getWidth()), percentY*(getStage().getHeight()+getHeight()), Align.top | Align.right);
    }

    @Override
    public void act(float delta) {
        percentY += speed*delta;
        if(percentY >= 1f){
            MenuBubble menuBubble = new MenuBubble(false);
            getStage().addActor(menuBubble);
            menuBubble.setPositionFromPercent();
            remove();
        }else {
            setPositionFromPercent();
            super.act(delta);
        }
    }
}
