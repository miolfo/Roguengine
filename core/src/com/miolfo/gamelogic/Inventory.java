package com.miolfo.gamelogic;

import java.util.ArrayList;

/**
 * Created by Mikko Forsman on 7/11/16.
 */
public class Inventory {
    private ArrayList<Item> mItems;

    private int mMaxInventorySize;

    public Inventory(int maxSize){
        mMaxInventorySize = maxSize;
        mItems = new ArrayList<Item>(mMaxInventorySize);
    }

    public void AddItem(Item item){
        //TODO: Check that the inventory capacity isn't exceeded
        mItems.add(item);
    }

    public ArrayList<Item> GetItems(){
        return (ArrayList<Item>) mItems.clone();
    }
}
