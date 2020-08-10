package com.wgsoft.game.stonewar.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
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

import java.util.Locale;

import static com.wgsoft.game.stonewar.Const.*;

public class SettingsScreen implements Screen, Localizable {
    private Stage backgroundStage;
    private Stage uiStage;

    private Label settingsLabel;
    private Label languageLabel;
    private Label musicLabel;
    private Label soundLabel;
    private TextButton backButton;
    private TextButton acceptButton;

    private InputMultiplexer inputMultiplexer;

    public SettingsScreen(){
        backgroundStage = game.bubbleBackgroundStage;
        uiStage = new Stage(new ScreenViewport(), game.batch);

        inputMultiplexer = new InputMultiplexer(uiStage, backgroundStage);

        Table rootTable = new Table(game.skin);
        rootTable.setFillParent(true);

        Image topBarImage = new Image(game.skin, "bar");
        rootTable.add(topBarImage).growX().height(BAR_HEIGHT);

        rootTable.row();
        rootTable.add().grow();
        rootTable.row();

        settingsLabel = new Label("settings.settings", game.skin, "boldLarge");
        rootTable.add(settingsLabel).expandX();

        rootTable.row();
        rootTable.add().grow();
        rootTable.row();

        Table wrapperTable = new Table(game.skin);

        languageLabel = new Label("settings.language", game.skin, "regularSmall");
        wrapperTable.add(languageLabel).growX();

        wrapperTable.row();

        final SelectBox <String> languageSelectBox = new SelectBox<>(game.skin, "language");
        languageSelectBox.setAlignment(Align.center);
        languageSelectBox.getList().getStyle().selection.setTopHeight((SETTINGS_LANGUAGE_LABEL_HEIGHT-languageSelectBox.getList().getItemHeight())/2f);
        languageSelectBox.getList().getStyle().selection.setBottomHeight((SETTINGS_LANGUAGE_LABEL_HEIGHT-languageSelectBox.getList().getItemHeight())/2f);
        languageSelectBox.getList().setAlignment(Align.center);
        languageSelectBox.setMaxListCount(MAX_SETTINGS_LANGUAGE_COUNT);
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

        final Slider musicSlider = new Slider(MIN_SETTINGS_MUSIC_VOLUME, MAX_SETTINGS_MUSIC_VOLUME, SETTINGS_MUSIC_VOLUME_STEP, false, game.skin, "music");
        musicSlider.getStyle().knobBefore = game.skin.getTiledDrawable("slider/music/knob-before");
        musicSlider.setStyle(musicSlider.getStyle());
        musicSlider.setValue(game.prefs.getFloat("settings.music"));
        musicStack.add(musicSlider);

        final Label musicPercentLabel = new Label(((int)musicSlider.getValue())+"%", game.skin, "regularSmall");
        musicPercentLabel.setAlignment(Align.center);
        musicPercentLabel.setTouchable(Touchable.disabled);
        musicSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                musicPercentLabel.setText(((int)musicSlider.getValue())+"%");
            }
        });
        musicStack.add(musicPercentLabel);

        wrapperTable.add(musicStack).growX();

        wrapperTable.row();
        wrapperTable.add().grow();
        wrapperTable.row();

        soundLabel = new Label("settings.sound", game.skin, "regularSmall");
        wrapperTable.add(soundLabel).growX();

        wrapperTable.row();

        Stack soundStack = new Stack();

        final Slider soundSlider = new Slider(MIN_SETTINGS_SOUND_VOLUME, MAX_SETTINGS_SOUND_VOLUME, SETTINGS_SOUND_VOLUME_STEP, false, game.skin, "sound");
        soundSlider.getStyle().knobBefore = game.skin.getTiledDrawable("slider/sound/knob-before");
        soundSlider.setStyle(soundSlider.getStyle());
        soundSlider.setValue(game.prefs.getFloat("settings.sound"));
        soundStack.add(soundSlider);

        final Label soundPercentLabel = new Label(((int)soundSlider.getValue())+"%", game.skin, "regularSmall");
        soundPercentLabel.setAlignment(Align.center);
        soundPercentLabel.setTouchable(Touchable.disabled);
        soundSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                soundPercentLabel.setText(((int)soundSlider.getValue())+"%");
            }
        });
        soundStack.add(soundPercentLabel);

        wrapperTable.add(soundStack).growX();

        rootTable.add(wrapperTable).growY();

        rootTable.row();
        rootTable.add().grow();
        rootTable.row();

        Table bottomBarTable = new Table(game.skin);
        bottomBarTable.setBackground("bar");

        backButton = new TextButton("back", game.skin, "transparent");
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.mainMenuScreen);
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
        backButton.setText(game.bundle.get("back"));
        acceptButton.setText(game.bundle.get("settings.accept"));
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(inputMultiplexer);
        Gdx.gl.glClearColor(0.925490196f, 0.945098039f, 0.945098039f, 1f);
    }

    @Override
    public void render(float delta) {
        game.batch.setColor(1f, 1f, 1f, 1f); //It makes background transparent for a second without this
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
