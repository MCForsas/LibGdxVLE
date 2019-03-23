package com.mcforsas.game.engine;

/*
 * Created by MCForsas on 3/22/2019
 * Replace this text by description, of what this code is for please,
 * you will know nothing about this code after you close the ide.
 */
public abstract class Entitie {
    public void start(){

    }

    public void update(){

    }

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
