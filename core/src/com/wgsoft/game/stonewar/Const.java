package com.wgsoft.game.stonewar;

import com.wgsoft.game.stonewar.screens.LoadingScreen;

public class Const {
    public static MyGdxGame game; //Static MyGdxGame instance
    public static LoadingScreen loadingScreen; //Static LoadingScreen instance (Disposed after hidden)
    public static final int SCREEN_WIDTH = 1080; //Minimal virtual screen width
    public static final int SCREEN_HEIGHT = 1920; //Minimal virtual screen height
    public static final int MENU_BUBBLE_COUNT = 10; //Bubble count in main menu
    public static final int LOADING_BUBBLE_COUNT = 20; //Bubble count in loading screen
    public static final float BAR_HEIGHT = 127f; //Bar height
    public static final float BAR_PADDING_HORIZONTAL = 32f; //Horizontal padding for bar
    public static final float MIN_COLOR_BUBBLE_SIZE = 72f; //For main menu and loading screen
    public static final float MAX_COLOR_BUBBLE_SIZE = 192f; //For main menu and loading screen
    public static final float MIN_MENU_BUBBLE_SPEED = 0.05f; //Animations per second for main menu bubble
    public static final float MAX_MENU_BUBBLE_SPEED = 0.2f; //Animations per second for main menu bubble
    public static final float LOADING_PADDING_BOTTOM = 128f; //Bottom padding for loading label in loading screen
    public static final float LOADING_BUBBLE_SPEED = 0.5f; //Animations per second for loading bubble
    public static final float LOADING_BUBBLE_POS_AMPLITUDE = 0.1f; //Amplitude of FROM position of bubble in loading screen
    public static final String[] COLOR_BUBBLES = new String[]{
            "blue",
            "peach",
            "violet",
            "green",
            "red"
    }; //Names of bubbles for main menu and loading screen
}
