package com.mcforsas.game.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import java.util.HashMap;

/*
 * Created by MCForsas on 3/16/2019
 * Loads assets from files. Handles I/O
 */
public class AssetHandler {

    private static HashMap<String, Texture> textures;
    private static HashMap<String, Sound> sounds;
    private static HashMap<String, Music> music;
    private static HashMap<String, FileHandle> data;
    private static HashMap<String, BitmapFont> fonts;


    //region <Directories>
    private static final String TEXTURE_DIR = "textures/";
    private static final String SOUND_DIR = "audio/sounds/";
    private static final String MUSIC_DIR = "audio/music/";
    private static final String FONTS_DIR = "fonts/";
    private static final String FILES_DIR = "files/";
    //endregion

    private static final boolean ANTI_ALIASING = true;

    AssetHandler(){
        textures = new HashMap<String, Texture>();
        sounds = new HashMap<String, Sound>();
        music = new HashMap<String, Music>();
        data = new HashMap<String, FileHandle>();
        fonts = new HashMap<String, BitmapFont>();
    }

    //region <Load data>
    public void loadTexture(String name, String path){
        Texture tx = new Texture(Gdx.files.internal(TEXTURE_DIR + path), ANTI_ALIASING);

        if(ANTI_ALIASING){
            tx.setFilter(
                    Texture.TextureFilter.MipMapLinearLinear,
                    Texture.TextureFilter.MipMapLinearLinear
            );
        }

        textures.put(name, tx);
    }

    public void loadSound(String name, String path) {
        Sound snd = Gdx.audio.newSound(Gdx.files.internal(SOUND_DIR + path));
        sounds.put(name, snd);
    }

    public void loadMusic(String name, String path) {
        Music mus = Gdx.audio.newMusic(Gdx.files.internal(MUSIC_DIR + path));
        music.put(name, mus);
    }

    public void loadFont(String name, String path) {
        BitmapFont font = new BitmapFont(Gdx.files.internal(FONTS_DIR + path));
        fonts.put(name, font);
    }

    public void loadFile(String name, String path) {
        FileHandle f = Gdx.files.internal(FILES_DIR + path);
        data.put(name, f);
    }
    //endregion

    //region <Data getters>
    public static Texture getTexture(String name) {
        return textures.get(name);
    }

    public static Sound getSound(String name) {
        return sounds.get(name);
    }

    public static Music getMusic(String name) {
        return music.get(name);
    }

    public static BitmapFont getFont(String name) {
        return fonts.get(name);
    }

    public static FileHandle getFile(String name) {
        return data.get(name);
    }
    //endregion
}
