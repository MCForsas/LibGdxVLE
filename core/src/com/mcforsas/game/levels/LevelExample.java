package com.mcforsas.game.levels;

import com.mcforsas.game.engine.Engine;
import com.mcforsas.game.engine.Level;
import com.mcforsas.game.engine.LevelHandler;
import com.mcforsas.game.entities.EntitieExample;

/*
 * Created by MCForsas on 3/16/2019
 * Replace this text by description, of what this code is for please,
 * you will know nothing about this code after you close the ide.
 */
public class LevelExample extends Level {
    EntitieExample entitieExample1 = new EntitieExample(Engine.WORLD_WIDTH/2, 1);
    EntitieExample entitieExample2 = new EntitieExample(Engine.WORLD_WIDTH/4, 2);

    @Override
    public void start() {
        addEntitie(entitieExample1);
        addEntitie(entitieExample2);

        setWidth(10);
        setHeigth(10);

        super.start();
    }
}
