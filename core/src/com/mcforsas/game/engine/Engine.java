package com.mcforsas.game.engine;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mcforsas.game.entities.EntitieExample;
import com.mcforsas.game.levels.LevelExample;

/*
 * Created by MCForsas on 3/16/2019
 * Main game class holds all game constants, main game loop and handlers
 */

public class Engine extends ApplicationAdapter {
	public static final int WORLD_WIDTH = 10, WORLD_HEIGHT = 10;

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

		loadAssets();
		Gdx.input.setInputProcessor(inputHandler);

		levelHandler.addLevel(new LevelExample());
		levelHandler.startFirstLevel();
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
