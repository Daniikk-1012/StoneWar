package com.wgsoft.game.stonewar.objects.loading;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;

import static com.wgsoft.game.stonewar.Const.*;

public class LoadingBubble extends Actor {
    private TextureRegion region;
    private float fromPercentX, fromPercentY;
    private float toPercentX, toPercentY;

    public LoadingBubble(){
        region = loadingScreen.skin.getRegion("bubble/"+COLOR_BUBBLES[MathUtils.random(COLOR_BUBBLES.length-1)]);
        float size = MathUtils.random(MIN_COLOR_BUBBLE_SIZE, MAX_COLOR_BUBBLE_SIZE);
        setSize(size, size);
        switchFrom();
        switchTo();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(region, getX(), getY(), getWidth(), getHeight());
    }

    private void switchFrom(){
        fromPercentX = MathUtils.random(0.5f-LOADING_BUBBLE_POS_AMPLITUDE, 0.5f+LOADING_BUBBLE_POS_AMPLITUDE);
        fromPercentY = MathUtils.random(0.5f-LOADING_BUBBLE_POS_AMPLITUDE, 0.5f+LOADING_BUBBLE_POS_AMPLITUDE);
    }

    private void switchTo(){
        toPercentX = MathUtils.random();
        toPercentY = MathUtils.random();
    }

    public void setPositionFromPercent(){
        float percentX, percentY;
        if(loadingScreen.progress < 0.5f) {
            percentX = Interpolation.circleOut.apply(fromPercentX, toPercentX, loadingScreen.progress*2f);
            percentY = Interpolation.circleOut.apply(fromPercentY, toPercentY, loadingScreen.progress*2f);
        }else{
            percentX = Interpolation.circleIn.apply(toPercentX, fromPercentX, (loadingScreen.progress-0.5f)*2f);
            percentY = Interpolation.circleIn.apply(toPercentY, fromPercentY, (loadingScreen.progress-0.5f)*2f);
        }

        setPosition(percentX*getStage().getWidth(), percentY*getStage().getHeight(), Align.center);
    }

    @Override
    public void act(float delta) {
        if(loadingScreen.prevProgress < 0.5f && loadingScreen.progress >= 0.5f){
            switchFrom();
        }else if(loadingScreen.prevProgress >= 0.5f && loadingScreen.progress < 0.5f){
            switchTo();
        }
        setPositionFromPercent();
        super.act(delta);
    }
}
