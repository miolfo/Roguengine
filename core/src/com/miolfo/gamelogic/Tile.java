package com.miolfo.gamelogic;

import java.util.ArrayList;

/**
 * Created by Mikko Forsman on 30.5.2016.
 */
public class Tile {

    private boolean mContainsCharacter = false;
    private Character mContainedCharacter;  //Whether or not there is a character in the current tile

    private TileType mType;                 //Type of this tile

    public Tile(TileType type){
        mType = type;
    }

    public TileType GetType(){
        return mType;
    }

    public void AddCharacter(Character character){
        mContainedCharacter = character;
        mContainsCharacter = true;
    }

    public void RemoveCharacter(){
        mContainsCharacter = false;
    }

    public Character GetContainedCharacter(){
        if (HasCharacter()) return mContainedCharacter;
        else throw new NullPointerException();          //Use HasCharacter to check before calling GetContainedCharacter
    }

    public boolean HasCharacter(){
        return mContainsCharacter;
    }

    public enum TileType {
        TILE_GRASS, TILE_FOREST, TILE_MOUNTAIN, TILE_WATER, TILE_SNOW, TILE_DESERT, TILE_UNDEFINED;

        @Override
        public String toString() {
            switch (this) {
                case TILE_GRASS:
                    return "Grass";
                case TILE_FOREST:
                    return "Forest";
                case TILE_MOUNTAIN:
                    return "Mountain";
                case TILE_WATER:
                    return "Water";
                case TILE_SNOW:
                    return "Snow";
                case TILE_DESERT:
                    return "Desert";
                case TILE_UNDEFINED:
                    return "N/A";
                default:
                    throw new IllegalArgumentException();
            }
        }
    }
}


