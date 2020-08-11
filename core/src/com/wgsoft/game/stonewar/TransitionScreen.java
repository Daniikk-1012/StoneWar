package com.wgsoft.game.stonewar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

import static com.wgsoft.game.stonewar.Const.*;

public class TransitionScreen implements Screen {
    private TransitionableScreen from;
    private TransitionableScreen to;
    private boolean toOnTop;
    private float duration;
    private float time;

    public Screen setup(TransitionableScreen from, TransitionableScreen to, boolean toOnTop, float duration){
        this.from = from;
        this.to = to;
        this.toOnTop = toOnTop;
        this.duration = duration;
        time = 0f;
        return this;
    }

    public float getProgress(){
        return time/duration;
    }

    public TransitionableScreen getFrom() {
        return from;
    }

    public TransitionableScreen getTo() {
        return to;
    }

    public boolean isToOnTop() {
        return toOnTop;
    }

    public float getDuration() {
        return duration;
    }

    public float getTime() {
        return time;
    }

    @Override
    public void show() {
        if(from != null) {
            from.setTransitionScreen(this);
        }
        if(to != null) {
            to.setTransitionScreen(this);
        }
        if(from != null) {
            from.transitionBegin();
        }
        if(to != null) {
            to.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            to.transitionBegin();
        }
    }

    @Override
    public void render(float delta) {
        time += delta;
        if(time >= duration){
            if(to != null) {
                game.setScreen(to);
                to.render(delta);
            }else{
                Gdx.app.exit();
            }
        }else if(toOnTop){
            if(from != null) {
                from.render(delta);
            }
            if(to != null) {
                to.render(delta);
            }
        }else{
            if(to != null) {
                to.render(delta);
            }
            if(from != null) {
                from.render(delta);
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        if(from != null) {
            from.resize(width, height);
        }
        if(to != null) {
            to.resize(width, height);
        }
    }

    @Override
    public void pause() {
        if(from != null) {
            from.pause();
        }
        if(to != null) {
            to.pause();
        }
    }

    @Override
    public void resume() {
        if(from != null) {
            from.resume();
        }
        if(to != null) {
            to.resume();
        }
    }

    @Override
    public void hide() {
        if(from != null) {
            from.transitionEnd();
        }
        if(to != null) {
            to.transitionEnd();
        }
        if(from != null) {
            from.setTransitionScreen(null);
        }
        if(to != null) {
            to.setTransitionScreen(null);
        }
    }

    @Override
    public void dispose() {
    }
}
