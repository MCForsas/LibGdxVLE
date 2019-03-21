package com.mcforsas.game.levels;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mcforsas.game.engine.Engine;
import com.mcforsas.game.engine.Level;
import com.mcforsas.game.engine.Renderer;
import com.mcforsas.game.engine.Utils;
import com.mcforsas.game.entities.EntitieExample;

/*
 * Created by MCForsas on 3/16/2019
 * Example level to use
 */
public class LevelExample extends Level {
    EntitieExample entitieExample1 = new EntitieExample(Engine.WORLD_WIDTH/2, 1);

    public LevelExample(){
        setWidth(Engine.WORLD_WIDTH*2);
        setHeigth(Engine.WORLD_WIDTH*2);
        setDepth(100);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        if(Engine.getInputHandler().isKeyDown(Input.Keys.W)){
            Renderer r =  Engine.getRenderer();
            r.translateCamera(0,1);
        }
        if(Engine.getInputHandler().isKeyDown(Input.Keys.S)){
            Renderer r =  Engine.getRenderer();
            r.translateCamera(0,-1);
        }
        if(Engine.getInputHandler().isKeyDown(Input.Keys.A)){
            Renderer r =  Engine.getRenderer();
            r.translateCamera(-1,0);
        }
        if(Engine.getInputHandler().isKeyDown(Input.Keys.D)){
            Renderer r =  Engine.getRenderer();
            r.translateCamera(1,0);
        }
        if(Engine.getInputHandler().isKeyDown(Input.Keys.N)){
            Engine.getLevelHandler().nextLevel();
        }
    }

    @Override
    public void start() {
        addEntitie(entitieExample1);

        sprite = new Sprite(Engine.getAssetHandler().getTexture("sprExample"));
        sprite.setPosition(0,0);
        sprite.setSize(getHeigth(), getHeigth());
        super.start();
    }

    @Override
    public void render(SpriteBatch spriteBatch, float deltaTime) {
        super.render(spriteBatch, deltaTime);
    }
}
