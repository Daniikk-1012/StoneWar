package com.wgsoft.game.stonewar.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.wgsoft.game.stonewar.objects.mainmenu.MenuBubble;

import static com.wgsoft.game.stonewar.MyGdxGame.game;
import static com.wgsoft.game.stonewar.Const.*;

public class MainMenuScreen implements Screen {
    private Stage backgroundStage;
    private Stage uiStage;

    private TextButton rateButton;
    private TextButton authorsButton;
    private Label titleLabel;
    private TextButton playButton;
    private TextButton tutorialButton;
    private TextButton campaignButton;
    private TextButton settingsButton;
    private TextButton exitButton;
    private TextButton achievementsButton;
    private Label versionLabel;

    private InputMultiplexer inputMultiplexer;

    public MainMenuScreen(){
        backgroundStage = new Stage(new ScreenViewport(), game.batch);
        uiStage = new Stage(new ScreenViewport(), game.batch);

        inputMultiplexer = new InputMultiplexer(uiStage, backgroundStage);

        for(int i = 0; i < MENU_BUBBLE_COUNT; i++){
            MenuBubble menuBubble = new MenuBubble(true);
            backgroundStage.addActor(menuBubble);
            menuBubble.setPositionFromPercent();
        }

        Table rootTable = new Table(game.skin);
        rootTable.setFillParent(true);

        Table topBarTable = new Table(game.skin);
        topBarTable.setBackground("bar");

        rateButton = new TextButton("main-menu.rate", game.skin, "transparent");
        topBarTable.add(rateButton).growY().padLeft(BAR_HORIZONTAL_PADDING);

        topBarTable.add().grow();

        authorsButton = new TextButton("main-menu.authors", game.skin, "transparent");
        topBarTable.add(authorsButton).growY().padRight(BAR_HORIZONTAL_PADDING);

        rootTable.add(topBarTable).growX().height(BAR_HEIGHT);

        rootTable.row();
        rootTable.add().grow();
        rootTable.row();

        titleLabel = new Label("main-menu.title", game.skin, "bold");
        rootTable.add(titleLabel).expandX();

        rootTable.row();
        rootTable.add().grow();
        rootTable.row();

        playButton = new TextButton("main-menu.play", game.skin, "play");
        rootTable.add(playButton).expandX();

        rootTable.row();
        rootTable.add().grow();
        rootTable.row();

        tutorialButton = new TextButton("main-menu.tutorial", game.skin, "tutorial");
        rootTable.add(tutorialButton).expandX();

        rootTable.row();
        rootTable.add().grow();
        rootTable.row();

        campaignButton = new TextButton("main-menu.campaign", game.skin, "campaign");
        rootTable.add(campaignButton).expandX();

        rootTable.row();
        rootTable.add().grow();
        rootTable.row();

        settingsButton = new TextButton("main-menu.settings", game.skin, "settings");
        rootTable.add(settingsButton).expandX();

        rootTable.row();
        rootTable.add().grow();
        rootTable.row();

        exitButton = new TextButton("main-menu.exit", game.skin, "exit");
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

        Table bottomBar = new Table(game.skin);
        bottomBar.setBackground("bar");

        achievementsButton = new TextButton("main-menu.achievements", game.skin, "transparent");
        bottomBar.add(achievementsButton).growY().padLeft(BAR_HORIZONTAL_PADDING);

        bottomBar.add().grow();

        versionLabel = new Label("main-menu.version", game.skin, "regularLarge");
        bottomBar.add(versionLabel).growY().padRight(BAR_HORIZONTAL_PADDING);

        rootTable.add(bottomBar).growX().height(BAR_HEIGHT);

        uiStage.addActor(rootTable);
    }

    public void localize(){
        rateButton.setText(game.bundle.get("main-menu.rate"));
        authorsButton.setText(game.bundle.get("main-menu.authors"));
        titleLabel.setText(game.bundle.get("main-menu.title"));
        playButton.setText(game.bundle.get("main-menu.play"));
        tutorialButton.setText(game.bundle.get("main-menu.tutorial"));
        campaignButton.setText(game.bundle.get("main-menu.campaign"));
        settingsButton.setText(game.bundle.get("main-menu.settings"));
        exitButton.setText(game.bundle.get("main-menu.exit"));
        achievementsButton.setText(game.bundle.get("main-menu.achievements"));
        versionLabel.setText(game.bundle.get("main-menu.version"));
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(inputMultiplexer);
        Gdx.gl.glClearColor(0.925490196f, 0.945098039f, 0.945098039f, 1f);
    }

    @Override
    public void render(float delta) {
        backgroundStage.act(delta);
        uiStage.act(delta);
        backgroundStage.draw();
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
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
    }

    @Override
    public void dispose() {
    }
}
