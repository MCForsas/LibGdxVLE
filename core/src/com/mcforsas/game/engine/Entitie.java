package com.mcforsas.game.engine;

/**
 * @author MCForsas @since 3/22/2019
 * Holds main entitie lifecycle methods
 */
public abstract class Entitie {

    /**
     * All assets and values should be set here for this to function correctly.
     */
    public void start(){

    }

    /**
     * Game logic
     */
    public void update(){

    }

    /**
     * End all game logic. Remove all dependencies.
     */
    public void end(){
        if(this instanceof InputListener){
            try {
                Engine.getInputHandler().removeInputListener((InputListener) this);
            }catch (NullPointerException e){
                e.printStackTrace();
            }
        }
    }
}
