package entities;

import org.junit.Test;

import static org.junit.Assert.*;

public class MapTest {
    @Test
    public void testMap(){
        Coordinate c1 = new Coordinate(123.0, 23.6);
        Coordinate c2 = new Coordinate(24.5, 53.1);
        Map m = new Map(c1, c2);
        assertEquals(c1, m.getCoordinateMin());
        assertEquals(c2, m.getCoordinateMax());
    }

    @Test
    public void testFailure() {
        assertEquals(1, 0);
    }

}