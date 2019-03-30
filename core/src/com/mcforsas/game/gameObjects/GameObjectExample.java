package com.mcforsas.game.gameObjects;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mcforsas.game.engine.*;
import com.mcforsas.game.engine.handlers.AssetHandler;
import com.mcforsas.game.engine.handlers.CameraHandler;

/**
 * @author MCForsas @since 3/16/2019
 * example Game object
 */
public class GameObjectExample extends GameObject implements InputListener {

    private float moveSpeed = .1f;

    public GameObjectExample(float x, float depth, Level level){
        sprite = new Sprite(AssetHandler.getTexture("sprBadlogic"));
        this.x = x;
        this.y = x;
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
    public void update(float deltaTime) {
        if(Engine.getInputHandler().isKeyDown(Input.Keys.W)){
            y += moveSpeed * deltaTime * 100;
        }
        if(Engine.getInputHandler().isKeyDown(Input.Keys.S)){
            y -= moveSpeed * deltaTime * 100;
        }
        if(Engine.getInputHandler().isKeyDown(Input.Keys.A)){
            x -= moveSpeed * deltaTime * 100;
        }
        if(Engine.getInputHandler().isKeyDown(Input.Keys.D)){
            x += moveSpeed * deltaTime * 100;
        }

        x = Utils.clamp(x, 0, level.getWidth());
        y = Utils.clamp(y, 0, level.getHeigth());

        super.update(deltaTime);
        CameraHandler camera = (CameraHandler) Engine.getRenderHandler().getCurrentCamera();
        camera.updatePosition(x,y,deltaTime);
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
