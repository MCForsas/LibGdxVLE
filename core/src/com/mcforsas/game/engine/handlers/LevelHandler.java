package com.mcforsas.game.engine.handlers;

import com.mcforsas.game.GameLauncher;
import com.mcforsas.game.engine.core.*;

import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Vector;

/**
 * @author MCForsas on 3/16/2019
 * Handles levels. Runs them. Starts and ends.
 */
public class LevelHandler extends Renderable {
    private Vector<Level> levels = new Vector<Level>();
    private Level currentLevel;
    private boolean paused = false;

    private final boolean REPORT_LEVEL_IS_NULL = false;

    //region <Level methods>
    public void startLevel() {
        startLevel(currentLevel);
    }

    public void startLevel(Level level) {
        try {
            level.start();
        } catch (NullPointerException e) {
            if(REPORT_LEVEL_IS_NULL)
                e.printStackTrace();
        }
    }


    public void update(Level level, float deltaTime) {
        try {
            if (!paused && level.isStarted())
                level.update(deltaTime);
        } catch (NullPointerException e) {
            if(REPORT_LEVEL_IS_NULL)
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
            if(REPORT_LEVEL_IS_NULL)
                e.printStackTrace();
        }
    }

    public void endLevel() {
        endLevel(currentLevel);
    }

    /**
     * Dispose all the objects in level
     */
    public void dispose() {
        for(int i = 0; i < levels.size(); i++){
            levels.get(i).dispose();
        }
    }

    public void dispose(Level level) {
        try {
            level.dispose();
        } catch (NullPointerException e) {
            if(REPORT_LEVEL_IS_NULL)
                e.printStackTrace();
        }
    }

    /**
     * Loops trough all levels calling save method
     * @param fileHandler
     * @param gameData
     */
    public void save(FileHandler fileHandler, GameData gameData){
        for(int i = 0; i < levels.size(); i++){
            levels.get(i).save(fileHandler, gameData);
        }
    }

    /**
     * Change the level to the next one.
     */
    public void nextLevel(){
        endLevel();
        if(levels.lastElement() != currentLevel){
            setCurrentLevel(levels.get(levels.indexOf(currentLevel) + 1));
        }else{
            try {
                throw new NullPointerException();
            }catch (NullPointerException e){
                e.printStackTrace();
            }
        }

    }

    /**
     * Change the level to the previous one.
     */
    public void previousLevel(){
        endLevel();
        if(levels.firstElement() != currentLevel){
            setCurrentLevel(levels.get(levels.indexOf(currentLevel) - 1));
        }else{
            try {
                throw new NullPointerException();
            }catch (NullPointerException e){
                e.printStackTrace();
            }
        }
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
        level.end();
        levels.remove(level);
    }

    public void startFirstLevel() throws NoSuchElementException {
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
        endLevel();
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