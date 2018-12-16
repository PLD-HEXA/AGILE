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
 * Class using the KMeans, Dijkstra and TSP algorithms to find the best path
 * @author PLD-HEXA-301
 */
public class PathFinder {
    /**
     * Graph representing the map
     */
    private List<List<Segment>> graph;
    /**
     * Array for the mapping between an index and his corresponding coordinate
     */
    private Coordinate[] coordinates;
    /**
     * List of delivery points the deliverers have to pass by. The key of the pair represents the index of the 
     * delivery point and the value represents the duration of the delivery.
     */
    private List<Pair<Integer, Integer>> deliveryPoints;
    /**
     * Represents the warehouse. The key of the pair represents the index of the warehouse and the value represents
     * the time where the warehouse is leaved.
     */
    private Pair<Integer, String> warehouse;
    /**
     * Represents the graph formed by all the deliveryPoints and the warehouse.
     */
    private double[][] adjMatrix;
    /**
     * List of delivery points + warehouse
     */
    private List<Integer> targets;
    /**
     * List of durations of each delivery point and the warehouse
     */
    private List<Integer> times;
    /**
     * speed of the deliverers (km/h)
     */
    private final int speed = 15;

    /**
     * Default constructor
     */
    public PathFinder() {
    }

    /**
     * Build the targets list from the delivery points list and the warehouse pair.
     */
    private void buildTargets() {
        this.targets = new ArrayList<>();
        this.targets.add(warehouse.getKey());
        for (Pair<Integer, Integer> dp : deliveryPoints) {
            this.targets.add(dp.getKey());
        }
    }

    /**
     * Build the times list from the delivery points list and the warehouse pair.
     */
    private void buildTimes() {
        times = new ArrayList<>();
        times.add(0);
        for (Pair<Integer, Integer> dp : deliveryPoints) {
            times.add(dp.getValue());
        }
    }

    /**
     * Build the graph adjMatrix using the Dijkstra algorithm
     */
    private void buildAdjMatrix() {
        this.adjMatrix = new double[deliveryPoints.size() + 1][deliveryPoints.size() + 1];

        //Fill the adjacency matrix with dijkstra algorithm
        Dijkstra dijkstra = new Dijkstra(this.graph);
        double[] distance;
        for (int i = 0; i < this.targets.size(); i++) {
            dijkstra.executeDijkstra(this.targets.get(i), this.targets);
            distance = dijkstra.getDistance();
            for (int j = 0; j < this.targets.size(); j++) {
                this.adjMatrix[i][j] = distance[this.targets.get(j)] * (3.6 / speed) + times.get(j);
            }
        }
    }

    /**
     * Getter for the departureTime of the warhouse
     * @return departure time of the warehouse
     */
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

    /**
     * Retrieve the shortest path from a vertice to another using the Dijkstra algorithm
     * @param s0 First vertice
     * @param s1 Second vertice
     * @return List of coordinates representing the shortest path
     */
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

    /**
     * Create a tab of coordinates corresponding to the list of deliveryPoints
     * @return Tab of coordinates
     */
    private Coordinate[] buildCoordinates() {
        Coordinate[] deliveryCoordinates = new Coordinate[deliveryPoints.size()];
        for (int i = 0; i < deliveryPoints.size(); i++) {
            deliveryCoordinates[i] = coordinates[deliveryPoints.get(i).getKey()];
        }

        return deliveryCoordinates;
    }

    /**
     * Extract the "adjMatrix" ofthe specified cluster
     * @param clusterPoints Cluster of points
     * @return The extractes "adjMatrix"
     */
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

    /**
     * Contruct an itinerary from a list of delivery points and a warehouse departure time.
     * @param wareHouseDepartureTime Departure time of the warehouse
     * @param deliveryIndexes Indexes of the deliveryPoints 
     * @return The itinerary.
     */
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

