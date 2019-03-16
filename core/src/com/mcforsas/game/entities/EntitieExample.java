package com.mcforsas.game.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mcforsas.game.engine.AssetHandler;
import com.mcforsas.game.engine.Engine;
import com.mcforsas.game.engine.Entitie;
import com.mcforsas.game.engine.Utils;

/*
 * Created by MCForsas on 3/16/2019
 * example entitie
 */
public class EntitieExample extends Entitie {

    public EntitieExample(float x, float depth){
        sprite = new Sprite(AssetHandler.getTexture("sprExample"));
        this.x = x;
        y = Engine.WORLD_HEIGHT/2;

        sprite.setPosition(x,y);
        sprite.setSize(Engine.WORLD_WIDTH/2,y);

        setDepth(depth);
    }

    @Override
    public void render(SpriteBatch spriteBatch, float deltaTime) {
        super.render(spriteBatch, deltaTime);
    }
}
