/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author User
 */
public class CoordinateTest {
    
    public CoordinateTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of toString method, of class Coordinate.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Coordinate instance = null;
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
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
        // TODO review the generated test code and remove the default call to fail.
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
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of getLatitude method, of class Coordinate.
     */
    @Test
    public void testGetLatitude() {
        System.out.println("getLatitude");
        Coordinate instance = null;
        Double expResult = null;
        Double result = instance.getLatitude();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of setLatitude method, of class Coordinate.
     */
    @Test
    public void testSetLatitude() {
        System.out.println("setLatitude");
        Double latitude = null;
        Coordinate instance = null;
        instance.setLatitude(latitude);
        // TODO review the generated test code and remove the default call to fail.
    }
    
}
