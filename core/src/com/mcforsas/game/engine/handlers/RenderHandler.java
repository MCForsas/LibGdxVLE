package com.mcforsas.game.engine.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mcforsas.game.engine.Engine;
import com.mcforsas.game.engine.Level;
import com.mcforsas.game.engine.Renderable;
import com.mcforsas.game.engine.Utils;

import java.util.*;

/**
 * @author MCForsas @since 3/16/2019
 * Renders all the added entities
 */
public class RenderHandler {
    private Vector<Camera> cameras = new Vector<Camera>();
    private Camera currentCamera;

    private Vector<Viewport> viewports = new Vector<Viewport>();
    private Viewport currentViewport;

    private Vector<Renderable> renderables = new Vector<Renderable>();//All rendered items

    public final float CAMERA_Z = 0;
    private boolean isCameraBounded = true; //Weather camera is allowed to leave the level

    //Dimensions of the viewport (Game world dimensions)
    public final int viewportWidth = Engine.WORLD_WIDTH;
    public final int viewportHeight = Engine.WORLD_HEIGHT;

    //Maximum allowed deviation from regular aspect ratio
    public final float maxAspectDeviation = .3f;

    /**
     * Setup default renderer defaults: Ortographic camera, ExtendViewPort and position camera in
     * the center
     */
    public void setupDefault(){
        currentCamera = new CameraHandler(.1f, 6);
        addCemera(currentCamera);

        currentViewport = new ExtendViewport(viewportWidth, viewportHeight,currentCamera);
        setViewportMaxDimensions(
                viewportWidth * (1 + maxAspectDeviation),
                viewportHeight * (1 + maxAspectDeviation)
        );
        addViewport(currentViewport);
    }

    public void render(SpriteBatch sb, float deltaTime){
        Gdx.gl.glClearColor(0,.06f,.02f,1); //Set background color
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        boundCamera(currentCamera);
        currentCamera.update();
        currentViewport.apply();

        sb.setProjectionMatrix(currentCamera.combined);

        sb.begin();
        for(int i = 0; i < renderables.size(); i++){
            renderables.get(i).render(sb, deltaTime);
        }
        sb.end();
    }

    /**
     * Sorts entities based on depth value
     */
    public void refreshRenderOrder(){
        HashMap<Renderable, Float> tempList = new HashMap<Renderable, Float>();
        ValueComparator bvc = new ValueComparator(tempList);
        TreeMap<Renderable, Float> sortedMap = new TreeMap<Renderable, Float>(bvc);

        for(int i = 0; i < renderables.size(); i++){
            Renderable r = renderables.get(i);
            tempList.put(r, r.getDepth());
        }

        sortedMap.putAll(tempList);
        renderables.clear();

        Vector<Renderable> sortedRenderables = new Vector<Renderable>(sortedMap.keySet());
        for(int i = 0; i < sortedRenderables.size(); i++){
            renderables.add(sortedRenderables.get(i));
        }

        renderables = sortedRenderables;
    }

    public void resize(int width, int height){
        currentViewport.update(width, height);
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

        float worldWidth = Engine.WORLD_WIDTH;
        float worldHeight = Engine.WORLD_HEIGHT;

        try {
            Level level = Engine.getLevelHandler().getCurrentLevel();
            worldWidth = level.getWidth();
            worldHeight = level.getHeigth();
        }catch (NullPointerException e){
            e.printStackTrace();
        }

        if(isCameraBounded) {
            x = Utils.clamp(
                    camera.position.x,
                    (camera.viewportWidth/2),
                    (worldWidth - camera.viewportWidth/2)
            );

            y = Utils.clamp(
                    camera.position.y,
                    camera.viewportHeight/2,
                    (worldHeight - camera.viewportHeight/2)
            );
        }

        camera.position.set(x,y, CAMERA_Z);
    }
    //endregion

    //region <Viewport>
    public void addViewport(Viewport viewport){
        viewports.add(viewport);
    }

    public void removeViewport(Viewport viewport){
        viewports.remove(viewport);
    }

    /**
     * Max dimensions before viewport starts letterboxing
     * @param viewport to apply to
     * @param width size in px
     * @param height size in px
     */
    private void setViewportMaxDimensions(Viewport viewport, float width, float height){
        width = Utils.clamp(
                width,
                ((ExtendViewport)viewport).getMinWorldWidth(),
                width
        );

        height = Utils.clamp(
                height,
                ((ExtendViewport)viewport).getMinWorldHeight(),
                height
        );

        ((ExtendViewport) viewport).setMaxWorldWidth(width);
        ((ExtendViewport) viewport).setMaxWorldHeight(height);
    }

    public void setViewportMaxDimensions(float width, float height) {
        setViewportMaxDimensions(currentViewport, width, height);
    }
    //endregion

    //region <Getters and setters>
    public boolean isCameraBounded() {
        return isCameraBounded;
    }

    public void setCameraBounded(boolean cameraBounded) {
        isCameraBounded = cameraBounded;
    }

    public Viewport getCurrentViewport() {
        return currentViewport;
    }

    public void setCurrentViewport(Viewport viewport) {
        this.currentViewport = viewport;
    }

    public Camera getCurrentCamera() {
        return currentCamera;
    }

    public void setCurrentCamera(Camera currentCamera) {
        this.currentCamera = currentCamera;
    }

    //endregion

    /**
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
