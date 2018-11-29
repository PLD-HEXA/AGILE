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
import java.util.Date;
import java.util.List;
import javafx.util.Pair;

/**
 *
 * @author HEXA 
 */
public class PathFinder {
    List<List<Segment>> graph;
    Coordinate[] coordinates;
    List<Pair<Integer, Integer>> deliveryPoints;
    Pair<Integer, String> warehouse;
    //int nbDeliveryMen;
    
    public PathFinder(){
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

    
    
    public List<Itinerary> findPathWithKmeans (Map map, int nbDeliveryMen){
        
        //Check that all the parameters are well set to find the itineraries
        this.graph = map.getGraph();
        this.coordinates = map.getCoordinates();
        this.deliveryPoints = map.getTabDeliveryPoints();
        this.warehouse = map.getWareHouse();
        
        if(graph == null || deliveryPoints == null || coordinates == null || nbDeliveryMen <1 || warehouse.getKey() <0 || nbDeliveryMen > deliveryPoints.size() ){
            return null;
        }
        
        //Contruct the list of targets : warehouse + deliveryPoints
        List<Integer> targets = new ArrayList<>();
        targets.add(warehouse.getKey());
        for(Pair<Integer, Integer> dp: deliveryPoints){
            targets.add(dp.getKey());
        }
        
        //Construct the list of durations the delivery man will have to stay at in each target
        List<Integer> times = new ArrayList<>();
        times.add(0);
        for(Pair<Integer, Integer> dp: deliveryPoints){
            times.add(dp.getValue());
        }
        
        
        //Construct the adjacencyMatrix
        double[][] adjMatrix = new double[deliveryPoints.size() +1][deliveryPoints.size() +1];
        
        //Fill the adjacency matrix with dijkstra algorithm
        Dijkstra dijkstra = new Dijkstra(this.graph);
        double[] distance;
        for(int i = 0; i< targets.size(); i++){
            dijkstra.executeDijkstra(targets.get(i), targets);
            distance = dijkstra.getDistance();
            for(int j = 0; j< targets.size(); j++){
                adjMatrix[i][j] = distance[targets.get(j)]*(3.6/15) + times.get(j); //+ ajouter la durée
            }
        }
        
        //k-means algorithm to find the n best clusters
        
        //TSP algorithm to find the best order
        
        //Pour chaque cluster de points faire:
            //Construire la matrice d'adjascence associée
            //Exécuter tsp
            
        GeneralTSP tsp = new GeneralTSP();
        int[] order =  tsp.getOrder(adjMatrix, nbDeliveryMen);
        
        List<List<Integer>> tempItineraries = new ArrayList<List<Integer>> ();
        for(int i =0; i< nbDeliveryMen; i++){
            tempItineraries.add(new ArrayList<>());
        }
        int j = 0;
        for(int i = 0; i< nbDeliveryMen; i++){
            while(j == 0 || order[j] != 0){
                tempItineraries.get(i).add(order[j]);
                j++;
            }
            tempItineraries.get(i).add(order[j]);  
            if(i != nbDeliveryMen-1){
                tempItineraries.get(i+1).add(order[j]);
            }
            j++;
            
        }
        
        //Create itineraries
        List<Itinerary> itineraries = new ArrayList<>();
        
        Date departureTime = null;
        DateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        try {
            // To get the date object from the string just called the 
            // parse method and pass the time string to it. This method 
            // throws ParseException if the time string is invalid. 
            // But remember as we don't pass the date information this 
            // date object will represent the 1st of january 1970.
            departureTime = sdf.parse(warehouse.getValue());            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        Date dTemp;
        for(List<Integer> li: tempItineraries){
            List<DeliveryPoint> generalPath = new ArrayList<>();
            
            dTemp = new Date(departureTime.getTime());
            //construct the general path
            for(Integer indexTarget: li){
                DeliveryPoint dp = new DeliveryPoint(coordinates[targets.get(indexTarget)], dTemp, new Date(dTemp.getTime() + times.get(indexTarget)*1000));
                dTemp = new Date(dTemp.getTime() + times.get(indexTarget)*1000);
                generalPath.add(dp);
            }
            //construct the detailed path
            List<Coordinate> detailedPath = new ArrayList<>();
            
            for(int i = 0; i< li.size()-1; i++){
                if(i==0){
                    detailedPath.addAll(getDetailedPath(targets.get(li.get(i)) , targets.get(li.get(i+1)) ));
                }
                else{
                    List<Coordinate> tempCoordinates = getDetailedPath(targets.get(li.get(i)) , targets.get(li.get(i+1)) );
                    tempCoordinates.remove(0);
                    detailedPath.addAll(tempCoordinates);
                }
            }
            itineraries.add(new Itinerary(generalPath, detailedPath));
        }
        
        
        for(int i = 0; i< order.length; i++){
            System.out.println(order[i]);
        }
        
        return itineraries;
    }

    
    
    
    public List<Itinerary> findPath (Map map, int nbDeliveryMen){
        
        //Check that all the parameters are well set to find the itineraries
        this.graph = map.getGraph();
        this.coordinates = map.getCoordinates();
        this.deliveryPoints = map.getTabDeliveryPoints();
        this.warehouse = map.getWareHouse();
        
        if(graph == null || deliveryPoints == null || coordinates == null || nbDeliveryMen <1 || warehouse.getKey() <0 || nbDeliveryMen > deliveryPoints.size() ){
            return null;
        }
        
        //Contruct the list of targets : warehouse + deliveryPoints
        List<Integer> targets = new ArrayList<>();
        targets.add(warehouse.getKey());
        for(Pair<Integer, Integer> dp: deliveryPoints){
            targets.add(dp.getKey());
        }
        
        //Construct the list of durations the delivery man will have to stay at in each target
        List<Integer> times = new ArrayList<>();
        times.add(0);
        for(Pair<Integer, Integer> dp: deliveryPoints){
            times.add(dp.getValue());
        }
        
        
        //Construct the adjacencyMatrix
        double[][] adjMatrix = new double[deliveryPoints.size() +1][deliveryPoints.size() +1];
        
        //Fill the adjacency matrix with dijkstra algorithm
        Dijkstra dijkstra = new Dijkstra(this.graph);
        double[] distance;
        for(int i = 0; i< targets.size(); i++){
            dijkstra.executeDijkstra(targets.get(i), targets);
            distance = dijkstra.getDistance();
            for(int j = 0; j< targets.size(); j++){
                adjMatrix[i][j] = distance[targets.get(j)]*(3.6/15) + times.get(j); //+ ajouter la durée
            }
        }
        
        //k-means algorithm to find the n best clusters
        
        //TSP algorithm to find the best order
        
        //Pour chaque cluster de points faire:
            //Construire la matrice d'adjascence associée
            //Exécuter tsp
            
        GeneralTSP tsp = new GeneralTSP();
        int[] order =  tsp.getOrder(adjMatrix, nbDeliveryMen);
        
        List<List<Integer>> tempItineraries = new ArrayList<List<Integer>> ();
        for(int i =0; i< nbDeliveryMen; i++){
            tempItineraries.add(new ArrayList<>());
        }
        int j = 0;
        for(int i = 0; i< nbDeliveryMen; i++){
            while(j == 0 || order[j] != 0){
                tempItineraries.get(i).add(order[j]);
                j++;
            }
            tempItineraries.get(i).add(order[j]);  
            if(i != nbDeliveryMen-1){
                tempItineraries.get(i+1).add(order[j]);
            }
            j++;
            
        }
        
        //Create itineraries
        List<Itinerary> itineraries = new ArrayList<>();
        
        Date departureTime = null;
        DateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        try {
            // To get the date object from the string just called the 
            // parse method and pass the time string to it. This method 
            // throws ParseException if the time string is invalid. 
            // But remember as we don't pass the date information this 
            // date object will represent the 1st of january 1970.
            departureTime = sdf.parse(warehouse.getValue());            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        Date dTemp;
        for(List<Integer> li: tempItineraries){
            List<DeliveryPoint> generalPath = new ArrayList<>();
            
            dTemp = new Date(departureTime.getTime());
            //construct the general path
            for(Integer indexTarget: li){
                DeliveryPoint dp = new DeliveryPoint(coordinates[targets.get(indexTarget)], dTemp, new Date(dTemp.getTime() + times.get(indexTarget)*1000));
                dTemp = new Date(dTemp.getTime() + times.get(indexTarget)*1000);
                generalPath.add(dp);
            }
            //construct the detailed path
            List<Coordinate> detailedPath = new ArrayList<>();
            
            for(int i = 0; i< li.size()-1; i++){
                if(i==0){
                    detailedPath.addAll(getDetailedPath(targets.get(li.get(i)) , targets.get(li.get(i+1)) ));
                }
                else{
                    List<Coordinate> tempCoordinates = getDetailedPath(targets.get(li.get(i)) , targets.get(li.get(i+1)) );
                    tempCoordinates.remove(0);
                    detailedPath.addAll(tempCoordinates);
                }
            }
            itineraries.add(new Itinerary(generalPath, detailedPath));
        }
        
        
        for(int i = 0; i< order.length; i++){
            System.out.println(order[i]);
        }
        
        return itineraries;
    }
    
    public List<Coordinate> getDetailedPath(Integer s0, Integer s1){
        List <Coordinate> res = new ArrayList<>();
        List <Integer> s1List = new ArrayList<>();
        s1List.add(s1);
        Dijkstra dijkstra = new Dijkstra(this.graph);
        Integer[] predecessors;
        dijkstra.executeDijkstra(s0, s1List);
        predecessors = dijkstra.getPredecessor();

        res.add(coordinates[s1]);
        Integer temp = predecessors[s1];
        while(temp != null){
            res.add(0,coordinates[temp]);
            temp = predecessors[temp];
        }    
        return res;
    }
    
}
