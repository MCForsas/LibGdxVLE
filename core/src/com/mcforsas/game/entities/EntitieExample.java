package com.mcforsas.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mcforsas.game.engine.*;

/*
 * Created by MCForsas on 3/16/2019
 * example entitie
 */
public class EntitieExample extends Entitie implements InputListener {

    public EntitieExample(float x, float depth){
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
