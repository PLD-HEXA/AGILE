/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.algorithms;

import entities.Coordinate;
import entities.Itinerary;
import entities.Segment;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author HEXA 
 */
public class PathFinder {
    List<List<Segment>> graph;
    Coordinate[] coordinates;
    int[] deliveryPoints;
    int indexWarehouse;
    int nbDeliveryMen;
    
    public PathFinder(){
        nbDeliveryMen = -1;
        indexWarehouse = -1;
        
    }

    public List<List<Segment>> getGraph() {
        return graph;
    }

    public Coordinate[] getCoordinates() {
        return coordinates;
    }

    public int getNbDeliveryMen() {
        return nbDeliveryMen;
    }


    public void setGraph(List<List<Segment>> graph) {
        this.graph = graph;
    }

    public void setCoordinates(Coordinate[] coordinates) {
        this.coordinates = coordinates;
    }

    public void setNbDeliveryMen(int nbDeliveryMen) {
        this.nbDeliveryMen = nbDeliveryMen;
    }

    public void setDeliveryPoints(int[] deliveryPoints) {
        this.deliveryPoints = deliveryPoints;
    }

    public void setIndexWarehouse(int indexWarehouse) {
        this.indexWarehouse = indexWarehouse;
    }

    
    
    
    public Itinerary[] findPath (){
        
        //Check that all the parameters are well set to find the itineraries
        if(graph == null || deliveryPoints == null || coordinates == null || nbDeliveryMen <1 || indexWarehouse <0 || nbDeliveryMen > deliveryPoints.length ){
            return null;
        }
        
        //Contruct the list of targets : warehouse + deliveryPoints
        List<Integer> targets = new ArrayList<>();
        targets.add(this.indexWarehouse);
        for(int i = 0; i< deliveryPoints.length; i++){
            targets.add(deliveryPoints[i]);
        }
        
        
        //Construct the adjacencyMatrix
        double[][] adjMatrix = new double[deliveryPoints.length +1][deliveryPoints.length +1];
        
        //Fill the adjacency matrix with dijkstra algorithm
        Dijkstra dijkstra = new Dijkstra(this.graph);
        double[] distance;
        for(int i = 0; i< targets.size(); i++){
            dijkstra.executeDijkstra(targets.get(i), targets);
            distance = dijkstra.getDistance();
            for(int j = 0; j< targets.size(); j++){
                adjMatrix[i][j] = distance[targets.get(j)]; //+ ajouter la durée
            }
        }
        
        //k-means algorithm to find the n best clusters
        
        //TSP algorithm to find the best order
        
        //Pour chaque cluster de points faire:
            //Construire la matrice d'adjascence associée
            //Exécuter tsp
            
        TSP tsp = new TSP();
        int[] order =  tsp.getOrder(adjMatrix);
        
        for(int i = 0; i< order.length; i++){
            System.out.println(order[i]);
        }
        
        return null;
    }
    
}
