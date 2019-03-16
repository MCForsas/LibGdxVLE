package com.mcforsas.game.engine;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mcforsas.game.entities.EntitieExample;

/*
 * Created by MCForsas on 3/16/2019
 * Main game class holds all game constants, main game loop and handlers
 */

public class Engine extends ApplicationAdapter {
	public static final int WORLD_WIDTH = 10, WORLD_HEIGHT = 10;

	private Renderer renderer;
	private LevelHandler levelHandler;
	private SpriteBatch spriteBatch;
	private AssetHandler assetHandler;
	private InputHandler inputHandler;

	@Override
	public void create () {
		renderer = new Renderer();
		levelHandler = new LevelHandler();
		spriteBatch = new SpriteBatch();
		assetHandler = new AssetHandler();
		inputHandler = new InputHandler();

		loadAssets();
		Gdx.input.setInputProcessor(inputHandler);

		EntitieExample entitieExample1 = new EntitieExample(Engine.WORLD_WIDTH/2, 2);
		EntitieExample entitieExample2 = new EntitieExample(Engine.WORLD_WIDTH/4, 3);

		renderer.addRenderable(entitieExample1);
		renderer.addRenderable(entitieExample2);


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
}
