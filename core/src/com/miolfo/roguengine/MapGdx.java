package com.miolfo.roguengine;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.miolfo.gamelogic.GameMap;
import com.miolfo.gamelogic.MapFactory;
import com.miolfo.gamelogic.Tile;

/**
 * Created by Mikko Forsman on 14.6.2016.
 */

/**
 * Class to handle all the operations that have to do with the map of the game
 */
public class MapGdx extends Game {

    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private GameMap map;
    private Texture grass_t, forest_t, snow_t, desert_t;

    //Map parameters
    private int tileWidthPx, tileHeightPx, mapSize;

    @Override
    public void create() {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);

        MapFactory mf = new MapFactory().MapSize(32).NoOfForests(3).SizeOfForests(3);
        map = mf.Generate();
        mapSize = map.GetSize();
        int w = Gdx.graphics.getWidth();
        int h = Gdx.graphics.getHeight();
        tileWidthPx =  w / mapSize;
        tileHeightPx = h / mapSize;
    }

    @Override
    public void render() {
        super.render();
        renderMap();
    }

    public void renderMap(){
        renderPartialMap(0, mapSize, 0, mapSize);
    }

    public int GetMapSize(){
        return mapSize;
    }

    public int GetTileWidthPixels(){
        return tileWidthPx;
    }

    public int GetTileHeightPixels(){
        return tileHeightPx;
    }

    private void renderPartialMap(int xMin, int xMax, int yMin, int yMax){
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for(int i = xMin; i < xMax; i++){
            for(int j = yMin; j < yMax; j++){
                Tile t = map.GetTile(i,j);
                batch.begin();
                switch(t){
                    case TILE_DESERT:
                        batch.draw(desert_t, i * tileWidthPx, j * tileHeightPx, tileWidthPx, tileHeightPx);
                        break;
                    case TILE_FOREST:
                        batch.draw(forest_t, i * tileWidthPx, j * tileHeightPx, tileWidthPx, tileHeightPx);
                        break;
                    case TILE_GRASS:
                        batch.draw(grass_t, i * tileWidthPx, j * tileHeightPx, tileWidthPx, tileHeightPx);
                        break;
                    case TILE_SNOW:
                        batch.draw(snow_t, i * tileWidthPx, j * tileHeightPx, tileWidthPx, tileHeightPx);
                        break;
                    default:
                        shapeRenderer.setColor(Color.WHITE);
                        break;
                }
                batch.end();
            }
        }
        shapeRenderer.end();
    }

    private void loadTextures(){
        grass_t = new Texture("grass64.png");
        forest_t = new Texture("forest64.png");
        snow_t = new Texture("snow64.png");
        desert_t = new Texture("desert64.png");
    }
}
