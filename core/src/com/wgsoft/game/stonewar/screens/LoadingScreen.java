package com.wgsoft.game.stonewar.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.Queue;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.wgsoft.game.stonewar.Localizable;
import com.wgsoft.game.stonewar.objects.loading.LoadingBubble;
import com.wgsoft.game.stonewar.objects.Bubble;

import static com.wgsoft.game.stonewar.Const.*;

public class LoadingScreen implements Screen, Localizable {
    public Skin skin;

    private Stage backgroundStage;
    private Stage uiStage;

    public float progress, prevProgress;

    private Label loadingLabel;

    private InputMultiplexer inputMultiplexer;

    private Queue<Runnable> runnableQueue;

    private boolean loaded;

    public LoadingScreen(){
        game.loadingScreen = this;

        skin = new Skin(Gdx.files.internal("loading/skin.json"));

        game.assetManager.load("img/skin.json", Skin.class);

        backgroundStage = new Stage(new ScreenViewport(), game.batch);
        uiStage = new Stage(new ScreenViewport(), game.batch);

        inputMultiplexer = new InputMultiplexer(uiStage, backgroundStage);

        runnableQueue = new Queue<>();

        for(int i = 0; i < LOADING_BUBBLE_COUNT; i++){
            backgroundStage.addActor(new LoadingBubble());
        }

        Table backRootTable = new Table(skin);
        backRootTable.setFillParent(true);

        backRootTable.add().grow();
        backRootTable.row();

        loadingLabel = new Label("loading.loading", skin, "regularSmall");
        backRootTable.add(loadingLabel).padBottom(LOADING_PADDING_BOTTOM);

        uiStage.addActor(backRootTable);

        Table frontRootTable = new Table(skin);
        frontRootTable.setFillParent(true);

        Label titleLabel = new Label(game.properties.getProperty("title"), skin, "boldLarge");
        titleLabel.setAlignment(Align.center);
        titleLabel.setWrap(true);
        frontRootTable.add(titleLabel).grow();

        uiStage.addActor(frontRootTable);
    }

    public void localize(){
        loadingLabel.setText(game.bundle.get("loading.loading"));
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(inputMultiplexer);
        Gdx.gl.glClearColor(0.925490196f, 0.945098039f, 0.945098039f, 1f);
    }

    @Override
    public void render(float delta) {
        prevProgress = progress;
        progress += LOADING_BUBBLE_SPEED*delta;
        while(progress >= 1f){
            progress -= 1f;
        }
        backgroundStage.act(delta);
        uiStage.act(delta);
        backgroundStage.draw();
        uiStage.draw();

        if(game.assetManager.update() && !loaded) {
            loaded = true;
            Thread thread = new Thread() {
                @Override
                public void run() {
                    game.skin = game.assetManager.get("img/skin.json");
                    ObjectMap.Entries<String, BitmapFont> entries = new ObjectMap.Entries<>(game.skin.getAll(BitmapFont.class));
                    for (ObjectMap.Entry<String, BitmapFont> entry : entries) {
                        entry.value.setUseIntegerPositions(false);
                        Array.ArrayIterator<TextureRegion> iterator = new Array.ArrayIterator<>(entry.value.getRegions());
                        for (final TextureRegion region : iterator) {
                            runnableQueue.addLast(new Runnable() {
                                @Override
                                public void run() {
                                    region.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
                                }
                            });
                        }
                    }
                    float selectionPaddingVertical = (SELECT_BOX_ITEM_HEIGHT-game.skin.getFont("regularSmall").getCapHeight()+game.skin.getFont("regularSmall").getDescent()*2f)/2f;
                    game.skin.getDrawable("selection").setTopHeight(selectionPaddingVertical);
                    game.skin.getDrawable("selection").setBottomHeight(selectionPaddingVertical);
                    //game.skin.get("red", Slider.SliderStyle.class).knobBefore = game.skin.getTiledDrawable("slider/red/knob-before");
                    game.skin.get("blue", Slider.SliderStyle.class).knobBefore = game.skin.getTiledDrawable("slider/blue/knob-before");
                    game.skin.get("green", Slider.SliderStyle.class).knobBefore = game.skin.getTiledDrawable("slider/green/knob-before");
                    //game.skin.get("violet", Slider.SliderStyle.class).knobBefore = game.skin.getTiledDrawable("slider/violet/knob-before");
                    //game.skin.get("yellow", Slider.SliderStyle.class).knobBefore = game.skin.getTiledDrawable("slider/yellow/knob-before");
                    runnableQueue.addFirst(new Runnable() {
                        @Override
                        public void run() {
                            game.bubbleBackgroundStage = new Stage(new ScreenViewport(), game.batch);
                        }
                    });
                    while(game.bubbleBackgroundStage == null){
                        try {
                            Thread.sleep(100);
                        }catch (InterruptedException ignored){
                        }
                    }
                    for (int i = 0; i < BUBBLE_COUNT; i++) {
                        Bubble bubble = new Bubble(true);
                        game.bubbleBackgroundStage.addActor(bubble);
                        bubble.setPositionFromPercent();
                    }
                    runnableQueue.addLast(new Runnable() {
                        @Override
                        public void run() {
                            game.mainMenuScreen = new MainMenuScreen();
                        }
                    });
                    runnableQueue.addLast(new Runnable() {
                        @Override
                        public void run() {
                            game.settingsScreen = new SettingsScreen();
                        }
                    });
                    runnableQueue.addLast(new Runnable() {
                        @Override
                        public void run() {
                            game.matchSettingsScreen = new MatchSettingsScreen();
                        }
                    });
                    while(
                            game.mainMenuScreen == null
                            || game.settingsScreen == null
                            || game.matchSettingsScreen == null
                    ){
                        try {
                            Thread.sleep(100);
                        }catch (InterruptedException ignored){
                        }
                    }
                    game.loaded = true;
                    game.localize();
                }
            };
            thread.start();
        }else if(runnableQueue.notEmpty()) {
            runnableQueue.removeFirst().run();
        }else if(game.loaded){
            game.setScreen(game.mainMenuScreen);
        }
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
        dispose();
    }

    @Override
    public void dispose() {
        skin.dispose();
        game.loadingScreen = null;
    }
}
