package com.miolfo.gamelogic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

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
        setInitialInventory();
    }

    public Inventory GetInventory(){
        return mInventory;
    }

    /**
     * Set the initial items of the player
     */
    private void setInitialInventory(){
        //For testing purposes, create item instances here
        //TODO: ItemManager class
        Item sword = new Item("Iron Sword", "A simple sword", new Sprite(new Texture(Gdx.files.internal("items/sword.png"))));
        mInventory.AddItem(sword);
        mInventory.AddItem(sword);
    }

}
