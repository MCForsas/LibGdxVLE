package com.mcforsas.game.engine;

/*
 * Created by MCForsas on 3/16/2019
 * Used as listener interface
 */
public interface InputListener {
    void touchDown(final float x, final float y);
    void touchUp(final float x, final float y);
    void keyUp(final int keycode);
    void keyDown(final int keycode);
    void mouseMoved(final float x, final float y);
}
