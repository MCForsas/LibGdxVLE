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
 * Renders all the added entities
 */
public class Renderer {
    private OrthographicCamera camera;
    private FitViewport viewport;

    private Vector<Renderable> renderables = new Vector<Renderable>();

    public Renderer(){
        camera = new OrthographicCamera();

        viewport = new FitViewport(Engine.WORLD_WIDTH, Engine.WORLD_HEIGHT,camera);
        viewport.apply();
        camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);
    }

    public void render(SpriteBatch sb, float deltaTime){
        //Set background color
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();

        sb.setProjectionMatrix(camera.combined);
        sb.begin();

        for(Renderable r : renderables){
            r.render(sb, deltaTime);
        }

        sb.end();
    }

    public void dispose(){
        for(Renderable r : renderables){
            r.dispose();
        }
    }

    /*
     * Sorts entities based on depth value
     */
    public void refreshRenderOrder(){
        HashMap<Renderable, Float> tempList = new HashMap<Renderable, Float>();
        ValueComparator bvc = new ValueComparator(tempList);
        TreeMap<Renderable, Float> sortedMap = new TreeMap<Renderable, Float>(bvc);

        for(Renderable r : renderables){
            tempList.put(r, r.getDepth());
        }

        sortedMap.putAll(tempList);

        renderables.clear();

        for (Map.Entry<Renderable, Float>entry : sortedMap.entrySet()) {
            renderables.add(entry.getKey());
        }
    }

    public void resize(int width, int height){
        viewport.update(width, height);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
    }

    public void addRenderable(Renderable renderable){
        renderables.add(renderable);
        refreshRenderOrder();
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

    /*
     * Used for comparing values
     */
    private class ValueComparator implements Comparator<Renderable> {
        Map<Renderable, Float> base;

        public ValueComparator(Map<Renderable, Float> base) {
            this.base = base;
        }

        // Note: this comparator imposes orderings that are inconsistent with
        // equals.
        public int compare(Renderable a, Renderable b) {
            if (base.get(a) >= base.get(b)) {
                return -1;
            } else {
                return 1;
            } // returning 0 would merge keys
        }
    }
}
