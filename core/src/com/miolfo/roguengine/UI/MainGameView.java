package com.miolfo.roguengine.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.miolfo.roguengine.MainGame;

/**
 * Created by Mikko Forsman on 16.6.2016.
 * Class to handle the basic GUI during the game
 */
public class MainGameView {

    private BitmapFont mFont;

    private static GameConsole mGameConsole;
    private static MainGameButtons mMainGameButtons;

    public MainGameView(){
        create();
        mGameConsole = new GameConsole();
        mMainGameButtons = new MainGameButtons();
    }

    public void create() {
        mFont = new BitmapFont();
        mFont.setColor(Color.WHITE);
    }

    public void render() {
        mGameConsole.render();
        mMainGameButtons.render();
        //Render fps
        MainGame.SpriteBatchInstance().begin();
        mFont.draw(MainGame.SpriteBatchInstance(), "FPS: " + Gdx.graphics.getFramesPerSecond(), 0,20);
        MainGame.SpriteBatchInstance().end();
    }
}

