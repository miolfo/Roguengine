package com.miolfo.gamelogic;
/**
 * Created by Mikko Forsman on 30.5.2016.
 */
public class GameMap {
    private static final int DEFAULT_MAP_SIZE = 20;

    private static Tile[][] map;

    private final int MAP_PADDING_LENGTH = 7;     //How long to make the string versions of tiles

    public GameMap(){
        this(DEFAULT_MAP_SIZE);
    }

    public GameMap(int mapSize){
        this(mapSize, mapSize);
    }

    /*
    Private until width and height can be different
     */
    private GameMap(int mapWidth, int mapHeight){
        map = new Tile[mapWidth][mapHeight];
        //Initialize with all-grass tiles
        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map.length; j++){
                SetTile(i,j, Tile.TILE_GRASS);
            }
        }
    }

    /**
     *
     * @param x x coordinate
     * @param y y coordinate
     * @return tile at x,y
     */
    public Tile GetTile(int x, int y){
        if(x < map.length && y < map.length && x >= 0 && y >= 0) {
            return map[y][x];
        } else return Tile.TILE_UNDEFINED;
    }

    /**
     *
     * @param pos pos
     * @return tile at pos.x, pos.y
     */
    public Tile GetTile(Position pos){
        return GetTile(pos.X(), pos .Y());
    }


    /**
     *
     * @param x x coordinate
     * @param y y coordinate
     * @param t tile to be set at coordinates x,y
     */
    public void SetTile(int x, int y, Tile t)
    {
        if(x < map.length && y < map.length && x >= 0 && y >= 0) {
            map[y][x] = t;
        }
    }

    public void SetTile(Position pos, Tile t){
        SetTile(pos.X(), pos.Y(), t);
    }

    /**
     * Print the map on the command line
     */
    public void PrintMapAscii(){

        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map.length; j++){
                String tile = GetTile(j,i).toString();
                System.out.print(String.format("%1$-" + MAP_PADDING_LENGTH + "s", tile));
            } System.out.println();
        }
    }

    /**
     *
     * @return map size
     */
    public int GetSize(){
        return map.length;
    }
}
