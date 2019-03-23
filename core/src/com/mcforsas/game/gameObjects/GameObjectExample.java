package com.mcforsas.game.gameObjects;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mcforsas.game.engine.*;

/*
 * Created by MCForsas on 3/16/2019
 * example entitie
 */
public class GameObjectExample extends GameObject implements InputListener {

    public GameObjectExample(float x, float depth){
        sprite = new Sprite(AssetHandler.getTexture("sprBadlogic"));
        this.x = x;
        y = Engine.WORLD_HEIGHT/2;
        sprite.setPosition(x,y);
        sprite.setSize(Engine.WORLD_WIDTH/2,y);

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
