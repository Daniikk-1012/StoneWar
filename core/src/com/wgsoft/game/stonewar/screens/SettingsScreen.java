package com.wgsoft.game.stonewar.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.wgsoft.game.stonewar.Localizable;
import com.wgsoft.game.stonewar.TransitionableScreen;

import java.util.Locale;

import static com.wgsoft.game.stonewar.Const.*;

public class SettingsScreen extends TransitionableScreen implements Localizable {
    private Stage backgroundStage;
    private Stage uiStage;

    private Table rootTable;
    private Image topBarImage;
    private Label settingsLabel;
    private Table wrapperTable;
    private Label languageLabel;
    private SelectBox<String> languageSelectBox;
    private Label musicLabel;
    private Slider musicSlider;
    private Label musicPercentLabel;
    private Label soundLabel;
    private Slider soundSlider;
    private Label soundPercentLabel;
    private Table bottomBarTable;
    private TextButton backButton;
    private TextButton acceptButton;

    private InputMultiplexer inputMultiplexer;

    public SettingsScreen(){
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

        settingsLabel = new Label("settings.settings", game.skin, "boldLarge");
        settingsLabel.setAlignment(Align.center);
        settingsLabel.setWrap(true);
        rootTable.add(settingsLabel).growX();

        rootTable.row();
        rootTable.add().grow();
        rootTable.row();

        wrapperTable = new Table(game.skin);

        languageLabel = new Label("settings.language", game.skin, "regularSmall");
        wrapperTable.add(languageLabel).growX();

        wrapperTable.row();

        languageSelectBox = new SelectBox<>(game.skin, "red");
        languageSelectBox.setAlignment(Align.center);
        languageSelectBox.getList().setAlignment(Align.center);
        languageSelectBox.setMaxListCount(MAX_SELECT_BOX_LIST_COUNT);
        int select = 0;
        for(int i = 0; i < SETTINGS_LANGUAGES.length; i++){
            if(SETTINGS_LANGUAGES[i].equals(game.prefs.getString("settings.language"))){
                select = i;
            }
            String[] localeStrings = SETTINGS_LANGUAGES[i].split("_");
            Locale locale;
            if(localeStrings.length == 1) {
                locale = new Locale(localeStrings[0]);
            }else if(localeStrings.length == 2){
                locale = new Locale(localeStrings[0], localeStrings[1]);
            }else{
                locale = new Locale(localeStrings[0], localeStrings[1], localeStrings[2]);
            }
            languageSelectBox.getItems().add(locale.getDisplayName(locale));
        }
        languageSelectBox.setItems(languageSelectBox.getItems());
        languageSelectBox.setSelectedIndex(select);
        wrapperTable.add(languageSelectBox);

        wrapperTable.row();
        wrapperTable.add().grow();
        wrapperTable.row();

        musicLabel = new Label("settings.music", game.skin, "regularSmall");
        wrapperTable.add(musicLabel).growX();

        wrapperTable.row();

        Stack musicStack = new Stack();

        musicSlider = new Slider(MIN_SETTINGS_MUSIC_VOLUME, MAX_SETTINGS_MUSIC_VOLUME, SETTINGS_MUSIC_VOLUME_STEP, false, game.skin, "blue"){
            @Override
            public float getPrefWidth() {
                return getStyle().background.getLeftWidth()+getStyle().background.getMinWidth()+getStyle().background.getRightWidth();
            }
        };
        musicSlider.setValue(game.prefs.getFloat("settings.music"));
        musicSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                musicPercentLabel.setText(((int)musicSlider.getValue())+"%");
            }
        });
        musicStack.add(musicSlider);

        musicPercentLabel = new Label(((int)musicSlider.getValue())+"%", game.skin, "regularSmall");
        musicPercentLabel.setAlignment(Align.center);
        musicPercentLabel.setTouchable(Touchable.disabled);
        musicStack.add(musicPercentLabel);

        wrapperTable.add(musicStack);

        wrapperTable.row();
        wrapperTable.add().grow();
        wrapperTable.row();

        soundLabel = new Label("settings.sound", game.skin, "regularSmall");
        wrapperTable.add(soundLabel).growX();

        wrapperTable.row();

        Stack soundStack = new Stack();

        soundSlider = new Slider(MIN_SETTINGS_SOUND_VOLUME, MAX_SETTINGS_SOUND_VOLUME, SETTINGS_SOUND_VOLUME_STEP, false, game.skin, "green"){
            @Override
            public float getPrefWidth() {
                return getStyle().background.getLeftWidth()+getStyle().background.getMinWidth()+getStyle().background.getRightWidth();
            }
        };
        soundSlider.setValue(game.prefs.getFloat("settings.sound"));
        soundSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                soundPercentLabel.setText(((int)soundSlider.getValue())+"%");
            }
        });
        soundStack.add(soundSlider);

        soundPercentLabel = new Label(((int)soundSlider.getValue())+"%", game.skin, "regularSmall");
        soundPercentLabel.setAlignment(Align.center);
        soundPercentLabel.setTouchable(Touchable.disabled);
        soundStack.add(soundPercentLabel);

        wrapperTable.add(soundStack);

        rootTable.add(wrapperTable).growY();

        rootTable.row();
        rootTable.add().grow();
        rootTable.row();

        bottomBarTable = new Table(game.skin);
        bottomBarTable.setBackground("bar");

        backButton = new TextButton("settings.back", game.skin, "transparent");
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.transitionScreen.setup(game.settingsScreen, game.mainMenuScreen, false, SCREEN_TRANSITION_DURATION));
            }
        });
        bottomBarTable.add(backButton).padLeft(BAR_PADDING_HORIZONTAL);

        bottomBarTable.add().grow();

        acceptButton = new TextButton("settings.accept", game.skin, "transparent");
        acceptButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.prefs.putString("settings.language", SETTINGS_LANGUAGES[languageSelectBox.getSelectedIndex()]);
                game.prefs.putFloat("settings.music", musicSlider.getValue());
                game.prefs.putFloat("settings.sound", soundSlider.getValue());
                game.prefs.flush();
                game.init();
            }
        });
        bottomBarTable.add(acceptButton).padRight(BAR_PADDING_HORIZONTAL);

        rootTable.add(bottomBarTable).growX().height(BAR_HEIGHT);

        uiStage.addActor(rootTable);
    }

    public void localize(){
        settingsLabel.setText(game.bundle.get("settings.settings"));
        languageLabel.setText(game.bundle.get("settings.language"));
        musicLabel.setText(game.bundle.get("settings.music"));
        soundLabel.setText(game.bundle.get("settings.sound"));
        backButton.setText(game.bundle.get("settings.back"));
        acceptButton.setText(game.bundle.get("settings.accept"));
    }

    @Override
    public void transitionBegin() {
        if(getTransitionScreen().getFrom() == this){
            topBarImage.addAction(Actions.moveBy(0f, topBarImage.getHeight(), getTransitionScreen().getDuration()/2f, Interpolation.fade));
            settingsLabel.addAction(Actions.moveBy(rootTable.getWidth(), 0f, getTransitionScreen().getDuration(), Interpolation.fade));
            wrapperTable.addAction(Actions.moveBy(rootTable.getWidth(), 0f, getTransitionScreen().getDuration(), Interpolation.fade));
            bottomBarTable.addAction(Actions.moveBy(0f, -bottomBarTable.getHeight(), getTransitionScreen().getDuration()/2f, Interpolation.fade));
        }else{
            for(int i = 0; i < SETTINGS_LANGUAGES.length; i++){
                if(SETTINGS_LANGUAGES[i].equals(game.prefs.getString("settings.language"))){
                    languageSelectBox.setSelectedIndex(i);
                    break;
                }
            }
            musicSlider.setValue(game.prefs.getFloat("settings.music"));
            musicPercentLabel.setText(((int)musicSlider.getValue())+"%");
            soundSlider.setValue(game.prefs.getFloat("settings.sound"));
            soundPercentLabel.setText(((int)soundSlider.getValue())+"%");

            topBarImage.addAction(Actions.sequence(Actions.moveBy(0f, topBarImage.getHeight()), Actions.delay(getTransitionScreen().getDuration()/2f, Actions.moveBy(0f, -topBarImage.getHeight(), getTransitionScreen().getDuration()/2f, Interpolation.fade))));
            settingsLabel.addAction(Actions.sequence(Actions.moveBy(rootTable.getWidth(), 0f), Actions.moveBy(-rootTable.getWidth(), 0f, getTransitionScreen().getDuration(), Interpolation.fade)));
            wrapperTable.addAction(Actions.sequence(Actions.moveBy(rootTable.getWidth(), 0f), Actions.moveBy(-rootTable.getWidth(), 0f, getTransitionScreen().getDuration(), Interpolation.fade)));
            bottomBarTable.addAction(Actions.sequence(Actions.moveBy(0f, -bottomBarTable.getHeight()), Actions.delay(getTransitionScreen().getDuration()/2f, Actions.moveBy(0f, bottomBarTable.getHeight(), getTransitionScreen().getDuration()/2f, Interpolation.fade))));
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
        game.batch.setColor(1f, 1f, 1f, 1f); //It makes background transparent for a second without this
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
