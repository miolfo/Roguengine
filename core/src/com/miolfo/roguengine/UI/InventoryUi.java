package com.miolfo.roguengine.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.miolfo.roguengine.MainGame;

/**
 * Created by Mikko Forsman on 7/8/16.
 * Class for rendering the inventory of the player
 */
public class InventoryUi {

    private final float INVENTORY_SIZE_FACTOR = 0.7f;
    private final int INVENTORY_WIDTH_SLOTS = 5;    //Temp variable until actual inventory implementation
    private final int INVENTORY_HEIGHT_SLOTS = 3;   //Temp variable until actual inventory implementation

    private float mInvWidth, mInvHeight, mSlotSize;

    private static boolean mVisible;
    private Stage mInventoryStage;
    private Sprite mInventoryBase;
    private Sprite mInventorySlot;

    public InventoryUi(){
        mInventoryStage = new Stage();
        mVisible = false;
        mInventoryBase = new Sprite(new Texture(Gdx.files.internal("graphics/inventory_base2.png")));
        mInventorySlot = new Sprite(new Texture(Gdx.files.internal("graphics/empty_slot.png")));
        mInvWidth = Gdx.graphics.getWidth() * INVENTORY_SIZE_FACTOR;
        mInvHeight = Gdx.graphics.getHeight() * INVENTORY_SIZE_FACTOR;
        mSlotSize = mInvWidth / 10;
    }

    public void render(){
        MainGame.SpriteBatchInstance().begin();
        MainGame.SpriteBatchInstance().draw(mInventoryBase, (Gdx.graphics.getWidth() - mInvWidth) / 2,
                    (Gdx.graphics.getHeight() - mInvHeight) /2,
                    mInvWidth, mInvHeight);
        renderEmptySlots();
        MainGame.SpriteBatchInstance().end();
    }


    public static void toggleVisibility(){mVisible = !mVisible;}

    public static boolean isVisible(){
        return mVisible;
    }

    /**
     * Render empty inventory slots
     */
    private void renderEmptySlots(){
        for(int i = 0; i < INVENTORY_WIDTH_SLOTS; i++){
            //for(int j = 0; j < INVENTORY_HEIGHT_SLOTS; i++){
            //    MainGame.SpriteBatchInstance().draw(mInventorySlot, );
            //}
            MainGame.SpriteBatchInstance().draw(mInventorySlot,
                    ((Gdx.graphics.getWidth() - mInvWidth) / 2) + (i*mSlotSize) + 70,
                    (Gdx.graphics.getHeight() - mInvHeight) /2 + 40,
                    mSlotSize,
                    mSlotSize
                    );
        }
    }
}
