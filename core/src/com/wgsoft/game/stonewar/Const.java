package com.wgsoft.game.stonewar;

import com.wgsoft.game.stonewar.screens.LoadingScreen;

public class Const {
    public static MyGdxGame game;

    public static final int SCREEN_WIDTH = 1080;
    public static final int SCREEN_HEIGHT = 1920;
    public static final int BUBBLE_COUNT = 10;
    public static final int LOADING_BUBBLE_COUNT = 20;
    public static final int MAX_SELECT_BOX_LIST_COUNT = 3;
    public static final int MIN_PLAYER_COUNT = 2;
    public static final int MAX_PLAYER_COUNT = 4;
    public static final float BAR_HEIGHT = 127f;
    public static final float BAR_PADDING_HORIZONTAL = 32f;
    public static final float MIN_BUBBLE_SIZE = 72f;
    public static final float MAX_BUBBLE_SIZE = 192f;
    public static final float MIN_LOADING_BUBBLE_SIZE = MIN_BUBBLE_SIZE;
    public static final float MAX_LOADING_BUBBLE_SIZE = MAX_BUBBLE_SIZE;
    public static final float MIN_BUBBLE_SPEED = 0.05f;
    public static final float MAX_BUBBLE_SPEED = 0.2f;
    public static final float LOADING_PADDING_BOTTOM = 128f;
    public static final float LOADING_BUBBLE_SPEED = 0.5f;
    public static final float LOADING_BUBBLE_POS_AMPLITUDE = 0.1f;
    public static final float SELECT_BOX_ITEM_HEIGHT = 103f;
    public static final float DEFAULT_SETTINGS_MUSIC_VOLUME = 50f;
    public static final float DEFAULT_SETTINGS_SOUND_VOLUME = 50f;
    public static final float MIN_SETTINGS_MUSIC_VOLUME = 0f;
    public static final float MAX_SETTINGS_MUSIC_VOLUME = 100f;
    public static final float SETTINGS_MUSIC_VOLUME_STEP = 1f;
    public static final float MIN_SETTINGS_SOUND_VOLUME = 0f;
    public static final float MAX_SETTINGS_SOUND_VOLUME = 100f;
    public static final float SETTINGS_SOUND_VOLUME_STEP = 1f;
    public static final float SCREEN_TRANSITION_DURATION = 1.5f;
    public static final String BUBBLE_PATH = "bubble";
    public static final String[] BUBBLES = new String[]{
            "blue",
            "yellow",
            "violet",
            "green",
            "red"
    };
    public static final String[] LOADING_BUBBLES = BUBBLES;
    public static final String[] SETTINGS_LANGUAGES = new String[]{
            "en",
            "ru_RU"
    };
}
