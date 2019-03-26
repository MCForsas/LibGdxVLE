package com.mcforsas.game.gameObjects;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mcforsas.game.engine.Engine;
import com.mcforsas.game.engine.GameObject;
import com.mcforsas.game.engine.InputListener;
import com.mcforsas.game.engine.Level;
import com.mcforsas.game.engine.handlers.AssetHandler;

/**
 * @author MCForsas @since 3/16/2019
 * example Game object
 */
public class GameObjectExample extends GameObject implements InputListener {

    public GameObjectExample(float x, float depth, Level level){
        sprite = new Sprite(AssetHandler.getTexture("sprBadlogic"));
        this.x = x;
        y = x;
        sprite.setPosition(x,y);
        sprite.setBounds(x,y,level.getHeigth()/4,level.getHeigth()/4);

        setDepth(depth);

        Engine.getInputHandler().addInputListener(this);
    }

    @Override
    public void render(SpriteBatch spriteBatch, float deltaTime) {
        super.render(spriteBatch, deltaTime);
    }

    @Override
    public void touchDown(float x, float y) {
        if(isOnSprite(x,y)) {
            Engine.getAssetHandler().getSound("sndExample").play();
        }

        if(Engine.getInputHandler().isButtonDown(Input.Buttons.RIGHT)){
            this.x = x;
            this.y = y;
        }
    }

    @Override
    public void touchUp(float x, float y) {

    }

    @Override
    public void keyUp(int keycode) {

    }

    @Override
    public void keyDown(int keycode) {

    }

    @Override
    public void mouseMoved(float x, float y) {

    }
}
