package com.miolfo.roguengine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.miolfo.gamelogic.Position;

/**
 * Created by Mikko Forsman on 16.6.2016.
 * Class to handle the basic GUI during the game
 */
public class BasicUi {

    private int ARROW_SIZE = 128;
    private final String UP_ARROW_NAME = "ArrowUp";
    private final String DOWN_ARROW_NAME = "ArrowDown";
    private final String LEFT_ARROW_NAME = "ArrowLeft";
    private final String RIGHT_ARROW_NAME = "ArrowRight";

    private static Stage mStage;
    private ImageButton mArrowDown, mArrowUp, mArrowLeft, mArrowRight;
    private Button.ButtonStyle mArrowStyle;
    private Skin mSkin;
    private TextureAtlas mButtonAtlas;

    public void create() {
        createArrows();
    }

    public void render() {
        mStage.draw();
    }

    private void createArrows(){
        mStage = new Stage();
        Gdx.input.setInputProcessor(mStage);
        mSkin = new Skin();
        mButtonAtlas = new TextureAtlas(Gdx.files.internal("arrow.atlas"));
        mSkin.addRegions(mButtonAtlas);
        ARROW_SIZE = Gdx.graphics.getWidth() / 10;

        mArrowStyle = new Button.ButtonStyle();
        mArrowStyle.up = mSkin.getDrawable("arrow");
        mArrowStyle.down = mSkin.getDrawable("arrow2");

        mArrowDown = new ImageButton(mArrowStyle.up, mArrowStyle.down);
        mArrowDown.setSize(ARROW_SIZE, ARROW_SIZE);
        mArrowDown.setPosition(Gdx.graphics.getWidth() - ARROW_SIZE * 2,0);
        mArrowUp = new ImageButton(mArrowStyle.up, mArrowStyle.down);
        mArrowUp.setSize(ARROW_SIZE, ARROW_SIZE);
        mArrowUp.getImage().setRotation(180);
        mArrowUp.setPosition(Gdx.graphics.getWidth() - ARROW_SIZE, ARROW_SIZE * 2);
        mArrowRight = new ImageButton(mArrowStyle.up, mArrowStyle.down);
        mArrowRight.setSize(ARROW_SIZE, ARROW_SIZE);
        mArrowRight.getImage().setRotation(90);
        mArrowRight.setPosition(Gdx.graphics.getWidth(),ARROW_SIZE / 2);
        mArrowLeft = new ImageButton(mArrowStyle.up, mArrowStyle.down);
        mArrowLeft.setSize(ARROW_SIZE, ARROW_SIZE);
        mArrowLeft.getImage().setRotation(270);
        mArrowLeft.setPosition(Gdx.graphics.getWidth() - ARROW_SIZE * 3, ARROW_SIZE + ARROW_SIZE / 2);

        mArrowDown.setName(DOWN_ARROW_NAME);
        mArrowUp.setName(UP_ARROW_NAME);
        mArrowLeft.setName(LEFT_ARROW_NAME);
        mArrowRight.setName(RIGHT_ARROW_NAME);

        mArrowDown.addListener(moveInputListener);
        mArrowUp.addListener(moveInputListener);
        mArrowRight.addListener(moveInputListener);
        mArrowLeft.addListener(moveInputListener);


        mStage.addActor(mArrowDown);
        mStage.addActor(mArrowUp);
        mStage.addActor(mArrowLeft);
        mStage.addActor(mArrowRight);
    }

    private InputListener moveInputListener = new InputListener(){
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){

            return true;
        }

        public void touchUp(InputEvent event, float x, float y, int pointer, int button){
            String buttonName = event.getListenerActor().getName();
            if(buttonName.equals(DOWN_ARROW_NAME)){
                MainGame.GetPlayer().Move(Position.MoveDirection.SOUTH);
            } else if(buttonName.equals(UP_ARROW_NAME)) {
                MainGame.GetPlayer().Move(Position.MoveDirection.NORTH);
            } else if(buttonName.equals(LEFT_ARROW_NAME)){
                MainGame.GetPlayer().Move(Position.MoveDirection.WEST);
            } else if(buttonName.equals(RIGHT_ARROW_NAME)){
                MainGame.GetPlayer().Move(Position.MoveDirection.EAST);
            }
        }
    };
}
