package com.miolfo.roguengine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

/**
 * Created by Mikko Forsman on 16.6.2016.
 * Class to handle the basic GUI during the game
 */
public class BasicUi {

    private int ARROW_SIZE = 128;


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

        mStage.addActor(mArrowDown);
        mStage.addActor(mArrowUp);
        mStage.addActor(mArrowLeft);
        mStage.addActor(mArrowRight);
    }
}
