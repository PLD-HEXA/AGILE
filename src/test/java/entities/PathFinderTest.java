/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import com.fasterxml.jackson.databind.JsonMappingException;
import entities.algorithms.PathFinder;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

/**
 *
 * @author youss
 */
public class PathFinderTest {
    /**
     * Test of toString method, of class Coordinate.
     */
    @Test
    public void testPathFinder() {
        entities.Map map = new entities.Map();
        
        Segment s01 = new Segment(1, "rue du Levant", 3);
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
        deliveryPoints.add(new Pair<>(4,1));
        
        Pair<Integer, String> warehouse = new Pair<>(0, "8:0:0");
        
        
        
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
        List<Itinerary> itineraries = pf.findPathTSP(map, 2);
     
    }
}
