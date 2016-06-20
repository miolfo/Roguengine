package com.miolfo.roguengine;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.miolfo.gamelogic.GameMap;
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
    private static GameMap mCurrentMap;
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
        mCurrentMap = mMapGdx.GetWorldMap();
        mPlayer.Move(new Position(mMapGdx.GetMapSize() / 2, mMapGdx.GetMapSize() / 2));
        System.out.println("Player set to " + mPlayer.GetPosition());
        mBasicUi = new BasicUi();
    }

    @Override
    public void dispose() {
        super.dispose();
        mBatch.dispose();
    }

    @Override
    public void render() {
        //mBatch.begin();
        updateGameState();
        super.render();
        mMapGdx.renderAroundPos(mPlayer.GetPosition());
        //mMapGdx.renderWholeMap();
        renderPlayer();
        mBasicUi.render();
        //mBatch.end();
    }


    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        mMapGdx.resize();
    }

    public static GameMap GetCurrentMap(){
        return mCurrentMap;
    }

    private void renderPlayer(){
        mBatch.begin();
        Position playerInPx = mMapGdx.PositionToScreenCoordinates(mPlayer.GetPosition());
        mBatch.draw(mPlayer.GetTexture(),
                playerInPx.X(),
                playerInPx.Y(),
                mMapGdx.GetTileWidthPixels(),
                mMapGdx.GetTileHeightPixels());
        mBatch.end();
    }

    private void updateGameState(){
        //TODO: Game logic here?
    }
}
