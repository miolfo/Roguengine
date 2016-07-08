package com.miolfo.roguengine.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
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
    private ImageButton.ImageButtonStyle mArrowStyle, mAttackStyle1, mAttackStyle2;
    private Skin mSkin;
    private TextureAtlas mButtonAtlas;

    public MainGameButtons(){
        createButtons();
    }

    public void render(){
        mStage.draw();
    }


    private InputListener inventoryInputListener = new InputListener(){
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
            return true;
        }
        public void touchUp(InputEvent event, float x, float y, int pointer, int button){
            InventoryUi.toggleVisibility();
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

        mArrowStyle = new ImageButton.ImageButtonStyle();
        mArrowStyle.up = mSkin.getDrawable("arrow");
        mArrowStyle.down = mSkin.getDrawable("arrow2");

        mArrowDown = new ImageButton(mArrowStyle.up, mArrowStyle.down);
        mArrowDown.setSize(BUTTON_SIZE, BUTTON_SIZE);
        mArrowDown.setPosition(Gdx.graphics.getWidth() - BUTTON_SIZE * 2,0);
        mArrowUp = new ImageButton(mArrowStyle.up, mArrowStyle.down);
        mArrowUp.setSize(BUTTON_SIZE, BUTTON_SIZE);
        mArrowUp.getImage().setRotation(180);
        mArrowUp.setPosition(Gdx.graphics.getWidth() - BUTTON_SIZE, BUTTON_SIZE * 3f);
        mArrowRight = new ImageButton(mArrowStyle.up, mArrowStyle.down);
        mArrowRight.setSize(BUTTON_SIZE, BUTTON_SIZE);
        mArrowRight.getImage().setRotation(90);
        mArrowRight.setPosition(Gdx.graphics.getWidth(), BUTTON_SIZE);
        mArrowLeft = new ImageButton(mArrowStyle.up, mArrowStyle.down);
        mArrowLeft.setSize(BUTTON_SIZE, BUTTON_SIZE);
        mArrowLeft.getImage().setRotation(270);
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

        mInventory = new ImageButton(new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("graphics/inventory32.png")))));
        //mInventory.setScale(10,10);
        mInventory.setSize(BUTTON_SIZE,BUTTON_SIZE);
        mInventory.setPosition(Gdx.graphics.getWidth() - BUTTON_SIZE * 1.5f, Gdx.graphics.getHeight() - BUTTON_SIZE * 2f);
        mInventory.getImage().setFillParent(true);

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
