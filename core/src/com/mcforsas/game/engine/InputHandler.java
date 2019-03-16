package com.mcforsas.game.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import java.util.Vector;

/*
 * Created by MCForsas on 3/16/2019
 * Handles input. When key is pressed, calls input listeners
 */
public class InputHandler implements InputProcessor {
    private Vector<InputListener> listeners = new Vector<InputListener>(); //Listeners

    public void addInputListener(InputListener listener){
        listeners.add(listener);
    }

    public boolean isKeyDown(final int keycode){
        return Gdx.input.isKeyPressed(keycode);
    }

    @Override
    public boolean keyDown(int keycode) {
        for(InputListener listener : listeners){
            listener.keyDown(keycode);
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        for(InputListener listener : listeners){
            listener.keyUp(keycode);
        }
        return true;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        for(InputListener listener : listeners){
            listener.touchDown(screenX, screenY);
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        for(InputListener listener : listeners){
            listener.touchUp(screenX, screenY);
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        for(InputListener listener : listeners){
            listener.mouseMoved(screenX, screenY);
        }
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}