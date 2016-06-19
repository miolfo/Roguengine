package com.miolfo.roguengine;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.miolfo.gamelogic.Player;
import com.miolfo.gamelogic.Position;

/**
 * Created by Mikko Forsman on 14.6.2016.
 */

/**
 * Main gdx logic of the game
 */
public class MainGame extends Game {
    private static SpriteBatch mBatch;
    private static Player mPlayer;
    private Texture mPlayer_t;
    private MapGdx mMapGdx;
    private BasicUi mBasicUi;

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
    public void create() {
        mBatch = new SpriteBatch();
        mPlayer = new Player();
        mPlayer_t = new Texture("player64.png");
        mPlayer.SetTexture(mPlayer_t);
        mMapGdx = new MapGdx();
        mMapGdx.create();
        mPlayer.Move(new Position(mMapGdx.GetMapSize() / 2, mMapGdx.GetMapSize() / 2));
        System.out.println("Player set to " + mPlayer.GetPosition());

        mBasicUi = new BasicUi();
        mBasicUi.create();
    }

    @Override
    public void dispose() {
        super.dispose();
        mBatch.dispose();
    }

    @Override
    public void render() {
        super.render();
        mMapGdx.renderAroundPos(mPlayer.GetPosition());
        //mMapGdx.renderWholeMap();
        renderPlayer();
        mBasicUi.render();
    }


    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        mMapGdx.resize();
    }

    private void renderPlayer(){
        mBatch.begin();
        mBatch.draw(mPlayer.GetTexture(),
                mMapGdx.GetMapWidthPixels() / 2,
                mMapGdx.GetMapHeightPixels() / 2,
                mMapGdx.GetTileWidthPixels(),
                mMapGdx.GetTileHeightPixels());
        mBatch.end();
    }
}
