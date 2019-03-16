package com.mcforsas.game.engine;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/*
 * Created by MCForsas on 3/16/2019
 * Renderable framework. Will be used for rendering. Has a sprite b default.
 */
public class Renderable {
    protected Sprite sprite;
    protected float x = 0, y = 0, z = 0;
    protected int depth = 0;

    protected Renderer renderer;

    public void render(SpriteBatch spriteBatch, float deltaTime){
        if(sprite != null)
            sprite.draw(spriteBatch);
    }

    public void dispose(){
        if(sprite != null)
            sprite.getTexture().dispose();
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public Renderer getRenderer() {
        return renderer;
    }

    public void setRenderer(Renderer renderer) {
        this.renderer = renderer;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        try{
            renderer.refreshRenderOrder();
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        this.depth = depth;
    }
}
