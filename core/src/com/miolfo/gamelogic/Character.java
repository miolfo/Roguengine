package com.miolfo.gamelogic;

/**
 * Created by Mikko Forsman on 6/13/16.
 */
public abstract class Character {
    private Position mPosition;
    private String mName;
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

    public void Move(Position.MoveDirection dir){
        mPosition.Move(dir, 1);
    }

    public void Move(Position.MoveDirection dir, int distance){
        mPosition.Move(dir, distance);
    }

    public void Move(Position newPos){
        mPosition = newPos;
    }


}
