package com.mcforsas.game.engine;

/*
 * Created by MCForsas on 3/16/2019
 * Game object. Can be used with game logic and so on.
 */
public abstract class Entitie extends Renderable{
    protected Level level;

    public void start(){
        super.start();
    }

    /*
     * Update event used for game logic
     */
    public void update(float deltaTime){
        sprite.setPosition(x,y);
    }

    public void end(){

    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }
}
