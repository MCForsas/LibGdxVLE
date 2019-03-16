package com.mcforsas.game.engine;

/*
 * Created by MCForsas on 3/16/2019
 * Game object
 */
public abstract class Entitie extends Renderable{

    public void start(){
        super.start();
    }

    public void update(float deltaTime){
        sprite.setPosition(x,y);
    }

    public void end(){

    }
}
