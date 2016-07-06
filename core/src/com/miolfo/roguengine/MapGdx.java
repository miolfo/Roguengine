package com.miolfo.roguengine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.miolfo.gamelogic.GameMap;
import com.miolfo.gamelogic.MapFactory;
import com.miolfo.gamelogic.Position;
import com.miolfo.gamelogic.Tile;

/**
 * Created by Mikko Forsman on 14.6.2016.
 */

/**
 * Class to handle all the operations that have to do with the maps of the game
 */
public class MapGdx{

    private static MapGdx instance;

    private static ShapeRenderer shapeRenderer;
    private GameMap worldMap;
    private Skin mSkin;
    private TextureAtlas mTextureAtlas;
    private Sprite grass_t, forest_t, snow_t, desert_t;

    //Map parameters
    private int tileWidthPx, tileHeightPx, mapSize, screenHeightPx, screenWidthPx,
            renderStartXTiles, renderEndXTiles, renderStartYTiles, renderEndYTiles;
    private int mapVisibilityWidth, mapVisibilityHeight;

    public MapGdx(){
        create();
    }

    public static MapGdx Instance(){
        if(instance == null){
            instance = new MapGdx();
        }
        return instance;
    }

    public void create() {
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);
        loadTextures();

        MapFactory mf = new MapFactory().MapSize(100).NoOfForests(8).SizeOfForests(8);
        worldMap = mf.Generate();
        mapSize = worldMap.GetSize();
        screenWidthPx = Gdx.graphics.getWidth();
        screenHeightPx = Gdx.graphics.getHeight();
        tileWidthPx =  screenWidthPx / mapSize;
        tileHeightPx = screenHeightPx / mapSize;
        determineVisibleMapArea();
    }

    /*public void render() {
        renderMap();
    }*/

    public void resize(){
        screenWidthPx = Gdx.graphics.getWidth();
        screenHeightPx = Gdx.graphics.getHeight();
        determineVisibleMapArea();
    }

    public void renderWholeMap(){
        renderPartialMap(0, mapSize, 0, mapSize);
    }

    /**
     * Render a partial worldMap around certain position
     * worldMap size determined by mapVisibilityWidth and mapVisibilityHeight
     * @param pos
     */
    public void renderAroundPos(Position pos){
        //Check that the rendering doesn't go past worldMap limits
        renderStartXTiles = pos.X() - mapVisibilityWidth / 2;
        renderEndXTiles = pos.X() + mapVisibilityWidth / 2;
        renderStartYTiles = pos.Y() - mapVisibilityHeight / 2;
        renderEndYTiles = pos.Y() + mapVisibilityHeight / 2;
        checkRenderingBoundaries();

        renderPartialMap(renderStartXTiles, renderEndXTiles, renderStartYTiles, renderEndYTiles);
    }



    /**
     * Translate a Position object to screen pixel coordinates
     * @param pos position in worldMap coordinates
     * @return position as pixels
     */
    public Position PositionToScreenCoordinates(Position pos){
        Position origInPx = new Position(pos.X()*tileWidthPx, pos.Y()*tileHeightPx);
        int offsetX = renderStartXTiles * tileWidthPx;
        int offsetY = renderStartYTiles * tileHeightPx;
        Position screen = new Position(origInPx.X() - offsetX, origInPx.Y()-offsetY);
        return screen;
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

    public GameMap GetWorldMap(){
        return worldMap;
    }

    private void renderPartialMap(int xMin, int xMax, int yMin, int yMax){

        MainGame.SpriteBatchInstance().begin();

        //shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for(int i = 0; i <= (xMax - xMin); i++){
            for(int j = 0; j < (yMax - yMin); j++){
                Tile t = worldMap.GetTile(i+xMin,j+yMin);
                switch(t){
                    case TILE_DESERT:
                        MainGame.SpriteBatchInstance().draw(desert_t, i * tileWidthPx, j * tileHeightPx, tileWidthPx, tileHeightPx);
                        break;
                    case TILE_FOREST:
                        MainGame.SpriteBatchInstance().draw(forest_t, i * tileWidthPx, j * tileHeightPx, tileWidthPx, tileHeightPx);
                        break;
                    case TILE_GRASS:
                        MainGame.SpriteBatchInstance().draw(grass_t, i * tileWidthPx, j * tileHeightPx, tileWidthPx, tileHeightPx);
                        break;
                    case TILE_SNOW:
                        MainGame.SpriteBatchInstance().draw(snow_t, i * tileWidthPx, j * tileHeightPx, tileWidthPx, tileHeightPx);
                        break;
                    default:
                        //shapeRenderer.setColor(Color.WHITE);
                        break;
                }
            }
        }
        //shapeRenderer.end();
        MainGame.SpriteBatchInstance().end();

    }

    public Tile TileAtPos(Position pos){
        return worldMap.GetTile(pos.X(), pos.Y());
    }

    private void loadTextures(){
        mSkin = new Skin();
        mTextureAtlas = new TextureAtlas(Gdx.files.internal("maptextures.atlas"));
        grass_t = mTextureAtlas.createSprite("grass64");
        forest_t = mTextureAtlas.createSprite("forest64");
        snow_t = mTextureAtlas.createSprite("snow64");
        desert_t = mTextureAtlas.createSprite("desert64");
    }

    /**
     * Check that the rendered partial worldMap does not
     * go past the worldMap limits
     */
    private void checkRenderingBoundaries(){
        if(renderStartXTiles < 0){
            renderStartXTiles = 0;
            renderEndXTiles = mapVisibilityWidth;
        }else if(renderEndXTiles > mapSize){
            renderStartXTiles = mapSize - mapVisibilityWidth;
            renderEndXTiles = mapSize;
        }

        if(renderStartYTiles < 0){
            renderStartYTiles = 0;
            renderEndYTiles = mapVisibilityHeight;
        }else if(renderEndYTiles > mapSize){
            renderStartYTiles = mapSize - mapVisibilityHeight;
            renderEndYTiles = mapSize;
        }
    }

    /**
     * Determine the zoom level of the worldMap according to the screen size
     * TODO: Currently very hackish
     */
    private void determineVisibleMapArea(){
        int visibilityw = 8, visibilityh = 8;
        while(screenWidthPx % visibilityw != 0){
            visibilityw++;
        }
        while(screenHeightPx % visibilityh != 0){
            visibilityh++;
        }

        mapVisibilityWidth = visibilityw;
        mapVisibilityHeight = visibilityh;
        tileWidthPx = screenWidthPx / mapVisibilityWidth;
        tileHeightPx = screenHeightPx / mapVisibilityHeight;
        System.out.println(visibilityw + "," + visibilityh);
        System.out.println("Screen width: " + Gdx.graphics.getWidth() + ", tilePx: " + tileWidthPx);
        System.out.println("Screen height: " + Gdx.graphics.getHeight() + ", tilePx: " + tileHeightPx);
    }
}
