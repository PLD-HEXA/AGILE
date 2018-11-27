/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import entities.algorithms.Dijkstra;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 *
 * @author youss
 */
public class DijkstraTest {
    
    /**
     * Test the executeDijkstra method that finds the shortest path from a starting point to all the targets specified (including the
     * starting point).
     */
    @Test
    public void testExecuteDijkstra() {
        Segment s01 = new Segment(1, "rue du Levant", 3);
        Segment s02 = new Segment(2, "nom de rue", 5 );
        
        Segment s12 = new Segment(2, "nom de rue", 1);
        Segment s13 = new Segment(3, "nom de rue", 6);
        
        Segment s21 = new Segment(1, "nom de rue", 1);
        Segment s23 = new Segment(3, "nom de rue", 3);
        Segment s24 = new Segment(4, "nom de rue", 6);
        
        Segment s34 = new Segment(4, "nom de rue", 2);
        
        Segment s43 = new Segment(3, "nom de rue", 7);
        
        
        
        List<List<Segment>> graph = new ArrayList<>();
        for(int i = 0; i< 5; i++){
            graph.add(new ArrayList<Segment>());
        }
        
        graph.get(0).add(s01);
        graph.get(0).add(s02);
        
        graph.get(1).add(s12);
        graph.get(1).add(s13);
        
        graph.get(2).add(s21);
        graph.get(2).add(s23);
        graph.get(2).add(s24);
        
        graph.get(3).add(s34);
        
        graph.get(4).add(s43);
        
        List<Integer> deliveryPoints = new ArrayList();
        deliveryPoints.add(0);
        deliveryPoints.add(1);
        deliveryPoints.add(2);
        deliveryPoints.add(3);
        deliveryPoints.add(4);
        
        Dijkstra dijkstra = new Dijkstra(graph);
        dijkstra.executeDijkstra(0, deliveryPoints);
        
        double[] distance = dijkstra.getDistance();
        Integer[] predecessor = dijkstra.getPredecessor();
        
        /*
        for(int i = 0; i< distance.length; i++){
            System.out.print(distance[i] + "   ");
            System.out.println();
        }
        
        for(int i = 0; i< distance.length; i++){
            System.out.print(predecessor[i] + "   ");
            System.out.println();
        }*/
        
        double[] expResDistance = {0.0, 3.0, 4.0, 7.0, 9.0};
        Integer [] expResPredecessor = {null, 0, 1, 2, 3};
        for(int i = 0; i< 5; i++){
            assertEquals(expResDistance[i], distance[i], 0);
            assertEquals(expResPredecessor[i], predecessor[i]);
        }
        //assertEquals(expResult, result);
    }
}
