package com.mcforsas.game.engine;

import com.badlogic.gdx.Gdx;
import java.util.Random;


/*
 * @author MCForsas @since 3/16/2019
 * Packs a bunch of useful functions.
 */
public final class Utils {

    private Utils(){  }

    public static final String WARNING_TAG = "warning";

    /*
     * Clamps a value between min and max
     * @param value
     * @param min
     * @param max
     * @return float clamped value
     */
    public static float clamp(float val, float min, float max){
        if(val > max){
            return max;
        }
        if(val < min){
            return min;
        }
        return val;
    }

    //region <Random based methods>

    /*
     * Chooses random object from given ones
     * @param Object... objects
     * @return Object
     */
    public static Object choose(Object... objects) {
        Random r = new Random();
        return objects[r.nextInt(objects.length)];
    }

    /*
     * Returns random int between 0 and max
     * @return int
     */
    public static final int irandom(int max) {
        Random r = new Random();
        return r.nextInt(max);
    }

    /*
     * Returns random int between min and max
     * @return int
     */
    public static int irandomRange(int min, int max) {
        Random r = new Random();
        return (r.nextInt((Math.abs(max - min)) + 1) + min);

    }

    /*
     * Returns true at given chance
     * @param int percentage number between 0 - 100
     * @return boolean chance true chance% of the time
     */
    public static boolean chance(int percentage) {
        Random r = new Random();
        return percentage > r.nextInt(100);
    }

    /*
     * Returns first object chance% of the time and second object  100% - chance% of the time
     * @param Object object1
     * @param Object object2
     * @param int percentage number between 0 - 100
     * @return Object chosen object
     */
    public static Object pick(Object object1, Object object2, int percentage) {
        return chance(percentage) ? object2 : object1;
    }
    //endregion

    public static boolean flipBoolean(boolean bool){
        return !bool;
    }

    /*
     * returns number between 0 and 1, which is eased in.
     * @param int progress
     * @return eased out
     */

    public static float easeIn(float value){
        float y = (float) Math.pow(Math.sin(5*value/Math.PI),2);
        return y;
    }

    /*
     * Approaches one value to other, by given completion amount (0 - 1);
     * @param float completion completion rating from 0 to 1
     * @param float from start value
     * @param float to end value
     */
    public static float approach(float completion, float from, float to){
        return from + (to - from) * completion;
    }

    /*
     * Checks if value falls in range between two other values
     * @param value value to check
     * @param min min value
     * @param max max value
     * @return inRange is in range?
     */
    public static boolean isInRange(float value, float min, float max){
        return (value >= min && value <= max);
    }

    /*
     * Returns stack of random ints between min and max
     * @param int min
     * @param int max
     * @param int amount
     * @return Stack<Integer> ints
     */

    public static boolean isInArray(int[] array, int searched) {
        for (int o : array) {
            if (o == searched)
                return true;
        }

        return false;
    }


    /*
     * Prints formated string as warning
     * @param String format format
     * @param Object[] o format
     */
    public static void warnf(String format, Object ... o){
        Gdx.app.log(WARNING_TAG, String.format(format, o));
    }

    /*
     * Prints formated string as warning
     * @param String format format
     */
    public static void warn(String format){
        Gdx.app.log(WARNING_TAG, format);
    }
}
