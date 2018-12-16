package entities.algorithms;

import entities.Coordinate;

import java.util.*;
import java.util.Map;

/**
 * KMeans is a class use to create clusters from a list of coordinates and a number of clusters
 * @author PLD-HEXA-301
 */
public class KMeans {
    /**
     * array that contains the coordinates of all the delivery points
     */
    private Coordinate[] coordinates;

    /**
     * array that contains the coordinates of cluster centers
     */
    private Coordinate[] clusterCenterCoordinates;

    /**
     * contains the number of clusters
     */
    private int clusterNb;

    /**
     * array that contains all the center of clusters
     */
    private int[] clusterCenters;

    /**
     * contains all the nodes belonging to a cluster
     */
    private ArrayList<ArrayList<Integer>> clusterNodes;

    /**
     * contains the previous version of clusterNodes
     */
    private ArrayList<ArrayList<Integer>> previousClusterNodes;

    /**
     * contains the distance between each delivery point and each cluster
     */
    private ArrayList<LinkedHashMap<Integer, Double>> distances;

    /**
     * Construct KMeans using the coordinates of the delivery points and the number of clusters
     * @param coordinates: array of the coordinates of the delivery points
     * @param clusterNb the number of clusters
     */
    public KMeans(Coordinate[] coordinates, int clusterNb) {
        this.coordinates = coordinates;
        this.clusterCenterCoordinates = new Coordinate[clusterNb];
        this.clusterNb = clusterNb;
    }

    /**
     * initialize the algorithm by choosing the first k delivery points as center of clusters
     */
    public void init() {
        this.clusterNodes = new ArrayList<>();
        this.distances = new ArrayList<>();
        this.previousClusterNodes = new ArrayList<>();
        clusterCenters = new int[clusterNb];

        for (int i = 0; i < clusterNb; i++) {
            clusterCenters[i] = i;
            clusterNodes.add(new ArrayList<>());
            clusterCenterCoordinates[i] = coordinates[i];
        }
    }

    /**
     * initialize the algorithm by choosing randomly k delivery points as center of clusters
     */
    public void randomInit() {
        int pointsNb = this.coordinates.length;
        int[] randomIntegers = new int[pointsNb];
        randomIntegers[0] = 0;
        Random r = new Random();
        for (int index = 1; index < pointsNb; index++) {
            int randomIndex = r.nextInt(index + 1);
            randomIntegers[index] = randomIntegers[randomIndex];
            randomIntegers[randomIndex] = index;
        }
        this.clusterCenters = Arrays.copyOfRange(randomIntegers, 0, this.clusterNb);
    }

    /**
     * create the k clusters
     * @return clusterNodes contains the index of each nodes of each cluster
     */
    public ArrayList<ArrayList<Integer>> findClusters() {
        init();
        for (int i = 0; i < 100; i++) {
            findClusterDistance();
            if (previousClusterNodes.equals(clusterNodes))
                return clusterNodes;
            copyClusters();
            updateCluster();
        }
        return previousClusterNodes;
    }

    /**
     * copy all clusters in a new structure to see if the clusters has changed
     */
    private void copyClusters() {
        previousClusterNodes.clear();
        for (int j = 0; j < clusterNb; j++) {
            previousClusterNodes.add(new ArrayList<>(clusterNodes.get(j)));
        }
    }

    /**
     * find the distance between each delivery point and each cluster
     */
    public void findClusterDistance() {
        for (int i = 0; i < clusterNb; i++) {
            distances.add(new LinkedHashMap<>());
        }

        for (int clusterIndex = 0; clusterIndex < this.clusterNb; clusterIndex++) {
            Map<Integer, Double> clusterDistances = new HashMap<>();
            for (int nodeIndex = 0; nodeIndex < this.coordinates.length; nodeIndex++) {
                double distance = getEuclideanDistance(
                        coordinates[nodeIndex], clusterCenterCoordinates[clusterIndex]);

                clusterDistances.put(nodeIndex, distance);
            }

            int finalClusterIndex = clusterIndex;
            clusterDistances.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue())
                    .forEachOrdered(x -> distances.get(finalClusterIndex).put(x.getKey(), x.getValue()));
        }

        assignNodesToClusters();
    }

    /**
     * finds for each delivery point the best cluster in order to have the same number of delivery
     * points in each cluster (+/- 1)
     */
    private void assignNodesToClusters() {
        while (true) {
            for (int clusterIndex = 0; clusterIndex < clusterNb; clusterIndex++) {
                if (distances.get(clusterIndex).isEmpty())
                    return;
                int key = distances.get(clusterIndex).keySet().iterator().next();
                for (int i = 0; i < clusterNb; i++) {
                    distances.get(i).remove(key);
                }
                clusterNodes.get(clusterIndex).add(key);
            }
        }
    }

    /**
     * calculates the center of gravity of each cluster and defines it as the new center of the cluster
     */
    public void updateCluster() {
        for (int clusterIndex = 0; clusterIndex < clusterNb; clusterIndex++) {
            int clusterSize = clusterNodes.get(clusterIndex).size();
            double longitude = 0;
            double latitude = 0;
            for (int nodeIndex = 0; nodeIndex < clusterSize; nodeIndex++) {
                longitude += coordinates[clusterNodes.get(clusterIndex).get(nodeIndex)].getLongitude();
                latitude += coordinates[clusterNodes.get(clusterIndex).get(nodeIndex)].getLatitude();
            }

            clusterNodes.get(clusterIndex).clear();
            clusterCenterCoordinates[clusterIndex] =
                    new Coordinate(longitude / clusterSize, latitude / clusterSize);
        }
    }

    /**
     * get the euclidean distance between two coordinates
     * @param coordinate1 first coordinate
     * @param coordinate2 second coordinate
     * @return distance between the two coordinates
     */
    private double getEuclideanDistance(Coordinate coordinate1, Coordinate coordinate2) {
        return
                Math.pow(coordinate1.getLongitude() - coordinate2.getLongitude(), 2) +
                        Math.pow(coordinate1.getLatitude() - coordinate2.getLatitude(), 2);
    }

    /**
     * get the centers of clusters
     * @return an array that contains the centers of clusters
     */
    public int[] getClusterCenters() {
        return this.clusterCenters;
    }

    /**
     * get all the nodes of all cluster
     * @return all nodes of each cluster
     */
    public ArrayList<ArrayList<Integer>> getClusterNodes() {
        return clusterNodes;
    }

    /**
     * get the distance between each node and each cluster center
     * @return the distance between each
     */
    public ArrayList<LinkedHashMap<Integer, Double>> getDistances() {
        return distances;
    }
}
