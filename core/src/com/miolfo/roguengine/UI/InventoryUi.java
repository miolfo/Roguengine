package com.miolfo.roguengine.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.miolfo.gamelogic.Inventory;
import com.miolfo.gamelogic.Player;
import com.miolfo.roguengine.MainGame;

/**
 * Created by Mikko Forsman on 7/8/16.
 * Class for rendering the inventory of the player
 */
public class InventoryUi {

    private final float INVENTORY_SIZE_FACTOR = 0.7f;
    private final int INVENTORY_WIDTH_SLOTS = 5;    //Temp variable until actual inventory implementation
    private final int INVENTORY_HEIGHT_SLOTS = 5;   //Temp variable until actual inventory implementation

    private float mInvWidth, mInvHeight, mSlotSize;

    private static boolean mVisible;
    private Sprite mInventoryBase;
    private Sprite mInventorySlot;

    public InventoryUi(){
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
        renderSlots();
        MainGame.SpriteBatchInstance().end();
    }


    public static void toggleVisibility(){mVisible = !mVisible;}

    public static boolean isVisible(){
        return mVisible;
    }

    /**
     * Render empty inventory slots
     */
    private void renderSlots(){
        int xOff = 40;  //Offset in pixels in x direction
        int yOff = 20; //Offset in pixels in y direction
        int inventoryIndex = 0;
        Inventory playerInv = Player.GetInstance().GetInventory();
        for(int i = 0; i < INVENTORY_HEIGHT_SLOTS; i++){
            for(int j = 0; j < INVENTORY_WIDTH_SLOTS; j++) {
                //}
                MainGame.SpriteBatchInstance().draw(mInventorySlot,
                        ((Gdx.graphics.getWidth() - mInvWidth) / 2) + (j * mSlotSize) + xOff,
                        ((Gdx.graphics.getHeight() - mInvHeight) + mInvHeight / 2) - (i * mSlotSize) + yOff,
                        mSlotSize,
                        mSlotSize
                );

                //If an item for the slot exists, render its sprite to the slot
                if(playerInv.GetItems().size() > inventoryIndex){
                    MainGame.SpriteBatchInstance().draw(playerInv.GetItems().get(inventoryIndex).GetSprite(),
                            ((Gdx.graphics.getWidth() - mInvWidth) / 2) + (j * mSlotSize) + xOff,
                            ((Gdx.graphics.getHeight() - mInvHeight) + mInvHeight / 2) - (i * mSlotSize) + yOff,
                            mSlotSize,
                            mSlotSize);
                }
                inventoryIndex++;
            }
        }
    }
}
