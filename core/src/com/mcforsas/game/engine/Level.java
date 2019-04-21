package com.mcforsas.game.engine;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ConcurrentModificationException;
import java.util.LinkedHashSet;
import java.util.Vector;

/**
 * @author by MCForsas @since 3/16/2019
 * Level object, holds gameObjects, can be used to draw backgrounds, etc.
 */
public abstract class Level extends Renderable{

    private Vector<GameObject> gameObjects = new Vector<GameObject>();
    private boolean isStarted = false;
    protected int width = Engine.WORLD_WIDTH, heigth = Engine.WORLD_HEIGHT;


    public void start(){
        super.start();
        for(int i = 0; i < gameObjects.size(); i++){
            gameObjects.get(i).start();
        }
        isStarted = true;
        isRendered = true;
    }

    public void update(float deltaTime){
        for(int i = 0; i < gameObjects.size(); i++){
            if(!gameObjects.get(i).isPaused) {
                gameObjects.get(i).update(deltaTime);
            }
        }
    }

    public void dispose() {
        for(int i = 0; i < gameObjects.size(); i++){
            gameObjects.get(i).dispose();
        }
    }


    public void end(){
        for(int i = 0; i < gameObjects.size(); i++){
            gameObjects.get(i).end();
        }
        gameObjects.clear();
        super.end();
        isStarted = false;
        isRendered = false;
    }


    @Override
    public void render(SpriteBatch spriteBatch, float deltaTime) {
        super.render(spriteBatch, deltaTime);
    }

    /**
     * Adds gameObject and sets it's level to self
     */
    public void addGameObject(GameObject gameObject){
        gameObjects.add(gameObject);
        gameObject.setLevel(this);
        if(isStarted){
            gameObject.start();
        }
    }

    public void removeGameObject(GameObject gameObject) {
        gameObjects.remove(gameObject);
    }

    public boolean isStarted() {
        return isStarted;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeigth() {
        return heigth;
    }

    public void setHeigth(int heigth) {
        this.heigth = heigth;
    }
}
