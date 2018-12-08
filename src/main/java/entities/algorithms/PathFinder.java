/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.algorithms;

import entities.Coordinate;
import entities.DeliveryPoint;
import entities.Itinerary;
import entities.Map;
import entities.Segment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javafx.util.Pair;

/**
 * @author HEXA
 */
public class PathFinder {
    List<List<Segment>> graph;
    Coordinate[] coordinates;
    List<Pair<Integer, Integer>> deliveryPoints;
    Pair<Integer, String> warehouse;
    private double[][] adjMatrix;
    private List<Integer> targets;
    private List<Integer> times;
    private int speed = 15;
    //int nbDeliveryMen;

    public PathFinder() {
        //nbDeliveryMen = -1;
        //indexWarehouse = -1;

    }

    public List<List<Segment>> getGraph() {
        return graph;
    }

    public Coordinate[] getCoordinates() {
        return coordinates;
    }

    /*public int getNbDeliveryMen() {
        return nbDeliveryMen;
    }*/


    public void setGraph(List<List<Segment>> graph) {
        this.graph = graph;
    }

    public void setCoordinates(Coordinate[] coordinates) {
        this.coordinates = coordinates;
    }

    /*public void setNbDeliveryMen(int nbDeliveryMen) {
        this.nbDeliveryMen = nbDeliveryMen;
    }*/

    public void setDeliveryPoints(List<Pair<Integer, Integer>> deliveryPoints) {
        this.deliveryPoints = deliveryPoints;
    }


    private void buildTargets() {
        this.targets = new ArrayList<>();
        this.targets.add(warehouse.getKey());
        for (Pair<Integer, Integer> dp : deliveryPoints) {
            this.targets.add(dp.getKey());
        }
    }

    private void buildTimes() {
        times = new ArrayList<>();
        times.add(0);
        for (Pair<Integer, Integer> dp : deliveryPoints) {
            times.add(dp.getValue());
        }
    }


    private void buildAdjMatrix() {
        this.adjMatrix = new double[deliveryPoints.size() + 1][deliveryPoints.size() + 1];

        //Fill the adjacency matrix with dijkstra algorithm
        Dijkstra dijkstra = new Dijkstra(this.graph);
        double[] distance;
        for (int i = 0; i < this.targets.size(); i++) {
            dijkstra.executeDijkstra(this.targets.get(i), this.targets);
            distance = dijkstra.getDistance();
            for (int j = 0; j < this.targets.size(); j++) {
                this.adjMatrix[i][j] = distance[this.targets.get(j)] * (3.6 / 15) + times.get(j);
            }
        }
    }

