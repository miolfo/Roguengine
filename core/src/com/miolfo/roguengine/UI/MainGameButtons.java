package com.miolfo.roguengine.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.miolfo.gamelogic.GameMap;
import com.miolfo.gamelogic.Position;
import com.miolfo.gamelogic.Tile;
import com.miolfo.roguengine.MainGame;

/**
 * Created by Mikko Forsman on 6.7.2016.
 */
public class MainGameButtons {
    private int BUTTON_SIZE = 140;
    private final String UP_ARROW_NAME = "ArrowUp";
    private final String DOWN_ARROW_NAME = "ArrowDown";
    private final String LEFT_ARROW_NAME = "ArrowLeft";
    private final String RIGHT_ARROW_NAME = "ArrowRight";

    private static boolean mAttacking = false;
    private boolean mInventoryStateChanged = false;

    private static Stage mStage;
    private ImageButton mArrowDown, mArrowUp, mArrowLeft, mArrowRight, mAttack, mInventory;
    private ImageButton.ImageButtonStyle mAttackStyle1, mAttackStyle2;
    private Skin mSkin;
    private TextureAtlas mButtonAtlas;

    public MainGameButtons(){
        createButtons();
    }

    public void render(){
        mStage.draw();
    }

    private void toggleButtonDisabled(Touchable t){
        mArrowUp.setTouchable(t);
        mArrowLeft.setTouchable(t);
        mArrowRight.setTouchable(t);
        mArrowDown.setTouchable(t);
        mAttack.setTouchable(t);
    }


