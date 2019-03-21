package com.mcforsas.game.levels;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mcforsas.game.engine.Engine;
import com.mcforsas.game.engine.Level;
import com.mcforsas.game.engine.Renderer;
import com.mcforsas.game.entities.EntitieExample;

/*
 * Created by MCForsas on 3/16/2019
 * Example level to use
 */
public class LevelExample2 extends Level {
    EntitieExample entitieExample1 = new EntitieExample(Engine.WORLD_WIDTH/2, 1);

    public LevelExample2(){
        setWidth(Engine.WORLD_WIDTH*2);
        setHeigth(Engine.WORLD_WIDTH*6);
        setDepth(100);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }

    @Override
    public void start() {
        //addEntitie(entitieExample1);

        sprite = new Sprite(Engine.getAssetHandler().getTexture("sprBadlogic"));
        sprite.setPosition(0,0);
        sprite.setSize(getHeigth(), getHeigth());
        super.start();
    }

    @Override
    public void render(SpriteBatch spriteBatch, float deltaTime) {
        super.render(spriteBatch, deltaTime);
    }
}
