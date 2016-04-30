package com.overload.algorithms.pathfinding;

import com.overload.loc.Locatable;

import java.util.List;

/**
 * 2D grid-based pathfinding algorithm collection.<br>
 * This pathfinding class is NOT thread-safe.
 * @author Odell
 *
 * forked from https://github.com/muhatashim/OverloadLib
 */
public class Pathfinder implements AlgorithmSettings {

    protected final Algorithm algorithm;
    protected final AlgorithmDefinition definition;

    /**
     * Creates a pathfinder given an algorithm type and flags.
     * @param alg the algorithm implementation to use.
     * @param flags the collision flags.
     */
    public Pathfinder(final Algorithm alg, final Flags flags) {
        if (alg == null)
            throw new IllegalArgumentException("algorithm can't be null");
        this.algorithm = alg;
        this.definition = alg.newDefinition();
        this.definition.setFlags(flags);
    }

    /**
     * Creates a pathfinder given an algorithm type and flags.
     * @param alg the algorithm implementation to use.
     * @param eight whether the algorithm should search in eight or four directions.
     * @param flags the collision flags.
     * @param heur the heuristic used by the algorithm implementation, null allows the algorithm to choose.
     */
    public Pathfinder(final Algorithm alg, final Flags flags, boolean eight, Heuristic heur) {
        this(alg, flags);
        setEight(eight);
        setHeuristic(heur);
    }

    /**
     * Returns the algorithm of this pathfinder.
     * @return the algorithm of this pathfinder.
     */
    public final Algorithm getAlgorithm() {
        return algorithm;
    }

    @Override
    public void setEight(boolean eight) {
        this.definition.setEight(eight);
    }

    @Override
    public void setFlags(Flags flags) {
        this.definition.setFlags(flags);
    }

    @Override
    public void setHeuristic(Heuristic heur) {
        this.definition.setHeuristic(heur);
    }

    @Override
    public void setDiagonal(double diagonal) {
        this.definition.setDiagonal(diagonal);
    }

    /**
     * Finds a path from the start to the end.
     * @param start the starting location.
     * @param end the destination location.
     * @return a valid path to the destination, otherwise null if there was no path.
     */
    public synchronized List<Locatable> findPath(final Locatable start, final Locatable end) {
        return definition.findPath(start, end);
    }

    /**
     * A collision flag provider.
     * @author Odell
     */
    public interface Flags {
        /**
         * Determines whether loc is blocked or not.<br>
         * @param loc a locatable which can be tested.
         * @param parent the parent of loc, can be null.
         * @return whether the given locatable is blocked or not.
         */
        public boolean blocked(final Locatable loc, final Locatable parent);
    }

    /**
     * Algorithm set for this pathfinding collection.
     * @author Odell
     */
    public enum Algorithm {

        /**
         * {@link AStarImpl}
         */
        ASTAR (new AlgorithmAccessor() {
            public AlgorithmDefinition newDefinition() {
                return new AStarImpl();
            }
        }),
        /**
         * {@link JPSImpl}
         */
        JUMP_POINT_SEARCH (new AlgorithmAccessor() {
            public AlgorithmDefinition newDefinition() {
                return new JPSImpl();
            }
        });

        private final AlgorithmAccessor accessor;

        Algorithm(AlgorithmAccessor accessor) {
            this.accessor = accessor;
        }

        AlgorithmDefinition newDefinition() {
            return accessor.newDefinition();
        }

        private interface AlgorithmAccessor {
            AlgorithmDefinition newDefinition();
        }

    }

    /**
     * Pathfinding heuristics.
     * @author Odell
     */
    public enum Heuristic {

        MANHATTAN (new HeuristicImpl() {
            public double distance(Locatable start, Locatable end, double diagonal) {
                return Math.abs(start.getX() - end.getX()) + Math.abs(start.getY() - end.getY());
            }
        }),
        CHEBYSHEV (new HeuristicImpl() {
            public double distance(Locatable start, Locatable end, double diagonal) {
                final int xDiff = Math.abs(end.getX() - start.getX()), yDiff = Math.abs(end.getY() - start.getY());
                final long diag = Math.min(xDiff, yDiff);
                final long manhattan = xDiff + yDiff;
                return (((double) diag) * diagonal) +
                        (double)(manhattan - (diag << 1));
            }
        }),
        EUCLIDEAN (new HeuristicImpl() {
            public double distance(Locatable start, Locatable end, double diagonal) {
                final long xDist = end.getX() - start.getX(), yDist = end.getY() - start.getY();
                return Math.sqrt((xDist * xDist) + (yDist * yDist));
            }
        });

        private final HeuristicImpl impl;

        Heuristic(HeuristicImpl impl) {
            this.impl = impl;
        }

        double distance(Locatable start, Locatable end, double diagonal) {
            return impl.distance(start, end, diagonal);
        }

        private interface HeuristicImpl {
            double distance(final Locatable start, final Locatable end, final double diagonal);
        }

    }

}
