package com.miolfo.roguengine;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.miolfo.gamelogic.Player;
import com.miolfo.gamelogic.Position;

/**
 * Created by Mikko Forsman on 14.6.2016.
 */

/**
 * Main gdx logic of the game
 */
public class MainGame extends Game {
    static SpriteBatch batch;
    ShapeRenderer shapeRenderer;
    Player player;
    Texture player_t;
    MapGdx mapGdx;

    public static SpriteBatch SpriteBatchInstance(){
        return batch;
    }

    public MainGame(){
        create();
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        player = new Player();
        player_t = new Texture("player64.png");
        player.SetTexture(player_t);
        mapGdx = new MapGdx();
        mapGdx.create();
        player.Move(new Position(mapGdx.GetMapSize() / 2, mapGdx.GetMapSize() / 2));
        System.out.println("Player set to " + player.GetPosition());
    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
        shapeRenderer.dispose();
    }

    @Override
    public void render() {
        super.render();
        mapGdx.renderAroundPos(player.GetPosition());
        //mapGdx.renderWholeMap();
        renderPlayer();
    }


    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        mapGdx.resize();
    }

    private void renderPlayer(){
        batch.begin();
        batch.draw(player.GetTexture(),
                mapGdx.GetMapWidthPixels() / 2,
                mapGdx.GetMapHeightPixels() / 2,
                mapGdx.GetTileWidthPixels(),
                mapGdx.GetTileHeightPixels());
        batch.end();
    }
}
