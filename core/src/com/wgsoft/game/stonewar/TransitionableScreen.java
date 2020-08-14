package com.wgsoft.game.stonewar;

import com.badlogic.gdx.Screen;

public abstract class TransitionableScreen implements Screen {
    private TransitionScreen transitionScreen;

    public abstract void transitionBegin();
    public abstract void transitionEnd();

    public TransitionScreen getTransitionScreen() {
        return transitionScreen;
    }

    public void setTransitionScreen(TransitionScreen transitionScreen) {
        this.transitionScreen = transitionScreen;
    }
}
