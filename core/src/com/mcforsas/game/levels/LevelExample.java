package com.mcforsas.game.levels;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mcforsas.game.engine.Engine;
import com.mcforsas.game.engine.Level;
import com.mcforsas.game.engine.Renderer;
import com.mcforsas.game.entities.EntitieExample;

/*
 * Created by MCForsas on 3/16/2019
 * Example level to use
 */
public class LevelExample extends Level {
    EntitieExample entitieExample1 = new EntitieExample(Engine.WORLD_WIDTH/2, 1);
    EntitieExample entitieExample2 = new EntitieExample(Engine.WORLD_WIDTH/4, 2);

    public LevelExample(){
        setWidth(Engine.WORLD_WIDTH*4);
        setHeigth(Engine.WORLD_WIDTH*4);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        if(Engine.getInputHandler().isKeyDown(Input.Keys.A)){
            Renderer r =  Engine.getRenderer();
            r.setCameraPosition(r.getCamera(), r.getCamera().position.x, r.getCamera().position.y + 1);
        }
    }

    @Override
    public void start() {
        addEntitie(entitieExample1);
        addEntitie(entitieExample2);
        sprite = new Sprite(Engine.getAssetHandler().getTexture("sprExample"));
        sprite.setPosition(0,0);
        sprite.setSize(getWidth(), getHeigth());
        super.start();
    }
}
