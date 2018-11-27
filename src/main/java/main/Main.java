package main;

import entities.Coordinate;
import entities.Map;
import entities.Segment;
import entities.algorithms.PathFinder;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        /*
        System.out.println("Hello World");
        Segment s = new Segment(1, "rue du Levant", 123);
        Coordinate c1 = new Coordinate(123.0, 23.6);
        Coordinate c2 = new Coordinate(24.5, 53.1);
        Map m = new Map(c1, c2);
        System.out.println(s);
        System.out.println(m);*/
        
        Segment s01 = new Segment(1, "rue du Levant", 3);
        Segment s02 = new Segment(2, "nom de rue", 5 );
        
        
        Segment s10 = new Segment(0, "nom de rue", 3);
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
        
        graph.get(1).add(s10);
        graph.get(1).add(s12);
        graph.get(1).add(s13);
        
        graph.get(2).add(s21);
        graph.get(2).add(s23);
        graph.get(2).add(s24);
        
        graph.get(3).add(s34);
        
        graph.get(4).add(s43);
        
        Coordinate[] coordinates = new Coordinate[5];
        
        int[] deliveryPoints = new int[2];
        deliveryPoints[0] = 1;
        deliveryPoints[1] = 2;
        //deliveryPoints[2] = 3;
        
        int indexWarehouse = 0;
        
        int nbDeliveryMen = 1;
        
        PathFinder pf = new PathFinder();
        pf.setGraph(graph);
        pf.setCoordinates(coordinates);
        pf.setDeliveryPoints(deliveryPoints);
        pf.setIndexWarehouse(indexWarehouse);
        pf.setNbDeliveryMen(nbDeliveryMen);
        
        pf.findPath();
    }
}
