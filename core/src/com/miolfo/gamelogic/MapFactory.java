package com.miolfo.gamelogic;

import java.util.Random;

/**
 * Created by Mikko Forsman on 30.5.2016.
 */
public class MapFactory {

    private GameMap mGameMap;

    private float mSnowCoverage = 0.1f;   //How large radius of the top is tundra-covered
    private float mDesertCoverage = 0.1f;   //How large radius of the bottom is desert-covered
    private float mNumberOfForests = 5;

    private int mMapSize = 20;
    private int mAvgForestSize = 3;

    public MapFactory(){

    }

    /**
     * Set the snow coverage of northern areas
     * @param snowCoverage 0..1
     * @return this factory object for chaining
     */
    public MapFactory SnowCoverage(float snowCoverage){
        assert snowCoverage > 0 && snowCoverage <= 1;
        mSnowCoverage = snowCoverage;
        return this;
    }

    /**
     * Set the desert coverage of southern areas
     * @param desertCoverage 0..1
     * @return this factory object for chaining
     */
    public MapFactory DesertCoverage(float desertCoverage){
        assert desertCoverage > 0 && desertCoverage <= 1;
        mDesertCoverage = desertCoverage;
        return this;
    }

    /**
     * Set the size of the map
     * @param mapSize
     * @return this factory object for chaining
     */
    public MapFactory MapSize(int mapSize){
        mMapSize = mapSize;
        return this;
    }

    /**
     * Set the number of the forests
     * @param forests
     * @return this factory object for chaining
     */
    public MapFactory NoOfForests(int forests){
        mNumberOfForests = forests;
        return this;
    }

    /**
     * Set the avg size of the forests
     * @param size
     * @return this factory object for chaining
     */
    public MapFactory SizeOfForests(int size){
        mAvgForestSize = size;
        return this;
    }

    public GameMap Generate(){
        mGameMap = new GameMap(mMapSize);
        //Initialize the map with grass base, snow north and desert bottom
        int mapSize = mGameMap.GetSize();
        for(int i = 0; i < mapSize; i++){
            for(int j = 0; j < mapSize; j++){
                mGameMap.SetTile(i,j, new Tile(Tile.TileType.TILE_GRASS));
            }
        }

        GeneratePolarAreas(true, Tile.TileType.TILE_SNOW);
        GeneratePolarAreas(false, Tile.TileType.TILE_DESERT);
        Random r = new Random();
        for(int i = 0; i < mNumberOfForests; i++){
            int forestX = r.nextInt(mMapSize);
            int forestY = r.nextInt(mMapSize);
            //TODO random forest sizes
            GenerateTilePatch(forestX, forestY, mAvgForestSize, 0.5f, Tile.TileType.TILE_FOREST);
        }
        return mGameMap;
    }

    /**
     * Function for generating the northern and southern radiuss of the map
     * @param north north or south radius
     * @param tileType tiles to apply to the radius
     */
    private void GeneratePolarAreas(boolean north, Tile.TileType tileType){
        int mapSize = mGameMap.GetSize();
        int baseLine;
        if(north) baseLine = Math.round(mSnowCoverage * mapSize);
        else baseLine = Math.round(mDesertCoverage * mapSize);
        Random r = new Random();
        for(int i = 0; i < mapSize; i++){
            int randomInt = r.nextInt(baseLine);    //Random integer to create a ragged edge
            if(north){
                for(int j = 0; j < baseLine + randomInt; j++){
                    mGameMap.SetTile(i,j, new Tile(Tile.TileType.TILE_SNOW));
                }
            }
            else {
                for (int j = mapSize - baseLine - randomInt; j < mapSize; j++) {
                    mGameMap.SetTile(i, j, new Tile(tileType));
                }
            }
        }
    }

    /**
     * Generate an radius filled with tiles
     * @param x x coordinate of radius center
     * @param y y coordinate of radius center
     * @param radius radius of area
     * @param randomness how smooth is the area, 0..1
     * @param tileType tiles used to fill the radius
     */
    private void GenerateTilePatch(int x, int y, int radius, float randomness, Tile.TileType tileType){
        Random r = new Random();
        int rrx = 0, rry = 0;
        int rndAsInt = Math.round(radius * randomness);
        if(randomness != 0) {
            rrx = r.nextInt(rndAsInt);
            rry = r.nextInt(rndAsInt);
        }

        for(int i = x - radius - rrx; i <= x + rrx; i++){
            for(int j = y - radius - rry;  j <= y + rry; j++){
                if((i-x) * (i-x) + (j-y)*(j-y) <= radius * radius){
                    int xs = x - (i - x);
                    int ys = y - (j - y);
                    mGameMap.SetTile(i, j, new Tile(tileType));
                    mGameMap.SetTile(i, ys, new Tile(tileType));
                    mGameMap.SetTile(xs, j, new Tile(tileType));
                    mGameMap.SetTile(xs, ys, new Tile(tileType));
                } else if( randomness != 0){
                    float chance = r.nextFloat();
                    if(chance <= randomness){
                        mGameMap.SetTile(i,j, new Tile(tileType));
                    }
                }
            }
        }
    }
}
