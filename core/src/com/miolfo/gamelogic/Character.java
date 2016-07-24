package com.miolfo.gamelogic;

import com.badlogic.gdx.graphics.Texture;
import com.miolfo.roguengine.MainGame;
import com.miolfo.roguengine.MapGdx;

/**
 * Created by Mikko Forsman on 6/13/16.
 */
public abstract class Character {
    private Position mPosition = new Position(-1,-1);
    private String mName;
    private Texture mTexture;
    private static MapGdx mMapGdxInstance;
    private int mId;

    public Character(String name){
        this(name, new Position(0,0));
    }

    public Character(String name, Position pos){
        mName = name;
        Move(pos);
    }

    public String GetName(){
        return mName;
    }

    public Position GetPosition(){
        return mPosition;
    }

    public void Move(Position.MoveDirection dir){
        Move(dir, 1);
    }

    /**
     * Move a number of positions towards a direction. Also handle
     * adding and removing characters contained in a Tile
     * @param dir Direction of movement
     * @param distance Distance of movement
     */
    public void Move(Position.MoveDirection dir, int distance){
        Position origPos = new Position(mPosition.X(), mPosition.Y());
        mPosition.Move(dir, distance);
        GameMap currMap = MainGame.GetCurrentMap();

        //If the tile already contains a character, move back to original pos
        if(currMap.GetTile(mPosition).HasCharacter()){
            mPosition = origPos;
            currMap.GetTile(origPos).AddCharacter(this);
        } else{
            currMap.GetTile(origPos).RemoveCharacter();
            currMap.GetTile(mPosition).AddCharacter(this);
        }
    }

    /**
     * Set Character to the defined position. Also handle adding and removing
     * of characters in a Tile
     * @param newPos New position
     */
    public void Move(Position newPos){
        Position origPos = new Position(mPosition.X(), mPosition.Y());
        GameMap currMap = MainGame.GetCurrentMap();
        if(!currMap.GetTile(newPos).HasCharacter()) {
            mPosition = newPos;
            currMap.GetTile(mPosition).AddCharacter(this);
            currMap.GetTile(origPos).RemoveCharacter();
        }
    }

    //TODO: Add default texture
    public void SetTexture(Texture texture){
        mTexture = texture;
    }

    public Texture GetTexture(){
        return mTexture;
    }

    public void render(){
        mMapGdxInstance = MainGame.GetMapGenerator();
        //TODO: Don't render outside screen boundaries
        MainGame.SpriteBatchInstance().begin();
        Position playerInPx = mMapGdxInstance.PositionToScreenCoordinates(GetPosition());
        MainGame.SpriteBatchInstance().draw(GetTexture(),
                playerInPx.X(),
                playerInPx.Y(),
                mMapGdxInstance.GetTileWidthPixels(),
                mMapGdxInstance.GetTileHeightPixels());
        MainGame.SpriteBatchInstance().end();
    }

    public abstract void Attack(Position targetPos);
}
