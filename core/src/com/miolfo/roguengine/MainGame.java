package com.miolfo.roguengine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.miolfo.gamelogic.*;
import com.miolfo.gamelogic.Character;
import com.miolfo.roguengine.UI.MainGameView;

import java.util.ArrayList;

/**
 * Created by Mikko Forsman on 14.6.2016.
 */

/**
 * Main gdx logic of the game
 */
public class MainGame implements Screen {
    private static SpriteBatch mBatch;
    private static Player mPlayer;
    private static GameMap mCurrentMap;
    private static ArrayList<Character> mNpcs = new ArrayList<Character>();

    private static MapGdx mMapGdx;
    private MainGameView mMainGameView;

    public static SpriteBatch SpriteBatchInstance(){
        return mBatch;
    }

    public static Player GetPlayer(){
        return mPlayer;
    }

    public MainGame(){
        create();
    }

    @Override
    public void show() {
        create();
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void hide() {

    }

    public void create() {
        mBatch = new SpriteBatch();
        mPlayer = new Player();
        mMapGdx = new MapGdx();
        mCurrentMap = mMapGdx.GetWorldMap();
        mPlayer.Move(new Position(mMapGdx.GetMapSize() / 2, mMapGdx.GetMapSize() / 2));
        System.out.println("Player set to " + mPlayer.GetPosition());
        mMainGameView = new MainGameView();
        //Create a monster close by the player, for debugging purposes
        //TODO: Another class for creating the monsters
        Monster testMonster = new Monster("Ogre", new Position(45,45));
        testMonster.SetTexture(new Texture(Gdx.files.internal("graphics/monster32.png")));
        mNpcs.add(testMonster);
    }

    @Override
    public void dispose() {
        mBatch.dispose();
    }

    @Override
    public void render(float v) {
        updateGameState();
        mMapGdx.renderAroundPos(mPlayer.GetPosition());
        mPlayer.render();
        for (Character c: mNpcs) {
            c.render();
        }
        mMainGameView.render();
    }


    @Override
    public void resize(int width, int height) {
        mMapGdx.resize();
    }

    public static GameMap GetCurrentMap(){
        return mCurrentMap;
    }

    //Public function for MapGdx since some MapGdx information
    //is required in rendering
    public static MapGdx GetMapGenerator(){ return mMapGdx;}

    private void updateGameState(){
        //TODO: Game logic here?
    }

}
