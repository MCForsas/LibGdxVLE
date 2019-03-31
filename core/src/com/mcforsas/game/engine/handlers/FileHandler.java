package com.mcforsas.game.engine.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.mcforsas.game.engine.GameData;

/**
 * @author MCForsas @since 3/25/2019
 * Reads and writes from files using JSON.
 */
public class FileHandler {
    public final String FILE_PATH = "files/";
    public final String PREFERENCES_NAME = "gdxEnginePreferences";

    private FileHandle fileHandle;
    private Preferences preferences;
    private String fileName;
    private Json json;
    private boolean isEncoded;

    /**
     * Opens file. If encode, encodes/decodes file data
     * @param isEncoded wheather information is encoded or not.
     */
    public FileHandler(String fileName, boolean isEncoded){
        this.fileName = fileName;
        this.isEncoded = isEncoded;

        fileHandle = Gdx.files.local(FILE_PATH + fileName);
        json = new Json(JsonWriter.OutputType.json);
        preferences = Gdx.app.getPreferences(PREFERENCES_NAME);
    }

    /**
     * Saves gameData object to json file.
     * @param gameData
     */
    public void save(GameData gameData){
        String writeString = json.prettyPrint(gameData);
        if(isEncoded){
            writeString = Base64Coder.encodeString(writeString);
        }
        try {

            fileHandle.writeString(writeString, false);
        }catch (Exception e){
            e.printStackTrace();
            Gdx.app.exit();
        }
    }

    /**
     * Loads game data from file or null if none was saved
     * @return gameData loaded data
     */
    public GameData load(){
        GameData data = null;
        String readString = fileHandle.readString();
        if(isEncoded){
            try {
                readString = Base64Coder.decodeString(readString);
            }catch (IllegalArgumentException e){
                e.printStackTrace();
            }

        }

        try {
            data = json.fromJson(GameData.class, readString);
        }catch (Exception e){
            e.printStackTrace();
        }

        return data;
    }

    //region <Preferences>

    /**
     * Puts preference to the preferences file
     * @param key
     * @param value
     * @throws if passed object is of incompatible type with preferences
     */
    public void putPreferences(String key, Object value) throws IllegalArgumentException{
        if(value instanceof String){
            putPreferencesString(key, (String) value);
        }else if(value instanceof Boolean){
            putPreferencesBoolean(key, (Boolean) value);
        }else if(value instanceof Integer){
            putPreferencesInt(key, (Integer) value);
        }else if(value instanceof Float){
            putPreferencesFloat(key, (Float) value);
        }else if(value instanceof Long){
            putPreferencesLong(key, (Long) value);
        }else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Gets preferences from preference file. If none are found, null is returned
     * @param key
     * @param type of data
     * @return
     */
    public Object getPreferences(String key, Class type){
        if(type == String.class){
            return getPrefrencesString(key);
        }else if(type == Boolean.class){
            return getPrefrencesBoolean(key);
        }else if(type == Integer.class){
            return getPreferencesInt(key);
        }else if(type == Float.class){
            return getPrefrencesFloat(key);
        }else if(type == Long.class){
            return getPrefrencesLong(key);
        }
        return null;
    }

    public void putPreferencesString(String key, String value) {
        preferences.putString(key, value);
    }

    public String getPrefrencesString(String key) {
        return preferences.getString(key);
    }

    public void putPreferencesInt(String key, int value) {
        preferences.putInteger(key, value);
    }

    public int getPreferencesInt(String key) {
        return preferences.getInteger(key);
    }

    public void putPreferencesBoolean(String key, boolean value) {
        preferences.putBoolean(key, value);
    }

    public boolean getPrefrencesBoolean(String key) {
        return preferences.getBoolean(key);
    }

    public void putPreferencesFloat(String key, Float value) {
        preferences.putFloat(key, value);
    }

    public Float getPrefrencesFloat(String key) {
        return preferences.getFloat(key);
    }

    public void putPreferencesLong(String key, Long value) {
        preferences.putLong(key, value);
    }

    public Long getPrefrencesLong(String key) {
        return preferences.getLong(key);
    }
    //endregion
}
