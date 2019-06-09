package com.mcforsas.game.engine.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mcforsas.game.GameLauncher;
import com.mcforsas.game.engine.core.Level;
import com.mcforsas.game.engine.core.Renderable;
import com.mcforsas.game.engine.core.Utils;

import java.util.*;

/**
 * @author MCForsas @since 3/16/2019
 * Renders all the added entities
 */
public class RenderHandler {
    private Camera camera;
    private Viewport viewport;

    private Vector<Renderable> renderables = new Vector<Renderable>();//All rendered items

    private ShaderProgram shader; //A global shader applied to the whole screen

    //Camera positions
    private float xTo = 0, yTo = 0;
    public static final float CAMERA_Z = 0;
    private boolean isCameraBounded = true; //Weather camera is allowed to leave the level

    public void setup(Camera camera, Viewport viewport){
        setCamera(camera);
        setViewport(viewport);
    }

    public void render(SpriteBatch sb, float deltaTime) {
        Gdx.gl.glClearColor(0, .06f, .02f, 1); //Set background color
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Move camera if it's a CameraHandler instance
        if (camera instanceof CameraHandler) {
            ((CameraHandler) camera).updatePosition(xTo, yTo, deltaTime);
        }else{
            //Otherwise set it's position
            camera.position.set(xTo, yTo, CAMERA_Z);
        }

        if (isCameraBounded){
            boundCamera(camera);
        }

        camera.update();
        viewport.apply();

        sb.setProjectionMatrix(camera.combined);

        sb.setShader(shader);
        sb.begin();
        for(int i = 0; i < renderables.size(); i++){
            renderables.get(i).render(sb, deltaTime);
        }
        sb.setShader(null);
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
        viewport.update(width, height);
    }

    //region <Camera>

    private void boundCamera(Camera camera){

        float worldWidth = GameLauncher.getWorldWidth();
        float worldHeight = GameLauncher.getWorldHeight();

        try {
            Level level = GameLauncher.getLevelHandler().getCurrentLevel();
            worldWidth = level.getWidth();
            worldHeight = level.getHeigth();
        }catch (NullPointerException e){
            e.printStackTrace();
        }

            camera.position.x = Utils.clamp(
                    camera.position.x,
                    (camera.viewportWidth/2),
                    (worldWidth - camera.viewportWidth/2)
            );

            camera.position.y = Utils.clamp(
                    camera.position.y,
                    camera.viewportHeight/2,
                    (worldHeight - camera.viewportHeight/2)
            );

            //To avoid shaky screen position camera in the center if viewport dimensions are bigger than worlds.
            if(viewport.getWorldWidth() >= worldWidth || viewport.getWorldHeight() >= worldHeight){
                camera.position.x = worldWidth/2;
                camera.position.y = worldHeight/2;
            }
    }

    public void setCameraPosition(float x, float y){
        xTo = x;
        yTo = y;
    }

    public void translateCamera(float deltaX, float deltaY){
        xTo += deltaX;
        yTo += deltaY;
    }

    //endregion

    //region <Getters and setters>
    public void addRenderable(Renderable renderable){
        renderables.add(renderable);
        refreshRenderOrder();
    }

    public void removeRenderable(Renderable renderable){
        renderables.remove(renderable);
    }

    public boolean isCameraBounded() {
        return isCameraBounded;
    }

    public void setCameraBounded(boolean cameraBounded) {
        isCameraBounded = cameraBounded;
    }

    public Viewport getViewport() {
        return viewport;
    }

    public void setViewport(Viewport viewport) {
        this.viewport = viewport;
    }

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
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
