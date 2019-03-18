package com.mcforsas.game.engine;

import java.util.Vector;

/*
 * Created by MCForsas on 3/16/2019
 * Handles levels. Runs them. Starts and stops.
 */
public class LevelHandler extends Renderable{
    private Vector<Level> levels = new Vector<Level>();
    private Level currentLevel;
    private boolean paused = false;

    public void startLevel(Level level){
        level.start();
    }

    public void startLevel(){
        try{
            if(
                    currentLevel.getHeigth() != Engine.getRenderer().getViewport().getWorldHeight() ||
                    currentLevel.getWidth() != Engine.getRenderer().getViewport().getWorldWidth()
            ){
                Engine.getRenderer().resizeViewport(
                        Engine.getRenderer().getViewport(),
                        currentLevel.getWidth(),
                        currentLevel.getHeigth()
                );
            }

            currentLevel.start();
            Engine.getRenderer().addRenderable(currentLevel);
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    public void endLevel(Level level){
        level.end();
    }

    public void endLevel(){
        try{
            currentLevel.end();
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    public void addLevel(Level level){
        levels.add(level);
    }

    public void updateLevel(float deltaTime){
        try{
            if(!paused)
                currentLevel.update(deltaTime);
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    public void startFirstLevel(){
        Level level = levels.firstElement();

        try {
            setCurrentLevel(level);
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    public Level getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(Level currentLevel) throws NullPointerException{
        this.currentLevel = currentLevel;
        if(currentLevel == null)
            throw new NullPointerException();
        if(!currentLevel.isStarted()){
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
