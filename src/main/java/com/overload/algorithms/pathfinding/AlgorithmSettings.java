package com.overload.algorithms.pathfinding;

import com.overload.algorithms.pathfinding.Pathfinder.Flags;
import com.overload.algorithms.pathfinding.Pathfinder.Heuristic;

/**
 * Defines set methods for algorithm definitions.
 * @author Odell
 *
 * forked from https://github.com/muhatashim/OverloadLib
 */
interface AlgorithmSettings {

    /**
     * Sets whether the implementation allows eight way path finding.<br>
     * Some algorithms may not support both four and eight way path finding, rendering this method useless.
     */
    public void setEight(boolean eight);

    /**
     * Sets the collision flags for this pathfinder.
     * @param flags the collision flags for the algorithm implementation.
     */
    public void setFlags(final Flags flags);

    /**
     * Sets the heuristics of this pathfinder.
     * @param heur the heuristics for the algorithm implementation.
     */
    public void setHeuristic(final Heuristic heur);

    /**
     * Sets the cost of diagonal movement.<br>
     * Only used in eight way path finding, {@link setEight(true)} enables the use of this function.<br>
     * Defaults to 1.0, which is equal to straight movement.
     * @param diagonal the cost of diagonal movement.
     */
    public void setDiagonal(final double diagonal);

}
