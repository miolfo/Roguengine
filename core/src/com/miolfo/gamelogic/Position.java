package com.miolfo.gamelogic;

/**
 * Created by forge on 6/13/16.
 */
public class Position {
    private int x;
    private int y;

    public Position(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int X(){
        return x;
    }

    public void X(int x){
        this.x = x;
    }

    public int Y(){
        return y;
    }

    public void Y(int y){
        this.y = y;
    }

    public void Move(MoveDirection dir){
        Move(dir, 1);
    }

    public void Move(MoveDirection dir, int distance){
        switch(dir){
            case EAST:
                x++;
                break;
            case WEST:
                x--;
                break;
            case SOUTH:
                y--;
                break;
            case NORTH:
                y++;
                break;
        }
    }

    public enum MoveDirection{
        NORTH, SOUTH, EAST, WEST
    }
}