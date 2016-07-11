package com.miolfo.gamelogic;

/**
 * Created by Mikko Forsman on 6/13/16.
 */
public class Player extends Character {
    private Inventory mInventory;
    private static Player mPlayerInstance;

    public static Player GetInstance(){
        if(mPlayerInstance == null) mPlayerInstance = new Player();
        return mPlayerInstance;
    }

    public Player(){
        super("Player");
        mInventory = new Inventory(25); //Initialize new inventory, 25 capacity during testing
        mPlayerInstance = this;
    }

    public Inventory GetInventory(){
        return mInventory;
    }

}
