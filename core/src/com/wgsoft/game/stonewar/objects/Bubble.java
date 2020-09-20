package com.wgsoft.game.stonewar.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;

import static com.wgsoft.game.stonewar.Const.*;

public class Bubble extends Actor {
    private TextureRegion region;
    private float percentX, percentY;
    private float speed;

    public Bubble(boolean initial){
        region = game.skin.getRegion("bubble/"+BUBBLES[MathUtils.random(BUBBLES.length-1)]);
        float size = MathUtils.random(MIN_BUBBLE_SIZE, MAX_BUBBLE_SIZE);
        setSize(size, size);
        percentX = MathUtils.random();
        if(initial){
            percentY = MathUtils.random();
        }else{
            percentY = 0f;
        }
        speed = MathUtils.random(MIN_BUBBLE_SPEED, MAX_BUBBLE_SPEED);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(1f, 1f, 1f, parentAlpha);
        batch.draw(region, getX(), getY(), getWidth(), getHeight());
        batch.setColor(1f, 1f, 1f, 1f);
    }

    public void setPositionFromPercent(){
        setPosition(percentX*(getStage().getWidth()+getWidth()), percentY*(getStage().getHeight()+getHeight()), Align.top | Align.right);
    }

    @Override
    public void act(float delta) {
        percentY += speed*delta;
        if(percentY >= 1f){
            Bubble bubble = new Bubble(false);
            getStage().addActor(bubble);
            bubble.setPositionFromPercent();
            remove();
        }else {
            setPositionFromPercent();
            super.act(delta);
        }
    }
}
