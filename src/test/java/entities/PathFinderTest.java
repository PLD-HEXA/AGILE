/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import entities.algorithms.PathFinder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.util.Pair;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author youss
 */
public class PathFinderTest {
    /**
     * Test the findPath results of the pathFinder
     */
    @Test
    public void testPathFinder() {
        //Contruct the map
        entities.Map map = new entities.Map();
        
        //Contruct the graph
        Segment s01 = new Segment(1, "nom de rue", 3);
        Segment s02 = new Segment(2, "nom de rue", 5 );
        
        Segment s12 = new Segment(2, "nom de rue", 1);
        Segment s13 = new Segment(3, "nom de rue", 6);
        
        
        Segment s20 = new Segment(0, "nom de rue", 1);
        Segment s21 = new Segment(1, "nom de rue", 1);
        Segment s23 = new Segment(3, "nom de rue", 3);
        Segment s24 = new Segment(4, "nom de rue", 6);
        
        Segment s34 = new Segment(4, "nom de rue", 2);
        
        Segment s42 = new Segment(2, "nom de rue", 1);
        Segment s43 = new Segment(3, "nom de rue", 7);
        
        
        
        List<List<Segment>> graph = new ArrayList<>();
        for(int i = 0; i< 5; i++){
            graph.add(new ArrayList<Segment>());
        }
        
        graph.get(0).add(s01);
        graph.get(0).add(s02);
        
        graph.get(1).add(s12);
        graph.get(1).add(s13);
        
        graph.get(2).add(s20);
        graph.get(2).add(s21);
        graph.get(2).add(s23);
        graph.get(2).add(s24);
                        
        graph.get(3).add(s34);
        
        graph.get(4).add(s42);
        graph.get(4).add(s43);
        
        List<Pair<Integer,Integer>> deliveryPoints = new ArrayList();
        deliveryPoints.add(new Pair<>(1,1));
        deliveryPoints.add(new Pair<>(2,1));
        deliveryPoints.add(new Pair<>(3,1));
        
        Pair<Integer, String> warehouse = new Pair<>(0, "8:00:00");
        
        
        Coordinate[] coordinates = new Coordinate[5];
        coordinates[0] = new Coordinate(0.0,0.0);
        coordinates[1] = new Coordinate(1.0,1.0);
        coordinates[2] = new Coordinate(2.0,2.0);
        coordinates[3] = new Coordinate(3.0,3.0);
        coordinates[4] = new Coordinate(4.0,4.0);
        
        map.setGraph(graph);
        map.setCoordinates(coordinates);
        map.setTabDeliveryPoints(deliveryPoints);
        map.setWareHouse(warehouse);
        
        PathFinder pf = new PathFinder();
        List<Itinerary> itineraries = pf.findPath(map, 2);
        
        List <Itinerary> expectedRes = new ArrayList<>();
        
        //Contruct the first expected itinerary
        DeliveryPoint dl0 = new DeliveryPoint(coordinates[0], new Date(25200000), new Date(25200000));
        DeliveryPoint dl1 = new DeliveryPoint(coordinates[2], new Date(25201000), new Date(25202000));
        DeliveryPoint dl2 = new DeliveryPoint(coordinates[3], new Date(25204000), new Date(25205000));
        DeliveryPoint dl3 = new DeliveryPoint(coordinates[0], new Date(25206000), new Date(25206000));
        List<DeliveryPoint> generalPath1 = new ArrayList<>();
        generalPath1.add(dl0);
        generalPath1.add(dl1);
        generalPath1.add(dl2);
        generalPath1.add(dl3);
        
        List<Coordinate> detailedPath1 = new ArrayList<>();
        detailedPath1.add(coordinates[0]);
        detailedPath1.add(coordinates[1]);
        detailedPath1.add(coordinates[2]);
        detailedPath1.add(coordinates[3]);
        detailedPath1.add(coordinates[4]);
        detailedPath1.add(coordinates[2]);
        detailedPath1.add(coordinates[0]);
        
        Itinerary itinerary1 = new Itinerary(generalPath1, detailedPath1);
        
        
        //Contruct the second expected itinerary
        DeliveryPoint dl4 = new DeliveryPoint(coordinates[0], new Date(25200000), new Date(25200000));
        DeliveryPoint dl5 = new DeliveryPoint(coordinates[1], new Date(25201000), new Date(25202000));
        DeliveryPoint dl6 = new DeliveryPoint(coordinates[0], new Date(25203000), new Date(25203000));
        List<DeliveryPoint> generalPath2 = new ArrayList<>();
        generalPath2.add(dl4);
        generalPath2.add(dl5);
        generalPath2.add(dl6);
        
        List<Coordinate> detailedPath2 = new ArrayList<>();
        detailedPath2.add(coordinates[0]);
        detailedPath2.add(coordinates[1]);
        detailedPath2.add(coordinates[2]);
        detailedPath2.add(coordinates[0]);
        
        Itinerary itinerary2 = new Itinerary(generalPath2, detailedPath2);
        
        
        expectedRes.add(itinerary1);
        expectedRes.add(itinerary2);
        
        for(int i = 0; i< expectedRes.size(); i++){
            for(int j = 0; j< expectedRes.get(i).getGeneralPath().size(); j++){
                assertEquals(expectedRes.get(i).getGeneralPath().get(j).getCoordinate() , itineraries.get(i).getGeneralPath().get(j).getCoordinate());             
            }

            
            for(int j = 0; j< expectedRes.get(i).getDetailedPath().size(); j++){
                assertEquals(expectedRes.get(i).getDetailedPath().get(j) , itineraries.get(i).getDetailedPath().get(j));
            }
        }   
    }
    
