package com.overload.loc;

/**
 * A class based wrapper for the Locatable interface.
 * @author Odell
 *
 * forked from https://github.com/muhatashim/OverloadLib
 */
public class Node implements Locatable {

    protected int x, y;

    /**
     * Creates a new Node object with the given x,y coordinates.
     * @param x
     * 		the X position of this Node.
     * @param y
     * 		the Y position of this Node.
     */
    public Node(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Creates a new Node object by cloning the given Locatable.
     * @param loc
     * 		the Locatable to use to create this Node.
     */
    public Node(final Locatable loc) {
        this(loc.getX(), loc.getY());
    }

    /**
     * Modifies the internal x,y positions of this Node.
     * @param x
     * 		the value to shift the X position of this Node.
     * @param y
     * 		the value to shift the Y position of this Node.
     * @return this Node.
     */
    public Node shift(final int x, final int y) {
        this.x += x;
        this.y += y;
        return this;
    }

    /**
     * Creates a copy of this Node shifted by the given values.
     * @param x
     * 		the value to shift the X position of this Node.
     * @param y
     * 		the value to shift the Y position of this Node.
     * @return a copy of this Node shifted.
     */
    public Node derive(final int x, final int y) {
        return clone().shift(x, y);
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    /**
     * Sets the X position of this Node.
     * @param x
     * 		the new X position of this Node.
     */
    public Node setX(final int x) {
        this.x = x;
        return this;
    }

    /**
     * Sets the Y position of this Node.
     * @param y
     * 		the new Y position of this Node.
     */
    public Node setY(final int y) {
        this.y = y;
        return this;
    }

    /**
     * Sets both coordinates of this Node.
     * @param x the new X position of this Node.
     * @param y the new Y position of this Node.
     */
    public Node set(final int x, final int y) {
        return setX(x).setY(y);
    }

    @Override
    public Node clone() {
        return new Node(x, y);
    }

    @Override
    public boolean equals(Object o) {
        return o != null && o instanceof Locatable &&
                ((Locatable) o).getX() == getX() && ((Locatable) o).getY() == getY();
    }

    @Override
    public int hashCode() {
        int hash = 373;
        hash = 31 * hash + x;
        hash = 31 * hash + y;
        return hash;
    }

    @Override
    public String toString() {
        return "Node(" + getX() + "," + getY() + ")";
    }

    /**
     * A double precision class based wrapper for the Locatable interface.
     * @author Odell
     */
    public static class Double implements Locatable.Double {

        protected double x, y;

        /**
         * Creates a new Node object with the given x,y coordinates.
         * @param x
         * 		the X position of this Node.
         * @param y
         * 		the Y position of this Node.
         */
        public Double(final double x, final double y) {
            this.x = x;
            this.y = y;
        }

        /**
         * Creates a new Node object by cloning the given Locatable.Double.
         * @param loc
         * 		the Locatable.Double to use to create this Node.
         */
        public Double(final Locatable.Double loc) {
            this(loc.getX(), loc.getY());
        }

        /**
         * Modifies the internal x,y positions of this Node.
         * @param x
         * 		the value to shift the X position of this Node.
         * @param y
         * 		the value to shift the Y position of this Node.
         * @return this Node.
         */
        public Double shift(final double x, final double y) {
            this.x += x;
            this.y += y;
            return this;
        }

        /**
         * Creates a copy of this Node shifted by the given values.
         * @param x
         * 		the value to shift the X position of this Node.
         * @param y
         * 		the value to shift the Y position of this Node.
         * @return a copy of this Node shifted.
         */
        public Double derive(final double x, final double y) {
            return clone().shift(x, y);
        }

        @Override
        public double getX() {
            return x;
        }

        @Override
        public double getY() {
            return y;
        }

        /**
         * Sets the X position of this Node.
         * @param x
         * 		the new X position of this Node.
         */
        public void setX(final double x) {
            this.x = x;
        }

        /**
         * Sets the Y position of this Node.
         * @param y
         * 		the new Y position of this Node.
         */
        public void setY(final double y) {
            this.y = y;
        }

        @Override
        public Double clone() {
            return new Double(x, y);
        }

        @Override
        public boolean equals(Object o) {
            return o != null && o instanceof Locatable.Double && ((Locatable.Double) o).getX() == getX() && ((Locatable.Double) o).getY() == getY();
        }

        @Override
        public int hashCode() {
            double hash = 373.0D;
            hash = 31.0D * hash + x;
            hash = 31.0D * hash + y;
            return (int) hash;
        }

        @Override
        public String toString() {
            return "Node.Double(" + getX() + "," + getY() + ")";
        }

    }

}
