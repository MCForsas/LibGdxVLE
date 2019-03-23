package com.mcforsas.game.engine;

/*
 * Created by MCForsas on 3/16/2019
 * Game object. Can be used with game logic and so on.
 */
public abstract class GameObject extends Renderable{
    protected Level level;

    public void start(){
        super.start();
    }

    public void update(float deltaTime){
        super.update();
        sprite.setPosition(x,y);
    }

    public void dispose(){
    }

    public void end(){
        super.end();
        level.removeGameObject(this);
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }
}
