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
    SpriteBatch batch;
    ShapeRenderer shapeRenderer;
    Player player;
    Texture player_t;
    MapGdx mapGdx;

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
        mapGdx.render();
        renderPlayer();
    }


    private void renderPlayer(){
        batch.begin();
        batch.draw(player.GetTexture(), player.GetPosition().X() * mapGdx.GetTileWidthPixels(),
                player.GetPosition().Y() * mapGdx.GetTileHeightPixels(),
                mapGdx.GetTileWidthPixels(),
                mapGdx.GetTileHeightPixels());
        batch.end();
    }
}
