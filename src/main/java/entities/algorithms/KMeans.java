package entities.algorithms;

import entities.Coordinate;

import java.util.*;
import java.util.Map;

public class KMeans {
    private Coordinate[] coordinates;

    private Coordinate[] clusterCenterCoordinates;

    private int clusterNb;

    private int[] clusterCenters;

    /**
     * clusterNodes: contains all the nodes belonging to a cluster
     */
    private ArrayList<ArrayList<Integer>> clusterNodes;

    private ArrayList<ArrayList<Integer>> previousClusterNodes;

    private ArrayList<Map<Double, Integer>> clusters;

    private ArrayList<LinkedHashMap<Integer, Double>> distances;

    public KMeans(Coordinate[] coordinates, int clusterNb) {
        this.coordinates = coordinates;
        this.clusterCenterCoordinates = new Coordinate[clusterNb];
        this.clusterNb = clusterNb;
    }

    public void init() {
        this.clusterNodes = new ArrayList<>();
        this.clusters = new ArrayList<>();
        this.distances = new ArrayList<>();
        this.previousClusterNodes = new ArrayList<>();
        clusterCenters = new int[clusterNb];

        for (int i = 0; i < clusterNb; i++) {
            clusterCenters[i] = i;
            clusterNodes.add(new ArrayList<>());
            clusterCenterCoordinates[i] = coordinates[i];
        }
    }

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

    public ArrayList<ArrayList<Integer>> findClusters() {
        init();
        for (int i = 0; i < 100; i++) {
            findClusterDistance();
            if (previousClusterNodes.equals(clusterNodes))
                break;
            copyClusters();
            updateCluster();
        }
        return clusterNodes;
    }

    public void copyClusters() {
        previousClusterNodes.clear();
        for (int j = 0; j < clusterNb; j++) {
            previousClusterNodes.add(new ArrayList<>(clusterNodes.get(j)));
        }
    }

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

    private double getEuclideanDistance(Coordinate coordinate1, Coordinate coordinate2) {
        return
                Math.pow(coordinate1.getLongitude() - coordinate2.getLongitude(), 2) +
                        Math.pow(coordinate1.getLatitude() - coordinate2.getLatitude(), 2);
    }

    public void setClusters(ArrayList<Map<Double, Integer>> clusters) {
        this.clusters = clusters;
    }

    public void setCoordinates(Coordinate[] coordinates) {
        this.coordinates = coordinates;
    }

    public Coordinate[] getCoordinates() {
        return coordinates;
    }

    public Coordinate getCoordinate(int index) {
        return this.coordinates[index];
    }

    public int getClusterNb() {
        return clusterNb;
    }

    public void setClusterNb(int clusterNb) {
        this.clusterNb = clusterNb;
    }

    public int[] getClusterCenters() {
        return this.clusterCenters;
    }

    public Coordinate[] getClusterCenterCoordinates() {
        return clusterCenterCoordinates;
    }

    public void setClusterCenterCoordinates(Coordinate[] clusterCenterCoordinates) {
        this.clusterCenterCoordinates = clusterCenterCoordinates;
    }

    public ArrayList<ArrayList<Integer>> getClusterNodes() {
        return clusterNodes;
    }

    public void setClusterNodes(ArrayList<ArrayList<Integer>> clusterNodes) {
        this.clusterNodes = clusterNodes;
    }

    public ArrayList<ArrayList<Integer>> getPreviousClusterNodes() {
        return previousClusterNodes;
    }

    public void setPreviousClusterNodes(ArrayList<ArrayList<Integer>> previousClusterNodes) {
        this.previousClusterNodes = previousClusterNodes;
    }

    public ArrayList<Map<Double, Integer>> getClusters() {
        return clusters;
    }

    public void setClusterCenters(int[] clusterCenters) {
        this.clusterCenters = clusterCenters;
    }

    public ArrayList<LinkedHashMap<Integer, Double>> getDistances() {
        return distances;
    }

    public void setDistances(ArrayList<LinkedHashMap<Integer, Double>> distances) {
        this.distances = distances;
    }
}
