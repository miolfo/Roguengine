package com.miolfo.roguengine.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.miolfo.roguengine.MainGame;

/**
 * Created by Mikko Forsman on 7/8/16.
 * Class for rendering the inventory of the player
 */
public class InventoryUi {

    private static boolean mVisible;
    private Sprite mInventoryBase;

    public InventoryUi(){
        mVisible = false;
        mInventoryBase = new Sprite(new Texture(Gdx.files.internal("graphics/inventory_base.png")));
    }

    public void render(){
        MainGame.SpriteBatchInstance().begin();
        MainGame.SpriteBatchInstance().draw(mInventoryBase, 50 ,50);
        MainGame.SpriteBatchInstance().end();
    }

    public static void toggleVisibility(){mVisible = !mVisible;}

    public static boolean isVisible(){
        return mVisible;
    }
}
