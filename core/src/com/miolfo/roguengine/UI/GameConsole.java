package com.miolfo.roguengine.UI;

/**
 * Created by Mikko Forsman on 20.6.2016.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.miolfo.roguengine.MainGame;

import java.util.ArrayList;

/**
 * Class to handle the "console" visible in the game screen
 */
public class GameConsole {

    private static final int TEXT_HEIGHT_SEPARATION = 8;    //Pixels between each line in console
    private static final int MAX_NUMBER_OF_LINES = 20;      //Maximum number of lines stored

    private int mConsoleHeight;
    private int mVisibleLines;
    private float mTextHeight;

    private BitmapFont mFont;
    private ShapeRenderer mShapeRenderer;

    private static ArrayList<String> mLines = new ArrayList<String>(MAX_NUMBER_OF_LINES);

    public GameConsole(){
        create();
    }

    private void create(){
        mFont = new BitmapFont();
        mShapeRenderer = new ShapeRenderer();
        mShapeRenderer.setAutoShapeType(true);
        mFont.setColor(Color.WHITE);
        mConsoleHeight = Gdx.graphics.getHeight() / 8;
        mTextHeight = mFont.getXHeight() + TEXT_HEIGHT_SEPARATION;
        mVisibleLines = (int) (mConsoleHeight / mTextHeight);

    }

    public void render(){
        //Render the black box for the console
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        mShapeRenderer.begin();
        mShapeRenderer.set(ShapeRenderer.ShapeType.Filled);
        mShapeRenderer.setColor(0, 0, 0, 0.5f);
        mShapeRenderer.rect(0, Gdx.graphics.getHeight() - mConsoleHeight, Gdx.graphics.getWidth(), mConsoleHeight);
        mShapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        //Render the latest texts
        renderLines();
    }

    public static void WriteLine(String line){
        mLines.add(line);
        //If too many lines of text are stored, remove the oldest ones
        if(mLines.size() > MAX_NUMBER_OF_LINES){
            for(int i = 0; i < mLines.size() - MAX_NUMBER_OF_LINES; i++){
                mLines.remove(i);
            }
        }
    }

    private void renderLines(){
        MainGame.SpriteBatchInstance().begin();
        //Draw latest mVisibleLines
        for(int i = 0; i < Math.min(mVisibleLines, mLines.size()); i++){
            String drawn = mLines.get(mLines.size()-i-1);
            mFont.draw(MainGame.SpriteBatchInstance(), drawn, 0, Gdx.graphics.getHeight() - mTextHeight*i);
        }
        MainGame.SpriteBatchInstance().end();
    }
}
