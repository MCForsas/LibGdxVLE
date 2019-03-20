package com.mcforsas.game.engine;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/*
 * Created by MCForsas on 3/16/2019
 * Shows loading screnn
 */

public class LoadingScreen extends Renderable {
    BitmapFont font  = new BitmapFont();

    @Override
    public void render(SpriteBatch spriteBatch, float deltaTime) {
        super.render(spriteBatch, deltaTime);
        font.draw(spriteBatch,"Hello",Engine.WORLD_WIDTH/2,Engine.WORLD_HEIGHT/2);
    }
}
