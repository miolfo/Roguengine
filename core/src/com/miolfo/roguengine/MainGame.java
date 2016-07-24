package com.miolfo.roguengine;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.miolfo.gamelogic.GameMap;
import com.miolfo.gamelogic.Player;
import com.miolfo.gamelogic.Position;
import com.miolfo.roguengine.UI.MainGameView;

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

    private MapGdx mMapGdx;
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
    }

    @Override
    public void dispose() {
        mBatch.dispose();
    }

    @Override
    public void render(float v) {
        updateGameState();
        mMapGdx.renderAroundPos(mPlayer.GetPosition());
        renderPlayer();
        mMainGameView.render();
    }


    @Override
    public void resize(int width, int height) {
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
