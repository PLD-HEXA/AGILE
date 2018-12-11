package entities;

import entities.algorithms.KMeans;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class KMeansTest {
    private static Coordinate[] coordinates;

    @BeforeClass
    public static void getCoordinates() {
        Coordinate c0 = new Coordinate(3.5, 1.0);
        Coordinate c1 = new Coordinate(2.0, 1.0);
        Coordinate c2 = new Coordinate(6.0, 1.8);
        Coordinate c3 = new Coordinate(9.1, 6.0);
        Coordinate c4 = new Coordinate(10.0, 4.1);
        Coordinate c5 = new Coordinate(3.0,  3.1);
        Coordinate c6 = new Coordinate(1.2, 3.1);
        Coordinate c7 = new Coordinate(12.1, 7.1);

        coordinates = new Coordinate[]{c0, c1, c2, c3, c4, c5, c6, c7};
    }

    /**
     * Test of the method init, of class KMeans.
     */
    @Test
    public void testInit() {
        System.out.println("init");
        int clusterNb = 3;
        KMeans kMeans = new KMeans(coordinates, clusterNb);
        kMeans.init();
        System.out.println(Arrays.toString(kMeans.getClusterCenters()));
    }

    /**
     * Test of the method randomInit, of class KMeans.
     */
    @Test
    public void testRandomInit() {
        System.out.println("randomInit");
        KMeans kMeans = new KMeans(coordinates, 6);
        kMeans.randomInit();
        assertNotNull(kMeans.getClusterCenters());
    }

    @Test
    public void testFindClusterDistance()
    {
        System.out.println("findClusterDistance");
        KMeans kMeans = new KMeans(coordinates, 2);
        kMeans.init();
        kMeans.findClusterDistance();
        System.out.println(kMeans.getDistances());
    }

    @Test
    public void testUpdateClusterCenters() {
        System.out.println("updateClusterDistance");
        KMeans kMeans = new KMeans(coordinates, 2);
        kMeans.init();
        kMeans.findClusterDistance();
        System.out.println(kMeans.getClusterNodes());
        kMeans.updateCluster();
        kMeans.findClusterDistance();
        System.out.println(kMeans.getClusterNodes());
    }

    @Test
    public void testFindCluster() {
        System.out.println("testFindDistance");
        KMeans kMeans = new KMeans(coordinates, 2);
        ArrayList<ArrayList<Integer>> expectedCluster =
                new ArrayList<>(Arrays.asList(
                        new ArrayList<>(Arrays.asList(1, 5, 0, 6)),
                        new ArrayList<>(Arrays.asList(4, 3, 7, 2))));
        assertEquals(expectedCluster, kMeans.findClusters());
    }
}
