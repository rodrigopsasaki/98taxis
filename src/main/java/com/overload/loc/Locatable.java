package com.overload.loc;

/**
 * An x,y all-purpose position.
 * @author Odell
 *
 * forked from https://github.com/muhatashim/OverloadLib
 */
public interface Locatable {

    /**
     * @return the X position of this Locatable.
     */
    public int getX();

    /**
     * @return the Y position of this Locatable.
     */
    public int getY();

    public static interface Double {

        /**
         * @return the X value of this Locatable.
         */
        public double getX();

        /**
         * @return the Y value of this Locatable.
         */
        public double getY();

    }

}
