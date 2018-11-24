/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 *
 * @author User
 */
public class CoordinateTest {
    /**
     * Test of toString method, of class Coordinate.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Coordinate instance = new Coordinate(12.2, 23.4);
        String expResult = "Coordinate{longitude=12.2, latitude=23.4}";
        String result = instance.toString();
        assertEquals(expResult, result);
    }

    /**
     * Test of getLongitude method, of class Coordinate.
     */
    @Test
    public void testGetLongitude() {
        System.out.println("getLongitude");
        Coordinate instance = new Coordinate(23.4, 124.);
        Double expResult = 23.4;
        Double result = instance.getLongitude();
        assertEquals(expResult, result);
    }

    /**
     * Test of setLongitude method, of class Coordinate.
     */
    @Test
    public void testSetLongitude() {
        System.out.println("setLongitude");
        Double longitude = 23.4;
        Coordinate instance = new Coordinate(23.4, 124.);
        instance.setLongitude(longitude);
    }
}