    /**
     * Test the findPath results of the pathFinder
     */
    @Test
    public void testFindAdditionalPath() {
        //Contruct the map
        entities.Map map = new entities.Map();
        
        //Contruct the graph
        Segment s01 = new Segment(1, "nom de rue", 3);
        Segment s02 = new Segment(2, "nom de rue", 5 );
        
        Segment s12 = new Segment(2, "nom de rue", 1);
        Segment s13 = new Segment(3, "nom de rue", 6);
        
        
        Segment s20 = new Segment(0, "nom de rue", 1);
        Segment s21 = new Segment(1, "nom de rue", 1);
        Segment s23 = new Segment(3, "nom de rue", 3);
        Segment s24 = new Segment(4, "nom de rue", 6);
        
        Segment s34 = new Segment(4, "nom de rue", 2);
        
        Segment s42 = new Segment(2, "nom de rue", 1);
        Segment s43 = new Segment(3, "nom de rue", 7);
        
        
        
        List<List<Segment>> graph = new ArrayList<>();
        for(int i = 0; i< 5; i++){
            graph.add(new ArrayList<Segment>());
        }
        
        graph.get(0).add(s01);
        graph.get(0).add(s02);
        
        graph.get(1).add(s12);
        graph.get(1).add(s13);
        
        graph.get(2).add(s20);
        graph.get(2).add(s21);
        graph.get(2).add(s23);
        graph.get(2).add(s24);
                        
        graph.get(3).add(s34);
        
        graph.get(4).add(s42);
        graph.get(4).add(s43);
        
        List<Pair<Integer,Integer>> deliveryPoints = new ArrayList();
        deliveryPoints.add(new Pair<>(1,1));
        deliveryPoints.add(new Pair<>(2,1));
        deliveryPoints.add(new Pair<>(3,1));
        
        Pair<Integer, String> warehouse = new Pair<>(0, "8:00:00");
        
        
        Coordinate[] coordinates = new Coordinate[5];
        coordinates[0] = new Coordinate(0.0,0.0);
        coordinates[1] = new Coordinate(1.0,1.0);
        coordinates[2] = new Coordinate(2.0,2.0);
        coordinates[3] = new Coordinate(3.0,3.0);
        coordinates[4] = new Coordinate(4.0,4.0);
        
        map.setGraph(graph);
        map.setCoordinates(coordinates);
        map.setTabDeliveryPoints(deliveryPoints);
        map.setWareHouse(warehouse);
        
        PathFinder pf = new PathFinder();
        List<Itinerary> itineraries = pf.findPath(map, 2);
        deliveryPoints.add(new Pair(4,1));
        map.setTabDeliveryPoints(deliveryPoints);
        pf.findAdditionalPath(map, itineraries, 1, false);
        
        //Contruct the first expected additional itinerary
        DeliveryPoint dl0 = new DeliveryPoint(coordinates[0], new Date(25203000), new Date(25503000));
        DeliveryPoint dl1 = new DeliveryPoint(coordinates[4], new Date(25506000), new Date(25507000));
        DeliveryPoint dl2 = new DeliveryPoint(coordinates[0], new Date(25507000), new Date(25507000));

        List<DeliveryPoint> generalPath1 = new ArrayList<>();
        generalPath1.add(dl0);
        generalPath1.add(dl1);
        generalPath1.add(dl2);
        
        List<Coordinate> detailedPath1 = new ArrayList<>();
        detailedPath1.add(coordinates[0]);
        detailedPath1.add(coordinates[1]);
        detailedPath1.add(coordinates[2]);
        detailedPath1.add(coordinates[3]);
        detailedPath1.add(coordinates[4]);
        detailedPath1.add(coordinates[2]);
        detailedPath1.add(coordinates[0]);
     
        Itinerary itinerary1 = new Itinerary(generalPath1, detailedPath1);    
        
        for(int j = 0; j< itineraries.get(2).getGeneralPath().size(); j++){
            assertEquals(itineraries.get(2).getGeneralPath().get(j).getCoordinate() , itinerary1.getGeneralPath().get(j).getCoordinate());
        }

        for(int j = 0; j< itineraries.get(2).getDetailedPath().size(); j++){
            assertEquals(itineraries.get(2).getDetailedPath().get(j) , itinerary1.getDetailedPath().get(j));
        }

    }
    
}
