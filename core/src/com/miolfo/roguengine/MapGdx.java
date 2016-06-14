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
public class MapGdx{

    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private GameMap map;
    private Texture grass_t, forest_t, snow_t, desert_t;

    //Map parameters
    private int tileWidthPx, tileHeightPx, mapSize, screenHeight, screenWidth;
    private int mapVisibilityWidth, mapVisibilityHeight;

    public void create() {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);
        loadTextures();

        MapFactory mf = new MapFactory().MapSize(100).NoOfForests(8).SizeOfForests(8);
        map = mf.Generate();
        mapSize = map.GetSize();
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();
        tileWidthPx =  screenWidth / mapSize;
        tileHeightPx = screenHeight / mapSize;
        determineVisibleMapArea();
    }

    public void render() {
        renderMap();
    }

    public void resize(){
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();
    }

    public void renderMap(){
        renderPartialMap(0, mapVisibilityWidth, 0, mapVisibilityHeight);
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
        tileWidthPx = screenWidth / (xMax - xMin);
        tileHeightPx = screenHeight / (yMax - yMin);
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

    /**
     * Determine the zoom level of the map according to the screen size
     */
    private void determineVisibleMapArea(){
        int visibilityw = 32, visibilityh = 32;
        while(screenWidth % visibilityw != 0){
            visibilityw++;
        }
        while(screenHeight % visibilityh != 0){
            visibilityh++;
        }
        mapVisibilityWidth = visibilityw;
        mapVisibilityHeight = visibilityh;
        System.out.println("Visibility set to " + visibilityw + "," + visibilityh);
    }
}
