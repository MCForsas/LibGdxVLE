package com.mcforsas.game.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
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

    public final float CAMERA_Z = 0;
    private boolean isCameraBounded = true; //Weather camera is allowed to leave the level

    public Renderer(){

    }

    public void setupDefault(){
        currentCamera = new OrthographicCamera();
        addCemera(currentCamera);

        currentViewport = new ExtendViewport(Engine.WORLD_WIDTH, Engine.WORLD_HEIGHT,currentCamera);
        currentViewport.apply();

        setCameraPosition(currentViewport.getWorldWidth()/2, currentViewport.getWorldHeight()/2);
    }

    public void render(SpriteBatch sb, float deltaTime){
        Gdx.gl.glClearColor(0, 1, 0, 1); //Set background color
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        boundCamera(currentCamera);
        currentCamera.update();
        currentViewport.apply();

        sb.setProjectionMatrix(currentCamera.combined);

        sb.begin();
        for(Renderable r : renderables){
            r.render(sb, deltaTime);
        }
        sb.end();
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
        //currentCamera.position.set(currentCamera.viewportWidth/2, currentCamera.viewportHeight / 2, 0);
    }

    public void addRenderable(Renderable renderable){
        renderables.add(renderable);
        refreshRenderOrder();
    }

    public void removeRenderable(Renderable renderable){
        renderables.remove(renderable);
    }

    //region <Camera>
    public Camera getCamera() {
        return currentCamera;
    }

    public void setCamera(Camera currentCamera) {
        this.currentCamera = currentCamera;
    }

    public void addCemera(Camera camera){
        cameras.add(camera);
    }

    public void removeCamera(Camera camera) {
        cameras.remove(camera);
    }

    public void setCameraPosition(Camera camera, float x, float y){
        camera.position.set(x,y,CAMERA_Z);
    }

    public void setCameraPosition(float x, float y){
        setCameraPosition(currentCamera, x, y);
    }

    public void translateCamera(float deltaX, float deltaY){
        translateCamera(currentCamera, deltaX, deltaY);
    }

    public void translateCamera(Camera camera, float deltaX, float deltaY){
        camera.translate(deltaX,deltaY,CAMERA_Z);
    }

    private void boundCamera(Camera camera){
        float x = camera.position.x, y = camera.position.y;


        int worldWidth = Engine.WORLD_WIDTH;
        int worldHeight = Engine.WORLD_HEIGHT;

        try {
            Level level = Engine.getLevelHandler().getCurrentLevel();
            worldWidth = level.getWidth();
            worldHeight = level.getHeigth();
        }catch (NullPointerException e){
            e.printStackTrace();
        }

        if(isCameraBounded) {
            //If a wierd aspect ratio is got, just exit the method
            if(x == worldWidth - camera.viewportWidth/2){
                return;
            }
            if(y == worldHeight - camera.viewportHeight/2){
                return;
            }

            //Continue as normal
            x = Utils.clamp(
                    camera.position.x,
                    camera.viewportWidth/2,
                    worldWidth - camera.viewportWidth/2
            );

            y = Utils.clamp(
                    camera.position.y,
                    camera.viewportHeight/2,
                    worldHeight - camera.viewportHeight/2
            );
            Utils.warnf("X:%f, %f : %f", x, camera.position.x, worldWidth - camera.viewportWidth/2);
        }

        camera.position.set(x,y, CAMERA_Z);
    }
    //endregion

    //region <Viewport>
    public Viewport getViewport() {
        return currentViewport;
    }

    public void setViewport(Viewport viewport) {
        this.currentViewport = viewport;
        viewport.apply();
    }

    public void resizeViewport(int width, int heigth){
        resizeViewport(currentViewport, width, heigth);
    }

    public void resizeViewport(Viewport viewport, int width, int heigth){
        viewport.setWorldSize(width, heigth);
        viewport.apply();
    }

    public void addViewport(Viewport viewport){
        viewports.add(viewport);
    }

    public void removeViewport(Viewport viewport){
        viewports.remove(viewport);
    }

    //endregion

    //region <Getters and setters>
    public boolean isCameraBounded() {
        return isCameraBounded;
    }

    public void setCameraBounded(boolean cameraBounded) {
        isCameraBounded = cameraBounded;
    }
    //endregion

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
