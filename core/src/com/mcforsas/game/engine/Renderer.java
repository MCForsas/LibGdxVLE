package com.mcforsas.game.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.*;

/*
 * Created by MCForsas on 3/16/2019
 * Renders all the added entities
 */
public class Renderer {
    private Vector<Camera> cameras = new Vector<Camera>();
    private Camera currentCamera;

    private Vector<Viewport> viewports = new Vector<Viewport>();
    private Viewport currentViewport;

    private Vector<Renderable> renderables = new Vector<Renderable>(); //All rendered items

    public Renderer(){

    }

    public void setupDefault(){
        currentCamera = new OrthographicCamera();
        addCemera(currentCamera);

        currentViewport = new FitViewport(Engine.WORLD_WIDTH, Engine.WORLD_HEIGHT,currentCamera);
        currentViewport.apply();

        currentCamera.position.set(currentCamera.viewportWidth/2,currentCamera.viewportHeight/2,0);
    }

    public void render(SpriteBatch sb, float deltaTime){
        Gdx.gl.glClearColor(0, 0, 0, 1); //Set background color
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        currentCamera.update();

        sb.setProjectionMatrix(currentCamera.combined);

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
        currentViewport.update(width, height);
        currentCamera.position.set(currentCamera.viewportWidth / 2, currentCamera.viewportHeight / 2, 0);
    }

    public void addRenderable(Renderable renderable){
        renderables.add(renderable);
        refreshRenderOrder();
    }

    public void setCameraPosition(final float x, final float y){
        currentCamera.position.set(x, y,0);
    }

    public Camera getCamera() {
        return currentCamera;
    }

    public void setCamera(Camera currentCamera) {
        this.currentCamera = currentCamera;
    }

    public void addCemera(Camera camera){
        cameras.add(camera);
    }

    public Viewport getViewport() {
        return currentViewport;
    }

    public void setViewport(Viewport viewport) {
        this.currentViewport = viewport;
    }

    public void resizeViewport(int width, int heigth){
        this.currentViewport.setWorldSize(width, heigth);
    }

    public void addViewport(Viewport viewport){
        viewports.add(viewport);
    }

    /*
     * Used for Sorting renderables array
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
