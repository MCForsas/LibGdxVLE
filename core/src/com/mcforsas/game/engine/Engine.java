package com.mcforsas.game.engine;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mcforsas.game.levels.LevelExample;
import com.mcforsas.game.levels.LevelExample2;

/*
 * Created by MCForsas on 3/16/2019
 * Main game class holds all game constants, main game loop and handlers
 */

public class Engine extends ApplicationAdapter {
	//Constants
	public static final int WORLD_WIDTH = 10, WORLD_HEIGHT = 10;

	//Main handlers
	private static Renderer renderer;
	private static LevelHandler levelHandler;
	private static AssetHandler assetHandler;
	private static InputHandler inputHandler;

	private SpriteBatch spriteBatch;
	private Thread assetLoadingThread;

	@Override
	public void create () {
		renderer = new Renderer();
		levelHandler = new LevelHandler();
		assetHandler = new AssetHandler();
		inputHandler = new InputHandler();

		spriteBatch = new SpriteBatch();

		renderer.setupDefault();

		assetLoadingThread = new Thread(new QeueuLoader());

		Gdx.app.postRunnable(assetLoadingThread);

		Gdx.input.setInputProcessor(inputHandler);
	}

	@Override
	public void render () {
		float deltaTime = Gdx.graphics.getDeltaTime();
		update(deltaTime);
		renderer.render(spriteBatch, deltaTime);
	}

	public void update(float deltaTime){
		levelHandler.update(deltaTime);
	}
	
	@Override
	public void dispose () {
		assetHandler.dispose();
		levelHandler.dispose();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		renderer.resize(width, height);
	}

	@Override
	public void pause() {
		super.pause();
		levelHandler.setPaused(true);
	}

	@Override
	public void resume(){
		super.resume();
		levelHandler.setPaused(false);
	}

	/*
	 * After all the assets are loaded and main object created, start the game
	 */
	public void startGame(){
		levelHandler.addLevel(new LevelExample());
		levelHandler.addLevel(new LevelExample2());
		levelHandler.startFirstLevel();
	}

	public void loadAssets(){
		assetHandler.addToQueue(Texture.class, "sprBadlogic", "badlogic.jpg");
		assetHandler.addToQueue(Texture.class, "sprExample", "example.jpg");
		assetHandler.addToQueue(Music.class, "musExample","example.ogg");
		assetHandler.addToQueue(Sound.class, "sndExample","test.wav");

		assetHandler.startLoadingQueu();

		startGame();
	}

	//region <Getters>
	public static Renderer getRenderer() {
		return renderer;
	}

	public static LevelHandler getLevelHandler() {
		return levelHandler;
	}

	public static AssetHandler getAssetHandler() {
		return assetHandler;
	}

	public static InputHandler getInputHandler() {
		return inputHandler;
	}
	//endregion

	private class QeueuLoader implements Runnable {
		@Override
		public void run() {
			loadAssets();
		}
	}
}
