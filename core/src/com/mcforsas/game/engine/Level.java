package com.mcforsas.game.engine;

import java.util.Vector;

/*
 * Created by MCForsas on 3/16/2019
 * Level object, holds entities
 */
public abstract class Level extends Renderable{

    private Vector<Entitie> entities = new Vector<Entitie>();
    private boolean isStarted = false;
    private int width = Engine.WORLD_WIDTH, heigth = Engine.WORLD_HEIGHT;

    public void update(float deltaTime){
        for(Entitie e : entities){
            e.update(deltaTime);
        }
    }

    /*
     * Starts all the entities
     */
    public void start(){
        for(Entitie e : entities){
            e.start();
        }

        isStarted = true;
    }

    public void end(){
        for(Entitie e : entities){
            e.end();
        }
    }

    /*
     * Adds entitie and sets it's level to self
     */
    public void addEntitie(Entitie entitie){
        entities.add(entitie);
        entitie.setLevel(this);
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
