package com.mcforsas.game.engine;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mcforsas.game.engine.handlers.AssetHandler;
import com.mcforsas.game.engine.handlers.InputHandler;
import com.mcforsas.game.engine.handlers.LevelHandler;
import com.mcforsas.game.engine.handlers.RenderHandler;
import com.mcforsas.game.levels.LevelExample;
import com.mcforsas.game.levels.LevelExample2;

import java.util.NoSuchElementException;

/**
 * @author MCForsas @since 3/16/2019
 * Main game class holds all game constants, main game loop and handlers
 */

public class Engine extends ApplicationAdapter {
	//Constants
	public static final int WORLD_WIDTH = 16, WORLD_HEIGHT = 9;

	//Main handlers
	private static RenderHandler renderHandler;
	private static LevelHandler levelHandler;
	private static AssetHandler assetHandler;
	private static InputHandler inputHandler;

	private SpriteBatch spriteBatch;

	@Override
	public void create () {
		renderHandler = new RenderHandler();
		levelHandler = new LevelHandler();
		assetHandler = new AssetHandler();
		inputHandler = new InputHandler();

		spriteBatch = new SpriteBatch();

		renderHandler.setupDefault();
		Gdx.input.setInputProcessor(inputHandler);

		Thread assetLoadingThread = new Thread(new QeueuLoader()); //Loads assets on a separate thread
		Gdx.app.postRunnable(assetLoadingThread);

	}

	@Override
	public void render () {
		float deltaTime = Gdx.graphics.getDeltaTime();
		update(deltaTime);
		renderHandler.render(spriteBatch, deltaTime);
	}

	/**
	 * Updates game ie: game logic
	 * @param deltaTime time that passed since last render update.
	 */
	private void update(float deltaTime){
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
		renderHandler.resize(width, height);
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

	/**
	 * After all the assets are loaded and main object created, start the game - setup default.
	 */
	private void startGame(){
		levelHandler.addLevel(new LevelExample());
		levelHandler.addLevel(new LevelExample2());
		try {
			levelHandler.startFirstLevel();
		}catch (NoSuchElementException e){
			e.printStackTrace();
		}

	}

	private void loadAssets(){
		assetHandler.addToQueue(Texture.class, "sprBadlogic", "badlogic.jpg");
		assetHandler.addToQueue(Texture.class, "sprExample", "example.jpg");
		assetHandler.addToQueue(Music.class, "musExample","example.ogg");
		assetHandler.addToQueue(Sound.class, "sndExample","test.wav");

		assetHandler.startLoadingQueue();

		startGame();
	}

	//region <Getters>
	public static RenderHandler getRenderHandler() {
		return renderHandler;
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
