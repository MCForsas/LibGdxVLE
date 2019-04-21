package com.mcforsas.game.engine;

/**
 * Created by MCForsas on 3/16/2019
 * Game object. Can be used with game logic and so on.
 */
public abstract class GameObject extends Renderable{
    protected Level level;
    protected boolean isPaused = false;

    public void update(float deltaTime){
        super.update();
    }

    public void dispose(){
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }
}
