package com.miolfo.roguengine.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
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

    private static Stage mStage;
    private ImageButton mArrowDown, mArrowUp, mArrowLeft, mArrowRight, mAttack;
    private ImageButton.ImageButtonStyle mArrowStyle, mAttackStyle1, mAttackStyle2;
    private Skin mSkin;
    private TextureAtlas mButtonAtlas;

    public MainGameButtons(){
        createButtons();
    }

    public void render(){
        mStage.draw();
    }

    private InputListener moveInputListener = new InputListener(){
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){

            return true;
        }

        public void touchUp(InputEvent event, float x, float y, int pointer, int button){
            String buttonName = event.getListenerActor().getName();
            Position oldPlayerPos = MainGame.GetPlayer().GetPosition().Clone();
            if(buttonName.equals(DOWN_ARROW_NAME)){
                MainGame.GetPlayer().Move(Position.MoveDirection.SOUTH);
                GameConsole.WriteLine("Moved south");
            } else if(buttonName.equals(UP_ARROW_NAME)) {
                MainGame.GetPlayer().Move(Position.MoveDirection.NORTH);
                GameConsole.WriteLine("Moved north");
            } else if(buttonName.equals(LEFT_ARROW_NAME)){
                MainGame.GetPlayer().Move(Position.MoveDirection.WEST);
                GameConsole.WriteLine("Moved west");
            } else if(buttonName.equals(RIGHT_ARROW_NAME)){
                MainGame.GetPlayer().Move(Position.MoveDirection.EAST);
                GameConsole.WriteLine("Moved east");
            }
            //If the move was invalid, return back to original position
            if(!isMoveValid(MainGame.GetPlayer().GetPosition(), MainGame.GetCurrentMap())){
                MainGame.GetPlayer().Move(oldPlayerPos);
                GameConsole.WriteLine("Can't move outside of map!");
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


        mArrowDown.addListener(moveInputListener);
        mArrowUp.addListener(moveInputListener);
        mArrowRight.addListener(moveInputListener);
        mArrowLeft.addListener(moveInputListener);
        mAttack.addListener(attackInputListener);

        mStage.addActor(mArrowDown);
        mStage.addActor(mArrowUp);
        mStage.addActor(mArrowLeft);
        mStage.addActor(mArrowRight);
        mStage.addActor(mAttack);
    }
}
