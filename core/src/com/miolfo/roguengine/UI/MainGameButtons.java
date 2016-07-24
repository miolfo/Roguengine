package com.miolfo.roguengine.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
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
    private final String UP_ARROW_NAME = "Up";
    private final String DOWN_ARROW_NAME = "Down";
    private final String LEFT_ARROW_NAME = "Left";
    private final String RIGHT_ARROW_NAME = "Right";
    private final float BUTTON_DOWN_SIZE = 0.9f;    //Size of the button sprite when pressed

    private static boolean mAttacking = false;

    private static Stage mStage;
    private ImageButton mArrowDown, mArrowUp, mArrowLeft, mArrowRight, mAttack, mInventory;
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
            String buttonName = event.getListenerActor().getName();
            //Disable functionality if inventory is visible
            //TODO: A smarter solution probably exists for blocking movement buttons functionality...
            if(!InventoryUi.isVisible() && !mAttacking) {
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
                //TODO: Move the Character class, and printing to Player class
                if (!isMoveValid(MainGame.GetPlayer().GetPosition(), MainGame.GetCurrentMap())) {
                    MainGame.GetPlayer().Move(oldPlayerPos);
                    GameConsole.WriteLine("Can't move outside of map!");
                } else {
                    GameConsole.WriteLine("Moved " + moveDirection.toString() + " to " + MainGame.GetPlayer().GetPosition());
                }

            } else if(mAttacking){
                //TODO: Attack functionality
                GameConsole.WriteLine("Attacked " + buttonName);
                mAttacking = false;
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
                GameConsole.WriteLine("Select attack direction, or click again to cancel attacking.....");
            }
            else{
                GameConsole.WriteLine("Canceled attacking");
            }
        }
    };

    private boolean isMoveValid(Position newPos, GameMap usedMap){
        return usedMap.GetTile(newPos.X(), newPos.Y()).GetType() != Tile.TileType.TILE_UNDEFINED;
    }

    private void createButtons(){
        mStage = new Stage();
        Gdx.input.setInputProcessor(mStage);
        mSkin = new Skin();
        mButtonAtlas = new TextureAtlas(Gdx.files.internal("graphics/mainbuttons.atlas"));
        mSkin.addRegions(mButtonAtlas);

        Sprite arrowDownUp = new Sprite(mSkin.getSprite("arrow_down"));
        arrowDownUp.setSize(BUTTON_SIZE, BUTTON_SIZE);
        Sprite arrowDownDown = new Sprite(mSkin.getSprite("arrow_down"));
        arrowDownDown.setSize(BUTTON_SIZE * BUTTON_DOWN_SIZE, BUTTON_SIZE * BUTTON_DOWN_SIZE);
        mArrowDown = new ImageButton(new SpriteDrawable(arrowDownUp), new SpriteDrawable(arrowDownDown));
        mArrowDown.setPosition(BUTTON_SIZE, 0);

        Sprite arrowUpUp = new Sprite(mSkin.getSprite("arrow_up"));
        arrowUpUp.setSize(BUTTON_SIZE, BUTTON_SIZE);
        Sprite arrowUpDown = new Sprite(mSkin.getSprite("arrow_up"));
        arrowUpDown.setSize(BUTTON_SIZE * BUTTON_DOWN_SIZE, BUTTON_SIZE * BUTTON_DOWN_SIZE);
        mArrowUp = new ImageButton(new SpriteDrawable(arrowUpUp), new SpriteDrawable(arrowUpDown));
        mArrowUp.setPosition(BUTTON_SIZE, BUTTON_SIZE * 2);

        Sprite arrowRightUp = new Sprite(mSkin.getSprite("arrow_right"));
        arrowRightUp.setSize(BUTTON_SIZE, BUTTON_SIZE);
        Sprite arrowRightDown = new Sprite(mSkin.getSprite("arrow_right"));
        arrowRightDown.setSize(BUTTON_SIZE * BUTTON_DOWN_SIZE, BUTTON_SIZE * BUTTON_DOWN_SIZE);
        mArrowRight = new ImageButton(new SpriteDrawable(arrowRightUp), new SpriteDrawable(arrowRightDown));
        mArrowRight.setPosition(BUTTON_SIZE * 2, BUTTON_SIZE);

        Sprite arrowLeftUp = new Sprite(mSkin.getSprite("arrow_left"));
        arrowLeftUp.setSize(BUTTON_SIZE, BUTTON_SIZE);
        Sprite arrowLeftDown = new Sprite(mSkin.getSprite("arrow_left"));
        arrowLeftDown.setSize(BUTTON_SIZE * BUTTON_DOWN_SIZE, BUTTON_SIZE * BUTTON_DOWN_SIZE);
        mArrowLeft = new ImageButton(new SpriteDrawable(arrowLeftUp), new SpriteDrawable(arrowLeftDown));
        mArrowLeft.setPosition(0, BUTTON_SIZE);

        mArrowDown.setName(DOWN_ARROW_NAME);
        mArrowUp.setName(UP_ARROW_NAME);
        mArrowLeft.setName(LEFT_ARROW_NAME);
        mArrowRight.setName(RIGHT_ARROW_NAME);

        Sprite attackUp = new Sprite(mSkin.getSprite("attack"));
        Sprite attackDown = new Sprite(mSkin.getSprite("attack"));
        attackUp.setSize(BUTTON_SIZE, BUTTON_SIZE);
        attackDown.setSize(BUTTON_SIZE * BUTTON_DOWN_SIZE, BUTTON_SIZE * BUTTON_DOWN_SIZE);
        mAttack = new ImageButton(new SpriteDrawable(attackUp), new SpriteDrawable(attackDown));
        mAttack.setSize(BUTTON_SIZE, BUTTON_SIZE);
        mAttack.setPosition(BUTTON_SIZE, BUTTON_SIZE);

        Sprite invSprite = new Sprite(new Texture(Gdx.files.internal("graphics/inventory32.png")));
        Sprite invSpriteDown = new Sprite(new Texture(Gdx.files.internal("graphics/inventory32.png")));
        invSprite.setSize(BUTTON_SIZE, BUTTON_SIZE);
        invSpriteDown.setSize(BUTTON_SIZE * BUTTON_DOWN_SIZE, BUTTON_SIZE * BUTTON_DOWN_SIZE);
        mInventory = new ImageButton(new SpriteDrawable(invSprite), new SpriteDrawable(invSpriteDown));
        mInventory.setPosition(Gdx.graphics.getWidth() - BUTTON_SIZE, 0);

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
