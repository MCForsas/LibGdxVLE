package com.mcforsas.game.engine.handlers;

import com.mcforsas.game.engine.Engine;
import com.mcforsas.game.engine.Level;
import com.mcforsas.game.engine.Renderable;

import java.util.Vector;

/**
 * @author MCForsas on 3/16/2019
 * Handles levels. Runs them. Starts and ends.
 */
public class LevelHandler extends Renderable {
    private Vector<Level> levels = new Vector<Level>();
    private Level currentLevel;
    private boolean paused = false;

    //region <Level methods>
    public void startLevel() {
        startLevel(currentLevel);
    }

    public void startLevel(Level level) {
        try {
            level.start();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
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

    public void endLevel(Level level) {
        try {
            level.end();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void endLevel() {
        endLevel(currentLevel);
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

    /**
     * Change the level to the next one.
     */
    public void nextLevel(){
        endLevel();
        int index = levels.indexOf(currentLevel);
        try {
            do{
                index++;
            }while (levels.get(index) == null);
        }catch (ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
        }
        setCurrentLevel(levels.get(index));
    }

    /**
     * Change the level to the previous one.
     */
    public void previousLevel(){
        endLevel();
        int index = levels.indexOf(currentLevel);
        try {
            do{
                index++;
            }while (levels.get(index) == null);
        }catch (ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
        }
        setCurrentLevel(levels.get(index));
    }

    public void restartLevel(){
        endLevel();
        setCurrentLevel(currentLevel);
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

    public Level getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(Level level) throws NullPointerException{
        this.currentLevel = level;

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
