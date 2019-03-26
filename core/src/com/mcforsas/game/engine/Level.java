package com.mcforsas.game.engine;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ConcurrentModificationException;
import java.util.LinkedHashSet;

/**
 * @author by MCForsas @since 3/16/2019
 * Level object, holds gameObjects, can be used to draw backgrounds, etc.
 */
public abstract class Level extends Renderable{

    private LinkedHashSet<GameObject> gameObjects = new LinkedHashSet<GameObject>();
    private boolean isStarted = false;
    private int width = Engine.WORLD_WIDTH, heigth = Engine.WORLD_HEIGHT;


    public void start(){
        super.start();
        for(GameObject e : gameObjects){
            e.start();
        }
        isStarted = true;
    }

    public void update(float deltaTime){
        for(GameObject e : gameObjects){
            e.update(deltaTime);
        }
    }

    public void dispose() {
        for (GameObject e : gameObjects) {
            e.dispose();
        }
    }
    

    public void end(){
        try {
            for(GameObject e : gameObjects){
                e.end();
                removeGameObject(e);
            }
        }catch (ConcurrentModificationException e){
            e.printStackTrace();
        }
        super.end();

        isStarted = false;
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
