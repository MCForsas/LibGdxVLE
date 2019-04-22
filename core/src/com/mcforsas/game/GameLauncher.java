package com.mcforsas.game;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.mcforsas.game.engine.core.Engine;
import com.mcforsas.game.levels.LevelExample;

/*
 * Created by mcforsas on 19.4.21
 * Game object. All the config and constants should be set here
 */
public class GameLauncher extends Engine {
    //Config here
    public GameLauncher() {
//        FPS = 120;
//        WORLD_WIDTH = 10;
//        WORLD_HEIGHT = 10;
//        RESOLUTION_H = 1080/4;
//        RESOLUTION_V = 1920/4;
    }

    @Override
    public void create() {
        super.create();
        renderHandler.setupDefault();
    }

    @Override
    protected void startGame() {
        levelHandler.addLevel(new LevelExample());
        super.startGame();
    }

    @Override
    protected void loadAssets() {
        assetHandler.addToQueue(Texture.class, "sprBadlogic", "badlogic.jpg");
        assetHandler.addToQueue(Texture.class, "sprExample", "example.jpg");
        assetHandler.addToQueue(Music.class, "musExample","example.ogg");
        assetHandler.addToQueue(Sound.class, "sndExample","test.wav");
        super.loadAssets();
    }
}
