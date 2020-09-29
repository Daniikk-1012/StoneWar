package com.wgsoft.game.stonewar.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.wgsoft.game.stonewar.Localizable;
import com.wgsoft.game.stonewar.TransitionableScreen;
import com.wgsoft.game.stonewar.objects.matchsettings.Difficulty;
import com.wgsoft.game.stonewar.objects.matchsettings.Diplomacy;
import com.wgsoft.game.stonewar.objects.matchsettings.MapSize;

import static com.wgsoft.game.stonewar.Const.*;

public class MatchSettingsScreen extends TransitionableScreen implements Localizable {
    private Stage backgroundStage;
    private Stage uiStage;

    private Table rootTable;
    private Image topBarImage;
    private Label matchSettingsLabel;
    private Table wrapperTable;
    private Label difficultyLabel;
    private SelectBox<Difficulty> difficultySelectBox;
    private Label sizeLabel;
    private SelectBox<MapSize> sizeSelectBox;
    private Label playersLabel;
    private SelectBox<Integer> playersSelectBox;
    private Label botsLabel;
    private SelectBox<Integer> botsSelectBox;
    private Label diplomacyLabel;
    private SelectBox<Diplomacy> diplomacySelectBox;
    private Table bottomBarTable;
    private TextButton backButton;
    private TextButton startButton;

    private InputMultiplexer inputMultiplexer;

