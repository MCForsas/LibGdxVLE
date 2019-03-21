package com.mcforsas.game.engine;

import java.util.Vector;

/*
 * Created by MCForsas on 3/16/2019
 * Handles levels. Runs them. Starts and stops.
 */
public class LevelHandler extends Renderable{
    private static final boolean FIT_VIEWPORT_ON_START = false;
    private Vector<Level> levels = new Vector<Level>();
    private Level currentLevel;
    private boolean paused = false;

    //region <Level methods>
    public void startLevel() {
        startLevel(currentLevel);
    }

    public void startLevel(Level level) {
        try {
            if(FIT_VIEWPORT_ON_START){
                fitViewport();
            }
            level.start();
            Engine.getRenderer().addRenderable(currentLevel);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void endLevel(Level level) {
        try {
            level.end();
            Engine.getRenderer().removeRenderable(level);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void endLevel() {
        endLevel(currentLevel);
    }

    public void nextLevel(){
        int index = levels.indexOf(currentLevel);
        while(levels.get(++index) == null){

        }
        endLevel();
        setCurrentLevel(levels.get(index));
    }

    public void update(Level level, float deltaTime) {
        try {
            if (!paused)
                level.update(deltaTime);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void update(float deltaTime) {
        update(currentLevel, deltaTime);
    }

    public void dispose() {
        dispose(currentLevel);
    }

    public void dispose(Level level) {
        try {
            level.dispose();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }
    //endregion

    public void addLevel(Level level){
        levels.add(level);
    }

    public void removeLevel(Level level) {
        levels.remove(level);
        Engine.getRenderer().removeRenderable(level);
    }

    public void startFirstLevel(){
        Level level = levels.firstElement();

        try {
            setCurrentLevel(level);
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    public void fitViewport(Level level) {
        if(
                level.getHeigth() != Engine.getRenderer().getViewport().getWorldHeight() ||
                        level.getWidth() != Engine.getRenderer().getViewport().getWorldWidth()
        ){
            Engine.getRenderer().resizeViewport(
                    level.getWidth(),
                    level.getHeigth()
            );
        }
    }

    public void fitViewport(){
        fitViewport(currentLevel);
    }

    public Level getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(Level level) throws NullPointerException{
        this.currentLevel = level;
        Engine.getRenderer().addRenderable(level);

        if(level == null)
            throw new NullPointerException();
        if(!level.isStarted()){
            startLevel();
        }
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }
}