    /**
     * 
     * @param converter
     * @param listToConvert
     * @return 
     */
    private ArrayList<Integer> convertTSP(ArrayList<Integer> converter, int[] listToConvert) {
        ArrayList<Integer> convertedList = new ArrayList<>();
        for (int itemToConvert : listToConvert) {
            convertedList.add(converter.get(itemToConvert));
        }

        return convertedList;
    }

    /**
     * Find the list of itineraries for the specified map using KMeans and TSP algorithms
     * @param map Map in which the itineraries have to be retrieved
     * @param nbDeliveryMen Number of delivery men
     * @return List of itineraries
     */
    public List<Itinerary> findPath(Map map, int nbDeliveryMen) {
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
    
    /**
     * Calculate an additional itineray with new points. 
     * @param map Map where the new itinerary have to be retrieved
     * @param itineraries List of itineraries to which add the new itinerary
     * @param nbNewPoints Number of new points in the new itinerary
     * @param undo State of the undo
     * @return True when the addition of a new itinerary is possible (the itinerary ends before 18:00pm); false otherwise
     */
    public boolean findAdditionalPath(Map map, List<Itinerary> itineraries, int nbNewPoints, boolean undo) {    
        if (nbNewPoints == 0) {
            itineraries.remove(itineraries.size() -1);
            return true;
        }
        else if ((undo && nbNewPoints >= 1) || (!undo && nbNewPoints > 1)) {
            itineraries.remove(itineraries.size() -1);
        } 
        
        //Find the earliest arrival time
        Date tempArrivalTime;
        Date earlierArrivalTime = itineraries.get(0).getGeneralPath().get(itineraries.get(0).getGeneralPath().size() -1).getDepartureTime();
        for(Itinerary itinerary: itineraries){
            tempArrivalTime = itinerary.getGeneralPath().get(itinerary.getGeneralPath().size()-1).getDepartureTime(); 
            if(tempArrivalTime.getHours() >=18){
                return false;
            }
            if(earlierArrivalTime.getTime() > tempArrivalTime.getTime()){
                earlierArrivalTime = tempArrivalTime;
            }
        }
        
        //Calculate the new itinerary
        List<Pair<Integer,Integer>> tabDeliveryPointsTemp= new ArrayList<>();
        List<Pair<Integer,Integer>> tabDeliveryPoints= map.getTabDeliveryPoints();
        int sizeTabDeliveryPoints = tabDeliveryPoints.size();
        for(int i = 0; i< nbNewPoints; i++){
            tabDeliveryPointsTemp.add(new Pair(tabDeliveryPoints.get(sizeTabDeliveryPoints- 1 -i).getKey() , tabDeliveryPoints.get(sizeTabDeliveryPoints- 1 -i).getValue()));
        }
        map.setTabDeliveryPoints(tabDeliveryPointsTemp);
        
       
        Pair<Integer, String> warehouse = map.getWareHouse();
        String startingTime = map.getWareHouse().getValue();
        
        long startingTimeTempNanoSeconds = earlierArrivalTime.getTime()+1000*5*60;
        DateFormat df = new SimpleDateFormat("hh:mm:ss");
        String startingTimeTemp = df.format(startingTimeTempNanoSeconds);
        
        map.setWareHouse(new Pair(map.getWareHouse().getKey(), startingTimeTemp));
        
        Itinerary additionalItinerary = this.findPath(map, 1).get(0);
        
        //Set the map as it was before
        map.setTabDeliveryPoints(tabDeliveryPoints);
        map.setWareHouse(warehouse);
        
        //Check that the new itinerary do not go past 18:00pm
        if(additionalItinerary.getGeneralPath().get(nbNewPoints+1).getDepartureTime().getHours()>=18 || 
           additionalItinerary.getGeneralPath().get(nbNewPoints+1).getDepartureTime().getDate() != earlierArrivalTime.getDate()){
            return false;          
        }
        //Add the new itinerary to the list of itineraries
        else{
            additionalItinerary.getGeneralPath().get(0).setArrivalTime(new Date(earlierArrivalTime.getTime()));
            itineraries.add(additionalItinerary);
            return true;
        }
    }
}