    private InputListener inventoryInputListener = new InputListener(){
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
            return true;
        }
        public void touchUp(InputEvent event, float x, float y, int pointer, int button){
            InventoryUi.toggleVisibility();
            if(InventoryUi.isVisible()){
                toggleButtonDisabled(Touchable.disabled);
            } else{
                toggleButtonDisabled(Touchable.enabled);
            }
        }
    };

    private InputListener moveInputListener = new InputListener(){
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
            return true;
        }

        public void touchUp(InputEvent event, float x, float y, int pointer, int button){
            //Disable functionality if inventory is visible
            //TODO: A smarter solution probably exists...
            if(!InventoryUi.isVisible()) {
                String buttonName = event.getListenerActor().getName();
                Position oldPlayerPos = MainGame.GetPlayer().GetPosition().Clone();
                Position.MoveDirection moveDirection;
                if (buttonName.equals(DOWN_ARROW_NAME)) {
                    MainGame.GetPlayer().Move(Position.MoveDirection.SOUTH);
                    moveDirection = Position.MoveDirection.SOUTH;
                } else if (buttonName.equals(UP_ARROW_NAME)) {
                    MainGame.GetPlayer().Move(Position.MoveDirection.NORTH);
                    moveDirection = Position.MoveDirection.NORTH;
                } else if (buttonName.equals(LEFT_ARROW_NAME)) {
                    MainGame.GetPlayer().Move(Position.MoveDirection.WEST);
                    moveDirection = Position.MoveDirection.WEST;
                } else {
                    MainGame.GetPlayer().Move(Position.MoveDirection.EAST);
                    moveDirection = Position.MoveDirection.EAST;
                }
                //If the move was invalid, return back to original position
                if (!isMoveValid(MainGame.GetPlayer().GetPosition(), MainGame.GetCurrentMap())) {
                    MainGame.GetPlayer().Move(oldPlayerPos);
                    GameConsole.WriteLine("Can't move outside of map!");
                } else {
                    GameConsole.WriteLine("Moved " + moveDirection.toString() + " to " + MainGame.GetPlayer().GetPosition());
                }
            }
        }
    };

    private InputListener attackInputListener = new InputListener(){
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){

            return true;
        }

        public void touchUp(InputEvent event, float x, float y, int pointer, int button){
            mAttacking = !mAttacking;

            if(mAttacking){
                //When attacking, change the button style and write to console
                mAttack.setStyle(mAttackStyle2);
                GameConsole.WriteLine("Select a target to attack, or click on empty tile to cancel");
            }
            else{
                mAttack.setStyle(mAttackStyle1);
            }
        }
    };

    private boolean isMoveValid(Position newPos, GameMap usedMap){
        return usedMap.GetTile(newPos.X(), newPos.Y()) != Tile.TILE_UNDEFINED;
    }

    private void createButtons(){
        mStage = new Stage();
        Gdx.input.setInputProcessor(mStage);
        mSkin = new Skin();
        mButtonAtlas = new TextureAtlas(Gdx.files.internal("graphics/mainbuttons.atlas"));
        mSkin.addRegions(mButtonAtlas);

        Sprite arrowUpSprite = mSkin.getSprite("arrow");
        arrowUpSprite.setSize(BUTTON_SIZE, BUTTON_SIZE);
        Sprite arrowDownSprite = mSkin.getSprite("arrow2");
        arrowDownSprite.setSize(BUTTON_SIZE, BUTTON_SIZE);

        mArrowDown = new ImageButton(new SpriteDrawable(arrowUpSprite), new SpriteDrawable(arrowDownSprite));
        mArrowDown.setPosition(Gdx.graphics.getWidth() - BUTTON_SIZE * 2,0);

        mArrowUp = new ImageButton(new SpriteDrawable(arrowUpSprite), new SpriteDrawable(arrowDownSprite));
        mArrowUp.getImage().setRotation(180);
        mArrowUp.rotateBy(180);
        mArrowUp.setPosition(Gdx.graphics.getWidth() - BUTTON_SIZE, BUTTON_SIZE * 3f);

        mArrowRight = new ImageButton(new SpriteDrawable(arrowUpSprite), new SpriteDrawable(arrowDownSprite));
        mArrowRight.getImage().setRotation(90);
        mArrowRight.setPosition(Gdx.graphics.getWidth(), BUTTON_SIZE);

        mArrowLeft = new ImageButton(new SpriteDrawable(arrowUpSprite), new SpriteDrawable(arrowDownSprite));
        mArrowLeft.getImage().rotateBy(270);
        mArrowLeft.rotateBy(270);
        mArrowLeft.setPosition(Gdx.graphics.getWidth() - BUTTON_SIZE * 3, BUTTON_SIZE + BUTTON_SIZE);

        mArrowDown.setName(DOWN_ARROW_NAME);
        mArrowUp.setName(UP_ARROW_NAME);
        mArrowLeft.setName(LEFT_ARROW_NAME);
        mArrowRight.setName(RIGHT_ARROW_NAME);


        mAttackStyle1 = new ImageButton.ImageButtonStyle();
        mAttackStyle1.up = mSkin.getDrawable("attack2");
        mAttackStyle2 = new ImageButton.ImageButtonStyle();
        mAttackStyle2.up = mSkin.getDrawable("attack");
        mAttack = new ImageButton(mAttackStyle1.up);
        mAttack.setSize(BUTTON_SIZE, BUTTON_SIZE);
        mAttack.setPosition(Gdx.graphics.getWidth() - BUTTON_SIZE * 2, BUTTON_SIZE);

        Sprite invSprite = new Sprite(new Texture(Gdx.files.internal("graphics/inventory32.png")));
        invSprite.setSize(BUTTON_SIZE, BUTTON_SIZE);
        mInventory = new ImageButton(new SpriteDrawable(invSprite));
        mInventory.setPosition(Gdx.graphics.getWidth() - BUTTON_SIZE, Gdx.graphics.getHeight() - BUTTON_SIZE * 2f);

        mArrowDown.addListener(moveInputListener);
        mArrowUp.addListener(moveInputListener);
        mArrowRight.addListener(moveInputListener);
        mArrowLeft.addListener(moveInputListener);
        mAttack.addListener(attackInputListener);
        mInventory.addListener(inventoryInputListener);

        mStage.addActor(mArrowDown);
        mStage.addActor(mArrowUp);
        mStage.addActor(mArrowLeft);
        mStage.addActor(mArrowRight);
        mStage.addActor(mAttack);
        mStage.addActor(mInventory);
    }
}
