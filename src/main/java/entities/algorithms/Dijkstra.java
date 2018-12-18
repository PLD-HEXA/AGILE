package entities.algorithms;

import entities.Segment;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Implementation of the dijkstra algorithm
 *
 * @author PLD-HEXA-301
 */
public class Dijkstra {
    /**
     * Tab of the shortest distances from the starting point
     */
    private double[] distance;

    /**
     * Tab of predecessors representing the shortest path for the starting point to all the other points
     */
    private Integer[] predecessor;

    /**
     * Data structure representing the graph to explore
     */
    private List<List<Segment>> graph;

    /**
     * Constructor
     *
     * @param graph Graph to explore
     */
    public Dijkstra(List<List<Segment>> graph) {
        this.graph = graph;
        distance = new double[graph.size()];
        predecessor = new Integer[graph.size()];
    }

    /**
     * Getter for the distance tab.
     *
     * @return The distance tab
     */
    public double[] getDistance() {
        return distance;
    }

    /**
     * Getter for the predecessors tab
     *
     * @return The predecessors tab
     */
    public Integer[] getPredecessor() {
        return predecessor;
    }

    /**
     * Get the distance between two vertices in the graph
     *
     * @param s1 First vertice
     * @param s2 Second vertice
     * @return The distance between the two specified vertices in the graph
     */
    public double getDistance(Integer s1, Integer s2) {
        List<Segment> segments = graph.get(s1);
        for (Segment segment : segments) {
            if (segment.getDestIndex() == s2) {
                return segment.getLength();
            }
        }
        return Double.MAX_VALUE;
    }

    /**
     * Release procedure of the Dijkstra algorithm
     *
     * @param s1 First vertice of the edge to release
     * @param s2 Second vertice of the edge to release
     */
    public void release(Integer s1, Integer s2) {
        if (distance[s2] > distance[s1] + getDistance(s1, s2)) {
            distance[s2] = distance[s1] + getDistance(s1, s2);
            predecessor[s2] = s1;
        }
    }

    /**
     * Implementation of the Dijkstra algorithm
     *
     * @param s0      Starting point from which starts the exploration of the graph
     * @param targets Targets for which we need to get the shortest distance and path from the starting point.
     *                When parameter is set to null, the Dijkstra algorithm explore the whole graph.
     */
    public void executeDijkstra(Integer s0, List<Integer> targets) {
        // reinitialize distance and predecessors arrow to empty arrays
        distance = new double[graph.size()];
        predecessor = new Integer[graph.size()];

        int nbPointsToReach;
        // if targets is null, all the points of the graph have to be reached
        if (targets == null) {
            targets = new ArrayList<Integer>();
            nbPointsToReach = -1;
        } else {
            nbPointsToReach = targets.size();
        }

        int length = graph.size();
        // tab representing the black vertices
        boolean[] black = new boolean[length];

        // priority queue containing the grey vertices
        PriorityQueue<Integer> grey = new PriorityQueue(11, new Comparator<Integer>() {
            @Override
            public int compare(Integer s1, Integer s2) {
                if (distance[s1] > distance[s2]) {
                    return 1;
                } else if (distance[s1] < distance[s2]) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });

        // tab representing the white vertices
        boolean[] white = new boolean[length];
        for (int i = 0; i < length; i++) {
            distance[i] = Double.MAX_VALUE;
            predecessor[i] = null;
            white[i] = true;
            black[i] = false;
        }

        distance[s0] = 0;
        white[s0] = false;
        grey.offer(s0);

        Integer si;
        while (!grey.isEmpty()) {
            si = grey.poll();
            for (Segment segment : graph.get(si)) {
                if (!black[segment.getDestIndex()]) {
                    release(si, segment.getDestIndex());
                    if (white[segment.getDestIndex()]) {
                        white[segment.getDestIndex()] = false;
                        grey.offer(segment.getDestIndex());
                    }
                }
            }
            black[si] = true;

            if (targets.contains(si)) {
                nbPointsToReach--;
            }

            // if all the points to reach have been reached, break
            if (nbPointsToReach == 0) {
                break;
            }
        }
    }
}
