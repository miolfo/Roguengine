package com.miolfo.gamelogic;
/**
 * Created by Mikko Forsman on 30.5.2016.
 */
public enum Tile {
    TILE_GRASS, TILE_FOREST, TILE_MOUNTAIN, TILE_WATER, TILE_SNOW, TILE_DESERT;

    @Override
    public String toString() {
        switch(this){
            case TILE_GRASS: return "Grass";
            case TILE_FOREST: return "Forest";
            case TILE_MOUNTAIN: return "Mountain";
            case TILE_WATER: return "Water";
            case TILE_SNOW: return "Snow";
            case TILE_DESERT: return "Desert";
            default: throw new IllegalArgumentException();
        }
    }
}
