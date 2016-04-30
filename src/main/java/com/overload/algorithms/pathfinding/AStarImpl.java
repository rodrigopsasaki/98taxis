package com.overload.algorithms.pathfinding;

import com.overload.algorithms.pathfinding.Pathfinder.Flags;
import com.overload.algorithms.pathfinding.Pathfinder.Heuristic;
import com.overload.impl.DoubleComparator;
import com.overload.loc.Locatable;
import com.overload.loc.Node;

import java.util.*;

/**
 * A* pathfinding algorithm first described by </br>
 * Peter Hart, Nils Nilsson, and Bertram Raphael in 1968.</br>
 * Based upon Dijkstra's pathfinding algorithm, with added heuristics.
 * @author Odell
 *
 * forked from https://github.com/muhatashim/OverloadLib
 */
class AStarImpl implements AlgorithmDefinition {

    private final PriorityQueue<ANode> open;
    private final HashSet<ANode> closed;
    private final HashMap<ANode, ANode> parentMap;
    private ANode curr = null;
    private Node dest = null;

    private Flags flags;
    private Heuristic heur;
    private boolean eight;
    private double diagonal = 1.0D;

    AStarImpl() {
        open = new PriorityQueue<ANode>(10, new DoubleComparator<ANode>() {
            public double compareD(ANode n1, ANode n2) {
                return n1.getF() - n2.getF();
            }
        });
        closed = new HashSet<ANode>();
        parentMap = new HashMap<ANode, ANode>();
    }

    @Override
    public void setEight(boolean eight) {
        this.eight = eight;
    }

    @Override
    public void setFlags(Flags flags) {
        this.flags = flags;
    }

    @Override
    public void setHeuristic(Heuristic heur) {
        this.heur = heur;
    }

    @Override
    public void setDiagonal(double diagonal) {
        this.diagonal = diagonal;
    }

    @Override
    public List<Locatable> findPath(final Locatable start, final Locatable end) {
        if (heur == null)
            this.heur = Heuristic.MANHATTAN; // recommended
        try {
            dest = new Node(end);
            if (flags == null || !flags.blocked(dest, null)) { // ensure the destination isn't blocked already
                curr = new ANode(start);
                do {
                    if (curr.equals(end))
                        return resolve(curr);
                    closed.add(curr);
                    for (final ANode neighbor : curr.getNeighbors(flags, eight)) {
                        if (closed.contains(neighbor))
                            continue;
                        if (!open.contains(neighbor)) {
                            neighbor.setParent(curr);
                            open.offer(neighbor);
                        } else if ((curr.getG() + curr.getMoveCost(neighbor)) < neighbor.getG()) { // G score of node with current node as it's parent
                            final ANode instanceNode = retrieveInstance(open, neighbor);
                            if (instanceNode != null)
                                instanceNode.setParent(curr);
                        }
                    }
                } while ((curr = open.poll()) != null);
            }
            return null;
        } finally {
            open.clear();
            closed.clear();
            parentMap.clear();
            curr = null;
            dest = null;
        }
    }

    private List<Locatable> resolve(ANode target) {
        if (target == null)
            return null;
        final LinkedList<Locatable> path = new LinkedList<Locatable>();
        do {
            path.addFirst(target);
        } while ((target = target.getParent()) != null);
        return new ArrayList<Locatable>(path);
    }

    private ANode retrieveInstance(final PriorityQueue<ANode> pq, final ANode node) {
        if (node == null)
            return null;
        final Iterator<ANode> nI = pq.iterator();
        while (nI.hasNext()) {
            final ANode n = nI.next();
            if (node.equals(n))
                return n;
        }
        return null;
    }

    /**
     * A node used for AStar.
     * @author Odell
     */
    private class ANode extends PNode {

        /**
         * the estimated (heuristic) cost to reach the destination from here.
         */
        private java.lang.Double h;
        /**
         * the exact cost to reach this node from the starting node.
         */
        private java.lang.Double g;
        /**
         * As the algorithm runs the F value of a node tells us how expensive we think it will be to reach our goal by way of that node.
         */
        private java.lang.Double f;

        public ANode(final int x, final int y) {
            super(x, y);
            h = g = f = null;
        }

        public ANode(final Locatable n) {
            this(n.getX(), n.getY());
        }

        @Override
        public ANode getParent() {
            return parentMap.get(this);
        }

        /**
         * @return the previous parent node.
         */
        protected ANode setParent(final ANode node) {
            g = null;
            return parentMap.put(this, node);
        }

        protected double getF() {
            if (g == null || h == null || f == null) {
                f = getG() + getH();
            }
            return f;
        }

        protected double getG() {
            if (g == null) {
                final ANode parent = getParent();
                g = parent != null ? parent.getG() + parent.getMoveCost(this) : 0.0D;
            }
            return g;
        }

        private double getMoveCost(final ANode adjacent) {
            if (adjacent == null)
                return 0;
            return (x == adjacent.x || y == adjacent.y) ? 1.0D : diagonal;
        }

        protected double getH() {
            if (h == null) {
                h = heur.distance(this, dest, diagonal);
            }
            return h;
        }

        @Override
        protected ANode[] getNeighbors(boolean eight) {
            return eight ? new ANode[] {
                    new ANode(x, y - 1), new ANode(x + 1, y - 1), new ANode(x + 1, y), new ANode(x + 1, y + 1),
                    new ANode(x, y + 1), new ANode(x - 1, y + 1), new ANode(x - 1, y), new ANode(x - 1, y - 1)
            } : new ANode[] {
                    new ANode(x, y - 1), new ANode(x + 1, y), new ANode(x, y + 1), new ANode(x - 1, y)
            };
        }

        protected ANode[] getNeighbors(final Flags flags, final boolean eight) {
            final ANode[] all = getNeighbors(eight);
            if (flags == null)
                return all;
            final LinkedList<ANode> open = new LinkedList<ANode>();
            for (final ANode n : all) {
                if (!flags.blocked(n, this)) {
                    open.add(n);
                }
            }
            return open.toArray(new ANode[open.size()]);
        }

    }

}
