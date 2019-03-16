package com.mcforsas.game.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.OrderedMap;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.*;

/*
 * Created by MCForsas on 3/16/2019
 * Replace this text by description, of what this code is for please,
 * you will know nothing about this code after you close the ide.
 */
public class Renderer {
    private OrthographicCamera camera;
    private FitViewport viewport;

    private Vector<Renderable> renderables = new Vector<Renderable>();
    private Vector<Renderable> renderOrder = new Vector<Renderable>();

    public Renderer(){
        camera.update();
        camera = new OrthographicCamera();
        viewport = new FitViewport(Engine.WORLD_WIDTH, Engine.WORLD_HEIGHT,camera);
        viewport.apply();
        camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);
    }

    public void render(SpriteBatch sb, float deltaTime){
        //Set background color
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        sb.setProjectionMatrix(camera.combined);
        sb.begin();

        for(Renderable r : renderOrder){
            r.render(sb, deltaTime);
        }

        sb.end();
    }

    public void dispose(){
        for(Renderable r : renderOrder){
            r.dispose();
        }
    }

    public void refreshRenderOrder(){
        HashMap<Renderable, Integer> tempSort = new HashMap<Renderable, Integer>();

        for(Renderable r : renderables){
            tempSort.put(r, r.getDepth());
        }

        Object[] sortedRenderables = tempSort.entrySet().toArray();
        Arrays.sort(sortedRenderables, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Map.Entry<String, Integer>) o2).getValue()
                        .compareTo(((Map.Entry<String, Integer>) o1).getValue());
            }
        });
        renderOrder = new Vector(Arrays.asList(sortedRenderables));
    }

    public void resize(int width, int height){
        viewport.update(width, height);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
    }

    public void addRenderable(Renderable renderable){
        renderables.add(renderable);
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public void setCamera(OrthographicCamera camera) {
        this.camera = camera;
    }

    public void setCameraPosition(final float x, final float y){
        camera.position.set(x, y,0);
    }
}
