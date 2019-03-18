package com.mcforsas.game.engine;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mcforsas.game.levels.LevelExample;

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
	private static SpriteBatch spriteBatch;
	private static AssetHandler assetHandler;
	private static InputHandler inputHandler;

	@Override
	public void create () {
		renderer = new Renderer();
		levelHandler = new LevelHandler();
		spriteBatch = new SpriteBatch();
		assetHandler = new AssetHandler();
		inputHandler = new InputHandler();

		Entitie.setRenderer(renderer);
		renderer.setupDefault();

		loadAssets();
		Gdx.input.setInputProcessor(inputHandler);

		startGame();
	}

	@Override
	public void render () {
		float deltaTime = Gdx.graphics.getDeltaTime();

		update(deltaTime);

		renderer.render(spriteBatch, deltaTime);
	}

	public void update(float deltaTime){
		levelHandler.updateLevel(deltaTime);
	}
	
	@Override
	public void dispose () {
		renderer.dispose();
	}

	/*
	 * After all the assets are loaded and main object created, start the game
	 */
	void startGame(){
		levelHandler.addLevel(new LevelExample());
		levelHandler.startFirstLevel();
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

	public void loadAssets(){
		assetHandler.loadTexture("sprExample", "badlogic.jpg");
	}

	public static Renderer getRenderer() {
		return renderer;
	}

	public static LevelHandler getLevelHandler() {
		return levelHandler;
	}

	public static SpriteBatch getSpriteBatch() {
		return spriteBatch;
	}

	public static AssetHandler getAssetHandler() {
		return assetHandler;
	}

	public static InputHandler getInputHandler() {
		return inputHandler;
	}
}
