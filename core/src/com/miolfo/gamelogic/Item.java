package com.miolfo.gamelogic;

import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by Mikko Forsman on 6.7.2016.
 */
public class Item {
    private String mName = "";
    private String mDescription = "";
    private Sprite mSprite;

    public Item(String name, String description, Sprite sprite){
        this.mName = name;
        this.mDescription = description;
        this.mSprite = sprite;
    }

    public String GetName(){
        return mName;
    }

    public String GetDescription(){
        return mDescription;
    }

    public Sprite GetSprite(){
        return mSprite;
    }
}
