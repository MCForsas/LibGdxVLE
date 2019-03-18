package com.mcforsas.game.engine;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/*
 * Created by MCForsas on 3/16/2019
 * Renderable framework. Will be used for rendering. Has a sprite by default.
 */
public abstract class Renderable {
    protected Sprite sprite;
    protected static Renderer renderer;

    protected float x = 0, y = 0, z = 0;
    protected float depth = 0;

    public boolean WARN_NO_SPRITE = false; //Weather to print stack trace if no sprite is set

    public void start(){
        renderer.addRenderable(this);
    }

    public void render(SpriteBatch spriteBatch, float deltaTime){
        try {
            sprite.draw(spriteBatch);
        }catch (NullPointerException e){
            if(WARN_NO_SPRITE)
                e.printStackTrace();
        }
    }

    public void dispose(){
        try {
            sprite.getTexture().dispose();
        }catch (NullPointerException e){
            if(WARN_NO_SPRITE)
                e.printStackTrace();
        }
    }


    //region <Getters and setters>
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

    public static Renderer getRenderer() {
        return renderer;
    }

    public static void setRenderer(Renderer rendererObject) {
        renderer = rendererObject;
    }

    public float getDepth() {
        return depth;
    }

    public void setDepth(float depth) {
        try {
            renderer.refreshRenderOrder();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        this.depth = depth;
    }
    //endregion
}
