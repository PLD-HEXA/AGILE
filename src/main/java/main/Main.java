package main;

import entities.Segment;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World");
        Segment s = new Segment(1, "rue du Levant", 123);
        System.out.println(s);
    }
}
