package main;

import controler.Controler;
import entities.Coordinate;
import entities.Map;
import entities.Segment;
import view.MainWindow;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World");
        Segment s = new Segment(1, "rue du Levant", 123);
        Coordinate c1 = new Coordinate(123.0, 23.6);
        Coordinate c2 = new Coordinate(24.5, 53.1);
        Map m = new Map(c1, c2);
        System.out.println(s);
        System.out.println(m);
        Controler controler=new Controler();
    }
}