    public MatchSettingsScreen(){
        backgroundStage = game.bubbleBackgroundStage;
        uiStage = new Stage(new ScreenViewport(), game.batch);

        inputMultiplexer = new InputMultiplexer(uiStage, backgroundStage);

        rootTable = new Table(game.skin);
        rootTable.setFillParent(true);

        topBarImage = new Image(game.skin, "bar");
        rootTable.add(topBarImage).growX().height(BAR_HEIGHT);

        rootTable.row();
        rootTable.add().grow();
        rootTable.row();

        matchSettingsLabel = new Label("match-settings.match-settings", game.skin, "boldLarge");
        matchSettingsLabel.setAlignment(Align.center);
        matchSettingsLabel.setWrap(true);
        rootTable.add(matchSettingsLabel).growX();

        rootTable.row();
        rootTable.add().grow();
        rootTable.row();

        wrapperTable = new Table(game.skin);

        difficultyLabel = new Label("match-settings.difficulty", game.skin, "regularSmall");
        wrapperTable.add(difficultyLabel).growX();

        wrapperTable.row();

        difficultySelectBox = new SelectBox<>(game.skin, "red");
        difficultySelectBox.setAlignment(Align.center);
        difficultySelectBox.getList().setAlignment(Align.center);
        difficultySelectBox.setMaxListCount(MAX_SELECT_BOX_LIST_COUNT);
        difficultySelectBox.setItems(Difficulty.LOW, Difficulty.MEDIUM, Difficulty.HIGH);
        difficultySelectBox.setSelected(Difficulty.LOW);
        wrapperTable.add(difficultySelectBox);

        wrapperTable.row();
        wrapperTable.add().grow();
        wrapperTable.row();

        sizeLabel = new Label("match-settings.size", game.skin, "regularSmall");
        wrapperTable.add(sizeLabel).growX();

        wrapperTable.row();

        sizeSelectBox = new SelectBox<>(game.skin, "blue");
        sizeSelectBox.setAlignment(Align.center);
        sizeSelectBox.getList().setAlignment(Align.center);
        sizeSelectBox.setMaxListCount(MAX_SELECT_BOX_LIST_COUNT);
        sizeSelectBox.setItems(new MapSize(18, 18), new MapSize(24, 24), new MapSize(36,  36));
        sizeSelectBox.setSelectedIndex(0);
        wrapperTable.add(sizeSelectBox);

        wrapperTable.row();
        wrapperTable.add().grow();
        wrapperTable.row();

        playersLabel = new Label("match-settings.players", game.skin, "regularSmall");
        wrapperTable.add(playersLabel).growX();

        wrapperTable.row();

        playersSelectBox = new SelectBox<>(game.skin, "green");
        playersSelectBox.setAlignment(Align.center);
        playersSelectBox.getList().setAlignment(Align.center);
        playersSelectBox.setMaxListCount(MAX_SELECT_BOX_LIST_COUNT);
        for(int i = 1; i <= MAX_PLAYER_COUNT; i++){
            playersSelectBox.getItems().add(i);
        }
        playersSelectBox.setItems(playersSelectBox.getItems());
        playersSelectBox.setSelected(MAX_PLAYER_COUNT);
        playersSelectBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(playersSelectBox.getSelected()+botsSelectBox.getSelected() > MAX_PLAYER_COUNT){
                    botsSelectBox.setSelected(MAX_PLAYER_COUNT-playersSelectBox.getSelected());
                }else if(playersSelectBox.getSelected()+botsSelectBox.getSelected() < MIN_PLAYER_COUNT){
                    botsSelectBox.setSelected(MIN_PLAYER_COUNT-playersSelectBox.getSelected());
                }
            }
        });
        wrapperTable.add(playersSelectBox);

        wrapperTable.row();
        wrapperTable.add().grow();
        wrapperTable.row();

        botsLabel = new Label("match-settings.bots", game.skin, "regularSmall");
        wrapperTable.add(botsLabel).growX();

        wrapperTable.row();

        botsSelectBox = new SelectBox<>(game.skin, "violet");
        botsSelectBox.setAlignment(Align.center);
        botsSelectBox.getList().setAlignment(Align.center);
        for(int i = 0; i < MAX_PLAYER_COUNT; i++){
            botsSelectBox.getItems().add(i);
        }
        botsSelectBox.setItems(botsSelectBox.getItems());
        botsSelectBox.setSelected(0);
        botsSelectBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(botsSelectBox.getSelected()+playersSelectBox.getSelected() > MAX_PLAYER_COUNT){
                    playersSelectBox.setSelected(MAX_PLAYER_COUNT-botsSelectBox.getSelected());
                }else if(botsSelectBox.getSelected()+playersSelectBox.getSelected() < MIN_PLAYER_COUNT){
                    playersSelectBox.setSelected(MIN_PLAYER_COUNT-botsSelectBox.getSelected());
                }
            }
        });
        wrapperTable.add(botsSelectBox);

        wrapperTable.row();
        wrapperTable.add().grow();
        wrapperTable.row();

        diplomacyLabel = new Label("match-settings.diplomacy", game.skin, "regularSmall");
        wrapperTable.add(diplomacyLabel).growX();

        wrapperTable.row();

        diplomacySelectBox = new SelectBox<>(game.skin, "yellow");
        diplomacySelectBox.setAlignment(Align.center);
        diplomacySelectBox.getList().setAlignment(Align.center);
        diplomacySelectBox.setItems(Diplomacy.ON, Diplomacy.OFF);
        diplomacySelectBox.setSelected(Diplomacy.OFF);
        wrapperTable.add(diplomacySelectBox);

        rootTable.add(wrapperTable).growY();

        rootTable.row();
        rootTable.add().grow();
        rootTable.row();

        bottomBarTable = new Table(game.skin);
        bottomBarTable.setBackground("bar");

        backButton = new TextButton("back", game.skin, "transparent");
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.transitionScreen.setup(game.matchSettingsScreen, game.mainMenuScreen, false, SCREEN_TRANSITION_DURATION));
            }
        });
        bottomBarTable.add(backButton).padLeft(BAR_PADDING_HORIZONTAL);

        bottomBarTable.add().grow();

        startButton = new TextButton("match-settings.start", game.skin, "transparent");
        startButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.gameScreen.init(sizeSelectBox.getSelected().getWidth(), sizeSelectBox.getSelected().getHeight(), playersSelectBox.getSelected());
                game.setScreen(game.transitionScreen.setup(game.matchSettingsScreen, game.gameScreen, true, SCREEN_TRANSITION_DURATION));
            }
        });
        bottomBarTable.add(startButton).padRight(BAR_PADDING_HORIZONTAL);

        rootTable.add(bottomBarTable).growX().height(BAR_HEIGHT);

        uiStage.addActor(rootTable);
    }

    @Override
    public void localize() {
        matchSettingsLabel.setText(game.bundle.get("match-settings.match-settings"));
        difficultyLabel.setText(game.bundle.get("match-settings.difficulty"));
        difficultySelectBox.setItems(difficultySelectBox.getItems());
        sizeLabel.setText(game.bundle.get("match-settings.size"));
        playersLabel.setText(game.bundle.get("match-settings.players"));
        botsLabel.setText(game.bundle.get("match-settings.bots"));
        diplomacyLabel.setText(game.bundle.get("match-settings.diplomacy"));
        diplomacySelectBox.setItems(diplomacySelectBox.getItems());
        backButton.setText(game.bundle.get("match-settings.back"));
        startButton.setText(game.bundle.get("match-settings.start"));
    }

    @Override
    public void transitionBegin() {
        if(getTransitionScreen().getFrom() == this){
            if(getTransitionScreen().getTo() == game.mainMenuScreen) {
                topBarImage.addAction(Actions.moveBy(0f, topBarImage.getHeight(), getTransitionScreen().getDuration() / 2f, Interpolation.fade));
                matchSettingsLabel.addAction(Actions.moveBy(rootTable.getWidth(), 0f, getTransitionScreen().getDuration(), Interpolation.fade));
                wrapperTable.addAction(Actions.moveBy(rootTable.getWidth(), 0f, getTransitionScreen().getDuration(), Interpolation.fade));
            }else{
                backgroundStage.addAction(Actions.alpha(0f, getTransitionScreen().getDuration(), Interpolation.fade));
                matchSettingsLabel.addAction(Actions.moveBy(-rootTable.getWidth(), 0f, getTransitionScreen().getDuration(), Interpolation.fade));
                wrapperTable.addAction(Actions.moveBy(-rootTable.getWidth(), 0f, getTransitionScreen().getDuration(), Interpolation.fade));
            }
            topBarImage.addAction(Actions.moveBy(0f, topBarImage.getHeight(), getTransitionScreen().getDuration() / 2f, Interpolation.fade));
            bottomBarTable.addAction(Actions.moveBy(0f, -bottomBarTable.getHeight(), getTransitionScreen().getDuration() / 2f, Interpolation.fade));
        }else{
            difficultySelectBox.setSelected(Difficulty.LOW);
            sizeSelectBox.setSelectedIndex(0);
            playersSelectBox.setSelected(MAX_PLAYER_COUNT);
            botsSelectBox.setSelected(0);
            diplomacySelectBox.setSelected(Diplomacy.OFF);
            topBarImage.addAction(Actions.sequence(Actions.moveBy(0f, topBarImage.getHeight()), Actions.delay(getTransitionScreen().getDuration()/2f, Actions.moveBy(0f, -topBarImage.getHeight(), getTransitionScreen().getDuration()/2f, Interpolation.fade))));
            matchSettingsLabel.addAction(Actions.sequence(Actions.moveBy(rootTable.getWidth(), 0f), Actions.moveBy(-rootTable.getWidth(), 0f, getTransitionScreen().getDuration(), Interpolation.fade)));
            wrapperTable.addAction(Actions.sequence(Actions.moveBy(rootTable.getWidth(), 0f), Actions.moveBy(-rootTable.getWidth(), 0f, getTransitionScreen().getDuration(), Interpolation.fade)));
            bottomBarTable.addAction(Actions.sequence(Actions.moveBy(0f, -bottomBarTable.getHeight()), Actions.delay(getTransitionScreen().getDuration()/2f, Actions.moveBy(0f, bottomBarTable.getHeight(), getTransitionScreen().getDuration()/2f, Interpolation.fade))));
        }
    }

    @Override
    public void transitionEnd() {
        backgroundStage.getRoot().setColor(1f, 1f, 1f, 1f);
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
