package com.wgsoft.game.stonewar.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.wgsoft.game.stonewar.Localizable;
import com.wgsoft.game.stonewar.TransitionableScreen;

import static com.wgsoft.game.stonewar.Const.*;

public class MainMenuScreen extends TransitionableScreen implements Localizable {
    private Stage backgroundStage;
    private Stage uiStage;

    private Table rootTable;
    private Table topBarTable;
    private TextButton rateButton;
    private TextButton authorsButton;
    private Label titleLabel;
    private TextButton playButton;
    private TextButton tutorialButton;
    private TextButton campaignButton;
    private TextButton settingsButton;
    private TextButton exitButton;
    private Table bottomBarTable;
    private TextButton achievementsButton;

    private InputMultiplexer inputMultiplexer;

    public MainMenuScreen(){
        backgroundStage = game.bubbleBackgroundStage;
        uiStage = new Stage(new ScreenViewport(), game.batch);

        inputMultiplexer = new InputMultiplexer(uiStage, backgroundStage);

        rootTable = new Table(game.skin);
        rootTable.setFillParent(true);

        topBarTable = new Table(game.skin);
        topBarTable.setBackground("bar");

        rateButton = new TextButton("main-menu.rate", game.skin, "transparent");
        topBarTable.add(rateButton).growY().padLeft(BAR_PADDING_HORIZONTAL);

        topBarTable.add().grow();

        authorsButton = new TextButton("main-menu.authors", game.skin, "transparent");
        topBarTable.add(authorsButton).growY().padRight(BAR_PADDING_HORIZONTAL);

        rootTable.add(topBarTable).growX().height(BAR_HEIGHT);

        rootTable.row();
        rootTable.add().grow();
        rootTable.row();

        titleLabel = new Label(game.properties.getProperty("title"), game.skin, "boldLarge");
        titleLabel.setAlignment(Align.center);
        titleLabel.setWrap(true);
        rootTable.add(titleLabel).growX();

        rootTable.row();
        rootTable.add().grow();
        rootTable.row();

        playButton = new TextButton("main-menu.play", game.skin, "red");
        playButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.transitionScreen.setup(game.mainMenuScreen, game.matchSettingsScreen, true, SCREEN_TRANSITION_DURATION));
            }
        });
        rootTable.add(playButton).expandX();

        rootTable.row();
        rootTable.add().grow();
        rootTable.row();

        tutorialButton = new TextButton("main-menu.tutorial", game.skin, "blue");
        rootTable.add(tutorialButton).expandX();

        rootTable.row();
        rootTable.add().grow();
        rootTable.row();

        campaignButton = new TextButton("main-menu.campaign", game.skin, "green");
        rootTable.add(campaignButton).expandX();

        rootTable.row();
        rootTable.add().grow();
        rootTable.row();

        settingsButton = new TextButton("main-menu.settings", game.skin, "violet");
        settingsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.transitionScreen.setup(game.mainMenuScreen, game.settingsScreen, true, SCREEN_TRANSITION_DURATION));
            }
        });
        rootTable.add(settingsButton).expandX();

        rootTable.row();
        rootTable.add().grow();
        rootTable.row();

        exitButton = new TextButton("main-menu.exit", game.skin, "yellow");
        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });
        rootTable.add(exitButton);

        rootTable.row();
        rootTable.add().grow();
        rootTable.row();

        bottomBarTable = new Table(game.skin);
        bottomBarTable.setBackground("bar");

        achievementsButton = new TextButton("main-menu.achievements", game.skin, "transparent");
        bottomBarTable.add(achievementsButton).growY().padLeft(BAR_PADDING_HORIZONTAL);

        bottomBarTable.add().grow();

        Label versionLabel = new Label(game.properties.getProperty("version"), game.skin, "regularLarge");
        bottomBarTable.add(versionLabel).growY().padRight(BAR_PADDING_HORIZONTAL);

        rootTable.add(bottomBarTable).growX().height(BAR_HEIGHT);

        uiStage.addActor(rootTable);
    }

    public void localize(){
        rateButton.setText(game.bundle.get("main-menu.rate"));
        authorsButton.setText(game.bundle.get("main-menu.authors"));
        playButton.setText(game.bundle.get("main-menu.play"));
        tutorialButton.setText(game.bundle.get("main-menu.tutorial"));
        campaignButton.setText(game.bundle.get("main-menu.campaign"));
        settingsButton.setText(game.bundle.get("main-menu.settings"));
        exitButton.setText(game.bundle.get("main-menu.exit"));
        achievementsButton.setText(game.bundle.get("main-menu.achievements"));
    }

    @Override
    public void transitionBegin() {
        if(getTransitionScreen().getFrom() == this) {
            topBarTable.addAction(Actions.moveBy(0f, topBarTable.getHeight(), getTransitionScreen().getDuration() / 2f, Interpolation.fade));
            titleLabel.addAction(Actions.moveBy(-rootTable.getWidth(), 0f, getTransitionScreen().getDuration(), Interpolation.fade));
            playButton.addAction(Actions.moveBy(-rootTable.getWidth(), 0f, getTransitionScreen().getDuration(), Interpolation.fade));
            tutorialButton.addAction(Actions.moveBy(-rootTable.getWidth(), 0f, getTransitionScreen().getDuration(), Interpolation.fade));
            campaignButton.addAction(Actions.moveBy(-rootTable.getWidth(), 0f, getTransitionScreen().getDuration(), Interpolation.fade));
            settingsButton.addAction(Actions.moveBy(-rootTable.getWidth(), 0f, getTransitionScreen().getDuration(), Interpolation.fade));
            exitButton.addAction(Actions.moveBy(-rootTable.getWidth(), 0f, getTransitionScreen().getDuration(), Interpolation.fade));
            bottomBarTable.addAction(Actions.moveBy(0f, -bottomBarTable.getHeight(), getTransitionScreen().getDuration() / 2f, Interpolation.fade));
        }else{
            topBarTable.addAction(Actions.sequence(Actions.moveBy(0f, topBarTable.getHeight()), Actions.delay(getTransitionScreen().getDuration()/2f, Actions.moveBy(0f, -topBarTable.getHeight(), getTransitionScreen().getDuration() / 2f, Interpolation.fade))));
            titleLabel.addAction(Actions.sequence(Actions.moveBy(-rootTable.getWidth(), 0f), Actions.moveBy(rootTable.getWidth(), 0f, getTransitionScreen().getDuration(), Interpolation.fade)));
            playButton.addAction(Actions.sequence(Actions.moveBy(-rootTable.getWidth(), 0f), Actions.moveBy(rootTable.getWidth(), 0f, getTransitionScreen().getDuration(), Interpolation.fade)));
            tutorialButton.addAction(Actions.sequence(Actions.moveBy(-rootTable.getWidth(), 0f), Actions.moveBy(rootTable.getWidth(), 0f, getTransitionScreen().getDuration(), Interpolation.fade)));
            campaignButton.addAction(Actions.sequence(Actions.moveBy(-rootTable.getWidth(), 0f), Actions.moveBy(rootTable.getWidth(), 0f, getTransitionScreen().getDuration(), Interpolation.fade)));
            settingsButton.addAction(Actions.sequence(Actions.moveBy(-rootTable.getWidth(), 0f), Actions.moveBy(rootTable.getWidth(), 0f, getTransitionScreen().getDuration(), Interpolation.fade)));
            exitButton.addAction(Actions.sequence(Actions.moveBy(-rootTable.getWidth(), 0f), Actions.moveBy(rootTable.getWidth(), 0f, getTransitionScreen().getDuration(), Interpolation.fade)));
            bottomBarTable.addAction(Actions.sequence(Actions.moveBy(0f, -bottomBarTable.getHeight()), Actions.delay(getTransitionScreen().getDuration()/2f, Actions.moveBy(0f, bottomBarTable.getHeight(), getTransitionScreen().getDuration() / 2f, Interpolation.fade))));
        }
    }

    @Override
    public void transitionEnd() {
        rootTable.invalidate();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(inputMultiplexer);
        Gdx.gl.glClearColor(0.925490196f, 0.945098039f, 0.945098039f, 1f);
    }

    @Override
    public void render(float delta) {
        if(getTransitionScreen() == null || getTransitionScreen().isToOnTop() && getTransitionScreen().getFrom() == this || !getTransitionScreen().isToOnTop() && getTransitionScreen().getTo() == this) {
            backgroundStage.act(delta);
        }
        uiStage.act(delta);
        if(getTransitionScreen() == null || getTransitionScreen().isToOnTop() && getTransitionScreen().getFrom() == this || !getTransitionScreen().isToOnTop() && getTransitionScreen().getTo() == this) {
            backgroundStage.draw();
        }
        uiStage.draw();
    }

    @Override
    public void resize(int width, int height) {
        if((float)width/height > (float)SCREEN_WIDTH/SCREEN_HEIGHT){
            ((ScreenViewport)backgroundStage.getViewport()).setUnitsPerPixel((float)SCREEN_HEIGHT/height);
            ((ScreenViewport)uiStage.getViewport()).setUnitsPerPixel((float)SCREEN_HEIGHT/height);
        }else{
            ((ScreenViewport)backgroundStage.getViewport()).setUnitsPerPixel((float)SCREEN_WIDTH/width);
            ((ScreenViewport)uiStage.getViewport()).setUnitsPerPixel((float)SCREEN_WIDTH/width);
        }
        backgroundStage.getViewport().update(width, height, true);
        uiStage.getViewport().update(width, height, true);
        rootTable.validate();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
    }
}
