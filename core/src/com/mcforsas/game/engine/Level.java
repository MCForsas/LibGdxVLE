package com.mcforsas.game.engine;

import java.util.Vector;

/*
 * Created by MCForsas on 3/16/2019
 * Level object, has entities.
 */
public abstract class Level {

    private Vector<Entitie> entities = new Vector<Entitie>();
    private boolean isStarted = false;

    public void update(float deltaTime){
        for(Entitie e : entities){
            e.update(deltaTime);
        }
    }

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

    public boolean isStarted() {
        return isStarted;
    }
}