    private Date getDepartureTime() {
        Date departureTime = null;
        DateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        try {
            departureTime = sdf.parse(warehouse.getValue());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return departureTime;
    }

    public List<Coordinate> getDetailedPath(Integer s0, Integer s1) {
        List<Coordinate> res = new ArrayList<>();
        List<Integer> s1List = new ArrayList<>();
        s1List.add(s1);
        Dijkstra dijkstra = new Dijkstra(this.graph);
        Integer[] predecessors;
        dijkstra.executeDijkstra(s0, s1List);
        predecessors = dijkstra.getPredecessor();

        res.add(coordinates[s1]);
        Integer temp = predecessors[s1];
        while (temp != null) {
            res.add(0, coordinates[temp]);
            temp = predecessors[temp];
        }
        return res;
    }

    private Coordinate[] buildCoordinates() {
        Coordinate[] deliveryCoordinates = new Coordinate[deliveryPoints.size()];
        for (int i = 0; i < deliveryPoints.size(); i++) {
            deliveryCoordinates[i] = coordinates[deliveryPoints.get(i).getKey()];
        }

        return deliveryCoordinates;
    }

    private double[][] extractAdjMatrix(ArrayList<Integer> clusterPoints) {
        int matSize = clusterPoints.size();
        double[][] smallMatrix = new double[matSize][matSize];

        for (int i = 0; i < matSize; i++) {
            for (int j = 0; j < matSize; j++) {
                smallMatrix[i][j] = adjMatrix[clusterPoints.get(i)][clusterPoints.get(j)];
            }
        }

        return smallMatrix;
    }

    private Itinerary constructItinerary(Date wareHouseDepartureTime, ArrayList<Integer> deliveryIndexes) {
        List<DeliveryPoint> generalPath = new ArrayList<>();
        List<Coordinate> detailedPath = new ArrayList<>();

        Date departureTime = new Date(wareHouseDepartureTime.getTime());
        int interDuration = 0;

        for (int i = 0; i < deliveryIndexes.size(); i++) {
            int deliveryIndex = deliveryIndexes.get(i);
            int stopDuration = times.get(deliveryIndex) * 1000;

            Coordinate coordinate = coordinates[targets.get(deliveryIndex)];
            Date arrivalTime = new Date(departureTime.getTime() + interDuration);
            departureTime = new Date(arrivalTime.getTime() + stopDuration);

            DeliveryPoint deliveryPoint = new DeliveryPoint(coordinate, arrivalTime, departureTime);
            generalPath.add(deliveryPoint);

            if (i < deliveryIndexes.size() - 1) {
                int nextDeliveryIndex = deliveryIndexes.get(i + 1);
                int delivery = targets.get(deliveryIndex);
                int nextDelivery = targets.get(nextDeliveryIndex);
                interDuration = (int) (1000 * adjMatrix[deliveryIndex][nextDeliveryIndex] - times.get(nextDeliveryIndex));

                if (i == 0)
                    detailedPath.addAll(getDetailedPath(delivery, nextDelivery));
                else {
                    List<Coordinate> tempCoordinates = getDetailedPath(delivery, nextDelivery);
                    tempCoordinates.remove(0);
                    detailedPath.addAll(tempCoordinates);
                }
            }
        }

        return new Itinerary(generalPath, detailedPath);
    }

    private ArrayList<Integer> convertTSP(ArrayList<Integer> converter, int[] listToConvert) {
        ArrayList<Integer> convertedList = new ArrayList<>();
        for (int itemToConvert : listToConvert) {
            convertedList.add(converter.get(itemToConvert));
        }

        return convertedList;
    }

    //Liste d'itinéraires !!!
    public List<Itinerary> findPathTSP(Map map, int nbDeliveryMen) {

        //Check that all the parameters are well set to find the itineraries
        this.graph = map.getGraph();
        this.coordinates = map.getCoordinates();
        this.deliveryPoints = map.getTabDeliveryPoints();
        this.warehouse = map.getWareHouse();

        if (graph == null || deliveryPoints == null || coordinates == null || nbDeliveryMen < 1 || warehouse.getKey() < 0 || nbDeliveryMen > deliveryPoints.size()) {
            return null;
        }

        //Construct the list of targets : warehouse + deliveryPoints
        this.buildTargets();

        //Construct the list of durations the delivery man will have to stay at in each target
        this.buildTimes();


        //Construct the adjacencyMatrix
        this.buildAdjMatrix();

        GeneralTSP tsp = new GeneralTSP();
        int[] order = tsp.getOrder(adjMatrix, nbDeliveryMen);

        List<List<Integer>> tempItineraries = new ArrayList<>();
        for (int i = 0; i < nbDeliveryMen; i++) {
            tempItineraries.add(new ArrayList<>());
        }
        int j = 0;
        for (int i = 0; i < nbDeliveryMen; i++) {
            while (j == 0 || order[j] != 0) {
                tempItineraries.get(i).add(order[j]);
                j++;
            }
            tempItineraries.get(i).add(order[j]);
            if (i != nbDeliveryMen - 1) {
                tempItineraries.get(i + 1).add(order[j]);
            }
            j++;

        }

        //Create itineraries
        List<Itinerary> itineraries = new ArrayList<>();

        Date departureTime = getDepartureTime();

        Date dTemp;
        for (List<Integer> li : tempItineraries) {
            List<DeliveryPoint> generalPath = new ArrayList<>();

            dTemp = new Date(departureTime.getTime());
            //construct the general path
            int cmpt = 1;
            for (Integer indexTarget : li) {
                DeliveryPoint dp = new DeliveryPoint(coordinates[targets.get(indexTarget)], dTemp, new Date(dTemp.getTime() + times.get(indexTarget) * 1000));
                if (cmpt < li.size()) {
                    dTemp = new Date(dTemp.getTime() + times.get(indexTarget) * 1000 + Math.round(adjMatrix[indexTarget][li.get(cmpt)]) * 1000 - times.get(li.get(cmpt)));
                }

                cmpt++;
                generalPath.add(dp);
            }
            //construct the detailed path
            List<Coordinate> detailedPath = new ArrayList<>();

            for (int i = 0; i < li.size() - 1; i++) {
                if (i == 0) {
                    detailedPath.addAll(getDetailedPath(targets.get(li.get(i)), targets.get(li.get(i + 1))));
                } else {
                    List<Coordinate> tempCoordinates = getDetailedPath(targets.get(li.get(i)), targets.get(li.get(i + 1)));
                    tempCoordinates.remove(0);
                    detailedPath.addAll(tempCoordinates);
                }
            }
            itineraries.add(new Itinerary(generalPath, detailedPath));
        }
        return itineraries;
    }

    public List<Itinerary> findPathClustering(Map map, int nbDeliveryMen) {
        long startTime = System.currentTimeMillis();

        // get needed data from the map
        this.graph = map.getGraph();
        this.coordinates = map.getCoordinates();
        this.deliveryPoints = map.getTabDeliveryPoints();
        this.warehouse = map.getWareHouse();

        buildTargets();
        buildTimes();
        buildAdjMatrix();

        Coordinate[] deliveryCoordinates = buildCoordinates();

        KMeans kMeans = new KMeans(deliveryCoordinates, nbDeliveryMen);

        ArrayList<ArrayList<Integer>> tempItineraries1 = kMeans.findClusters();
        ArrayList<ArrayList<Integer>> tempItineraries = new ArrayList<>();
        List<Itinerary> itineraries = new ArrayList<>();

        Date departureTime = getDepartureTime();

        for (int i = 0; i < tempItineraries1.size(); i++) {
            // insert the warehouse in the first place
            tempItineraries.add(new ArrayList<>());
            tempItineraries.get(i).add(0);
            for (int j = 0; j < tempItineraries1.get(i).size(); j++) {
                tempItineraries.get(i).add(tempItineraries1.get(i).get(j) + 1);
            }
            Collections.sort(tempItineraries.get(i));

            double[][] smallMatrix = extractAdjMatrix(tempItineraries.get(i));
            int[] path = new TSP().getOrder(smallMatrix);
            ArrayList<Integer> coordinatesItinerary = convertTSP(tempItineraries.get(i), path);
            itineraries.add(constructItinerary(departureTime, coordinatesItinerary));
        }

        long stopTime = System.currentTimeMillis();
        System.out.println("duration: " + (stopTime - startTime) + "ms");

        return itineraries;
    }

    public boolean findAdditionalPath(List<Itinerary> itineraries, Map map, int numberPointAdd) {
        return false;
    }
}