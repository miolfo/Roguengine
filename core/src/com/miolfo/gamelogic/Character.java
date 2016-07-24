package com.miolfo.gamelogic;

import com.badlogic.gdx.graphics.Texture;
import com.miolfo.roguengine.MainGame;
import com.miolfo.roguengine.MapGdx;

/**
 * Created by Mikko Forsman on 6/13/16.
 */
public abstract class Character {
    private Position mPosition;
    private String mName;
    private Texture mTexture;
    private static MapGdx mMapGdxInstance;
    private int mId;

    public Character(String name){
        this(name, new Position(0,0));
    }

    public Character(String name, Position pos){
        mName = name;
        mPosition = pos;
    }

    public String GetName(){
        return mName;
    }

    public Position GetPosition(){
        return mPosition;
    }

    public void Move(Position.MoveDirection dir){
        mPosition.Move(dir);
    }

    public void Move(Position.MoveDirection dir, int distance){
        mPosition.Move(dir, distance);
    }

    public void Move(Position newPos){
        mPosition = newPos;
    }

    //TODO: Add default texture, and (possibly?) render function
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
