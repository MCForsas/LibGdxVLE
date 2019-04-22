package com.mcforsas.game.levels;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mcforsas.game.engine.core.Engine;
import com.mcforsas.game.engine.core.Level;

/**
 * Created by MCForsas on 3/16/2019
 * Example level to use
 */
public class LevelExample2 extends Level {

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if(Engine.getInputHandler().isKeyDown(Input.Keys.P)){
            Engine.getLevelHandler().previousLevel();
        }
    }

    @Override
    public void start() {
        setWidth(getWidth()*2);
        setHeigth(getHeigth()*6);
        setDepth(100);

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
