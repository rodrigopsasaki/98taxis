package com.overload.algorithms.pathfinding;

import com.overload.loc.Locatable;
import com.overload.loc.Node;

/**
 * A node with a parent.
 * @author Odell
 *
 * forked from https://github.com/muhatashim/OverloadLib
 */
abstract class PNode extends Node implements Comparable<PNode> {

    PNode(final int x, final int y) {
        super(x, y);
    }

    PNode(final Locatable loc) {
        super(loc);
    }

    /**
     * Returns the parent of this node.
     * @return the parent of this node.
     */
    public abstract PNode getParent();

    /**
     * Gets all neighboring nodes to this node.<br>
     * Starts @ north and goes clockwise.
     * @param eight to search in 4 or 8 directions.
     * @return an array of neighboring nodes.
     */
    protected abstract PNode[] getNeighbors(final boolean eight);

    @Override
    public PNode clone() {
        final PNode pn = this;
        return new PNode(this) {
            public PNode getParent() {
                return pn.getParent();
            }
            protected PNode[] getNeighbors(boolean eight) {
                return null;
            }
        };
    }

    @Override
    public int compareTo(PNode pn) {
        if (getY() != pn.getY())
            return getY() - pn.getY();
        return getX() - pn.getX();
    }

    /**
     * Determines whether the two locatable nodes are diagonal each other or not.
     * @return <tt>true</tt> if diagonal, otherwise <tt>false</tt>.
     */
    public static boolean isDiagonal(final Locatable l1, final Locatable l2) {
        return l1 != null && l2 != null && l1.getX() != l2.getX() && l1.getY() != l2.getY();
    }

}
