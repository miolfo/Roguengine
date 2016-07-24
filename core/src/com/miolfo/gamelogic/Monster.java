package com.miolfo.gamelogic;

import com.badlogic.gdx.Gdx;

/**
 * Created by Mikko Forsman on 7/15/16.
 */
public class Monster extends Character implements INpc {

    public Monster(String name, Position pos){
        super(name, pos);
        Gdx.app.log("RoguEngine", "Spawned " + name + " at " + pos);
    }

    @Override
    public void Attack(Position targetPos) {

    }

    @Override
    public void ProcessTurn() {

    }
}
