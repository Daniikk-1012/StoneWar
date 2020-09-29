package com.wgsoft.game.stonewar.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.wgsoft.game.stonewar.Localizable;
import com.wgsoft.game.stonewar.TransitionableScreen;
import com.wgsoft.game.stonewar.objects.game.CellColor;
import com.wgsoft.game.stonewar.objects.game.Map;
import com.wgsoft.game.stonewar.objects.game.Player;

import static com.wgsoft.game.stonewar.Const.*;

public class GameScreen extends TransitionableScreen implements Localizable {
    private Stage backgroundStage;
    private Stage uiStage;

    private Table rootTable;
    private Table topBarTable;
    private Label turnLabel;
    private Label platformLabel;
    private Map map;
    private ScrollPane mapScrollPane;
    private Table bottomBarTable;

    private InputMultiplexer inputMultiplexer;
    
    private Player[] players;
    private int turn;

    public GameScreen(){
        backgroundStage = new Stage(new ScreenViewport(), game.batch);
        uiStage = new Stage(new ScreenViewport(), game.batch);

        inputMultiplexer = new InputMultiplexer(uiStage, backgroundStage);

        rootTable = new Table(game.skin);
        rootTable.setFillParent(true);

        topBarTable = new Table(game.skin);
        topBarTable.setBackground("bar");

        turnLabel = new Label("game.turn", game.skin, "regularLarge");
        topBarTable.add(turnLabel).padLeft(BAR_PADDING_HORIZONTAL);

        topBarTable.add().grow();

        platformLabel = new Label("game.platform", game.skin, "regularLarge");
        topBarTable.add(platformLabel).padRight(BAR_PADDING_HORIZONTAL);

        rootTable.add(topBarTable).growX().height(BAR_HEIGHT);

        rootTable.row();

        map = new Map();

        mapScrollPane = new ScrollPane(map){
            @Override
            public void draw(Batch batch, float parentAlpha) {
                super.draw(batch, parentAlpha);
                setSmoothScrolling(true);
            }
        };
        mapScrollPane.setFlickScroll(false);
        mapScrollPane.removeListener(mapScrollPane.getListeners().peek());
        mapScrollPane.setFlickScroll(true);
        mapScrollPane.addListener(new InputListener(){
            @Override
            public boolean scrolled(InputEvent event, float x, float y, int amount) {
                float scale = map.getScaleX();
                float scrollX = mapScrollPane.getScrollX()+mapScrollPane.getWidth()/2f, scrollY = mapScrollPane.getScrollY()+mapScrollPane.getHeight()/2f;
                float delta = -GAME_SCROLL_SCALE_AMOUNT*amount;
                if(scale+delta < MIN_GAME_MAP_SCALE){
                    map.setScale(MIN_GAME_MAP_SCALE);
                }else if(scale+delta > MAX_GAME_MAP_SCALE){
                    map.setScale(MAX_GAME_MAP_SCALE);
                }else{
                    map.scaleBy(delta);
                }
                mapScrollPane.invalidate();
                mapScrollPane.validate();
                mapScrollPane.setSmoothScrolling(false);
                mapScrollPane.setScrollX(scrollX*map.getScaleX()/scale-mapScrollPane.getWidth()/2f);
                mapScrollPane.setScrollY(scrollY*map.getScaleY()/scale-mapScrollPane.getHeight()/2f);
                return true;
            }
        });
        mapScrollPane.addListener(new ActorGestureListener(){
            @Override
            public void zoom(InputEvent event, float initialDistance, float distance) {
                float scale = map.getScaleX();
                float scrollX = mapScrollPane.getScrollX()+mapScrollPane.getWidth()/2f, scrollY = mapScrollPane.getScrollY()+mapScrollPane.getHeight()/2f;
                float grow = distance/initialDistance;
                if(scale*grow < MIN_GAME_MAP_SCALE){
                    map.setScale(MIN_GAME_MAP_SCALE);
                }else{
                    map.setScale(Math.min(scale * grow, MAX_GAME_MAP_SCALE));
                }
                mapScrollPane.invalidate();
                mapScrollPane.validate();
                mapScrollPane.setSmoothScrolling(false);
                mapScrollPane.setScrollX(scrollX*map.getScaleX()/scale-mapScrollPane.getWidth()/2f);
                mapScrollPane.setScrollY(scrollY*map.getScaleY()/scale-mapScrollPane.getHeight()/2f);
            }
        });
        uiStage.setScrollFocus(mapScrollPane);
        rootTable.add(mapScrollPane).grow();

        rootTable.row();

        bottomBarTable = new Table(game.skin);
        bottomBarTable.setBackground("bar");

        rootTable.add(bottomBarTable).growX().height(BAR_HEIGHT);

        uiStage.addActor(rootTable);
    }

    public void init(int width, int height, int players){
        turn = 0;
        this.players = new Player[players];
        for(int i = 0; i < players; i++){
            this.players[i] = new Player(CellColor.values()[i]);
        }
        map.init(width, height);
    }

    @Override
    public void localize() {
        turnLabel.setText(game.bundle.format("game.turn", 1));
        platformLabel.setText(game.bundle.format("game.platform", 4));
    }

    @Override
    public void transitionBegin() {
        if(getTransitionScreen().getFrom() == this){
        }else{
            topBarTable.addAction(Actions.sequence(Actions.moveBy(0f, topBarTable.getHeight()), Actions.delay(getTransitionScreen().getDuration()/2f, Actions.moveBy(0f, -topBarTable.getHeight(), getTransitionScreen().getDuration()/2f, Interpolation.fade))));
            mapScrollPane.addAction(Actions.sequence(Actions.alpha(0f), Actions.alpha(1f, getTransitionScreen().getDuration(), Interpolation.fade)));
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
