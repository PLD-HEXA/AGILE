/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import entities.algorithms.Dijkstra;
import entities.algorithms.TSP;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author youss
 */
public class TSPTest {
    
     /**
     * Test the get order methiod of the class TSP. 
     */
    @Test
    public void testGetOrder() {      
        TSP tsp = new TSP();
        double[][] adjMatrix = { {0,8,4,9,9},
                                 {8,0,6.0,7,10},
                                 {4,6,0,5.0,6},
                                 {9,7.0,5,0,4},
                                 {9,10,6.0,4,0} };
        
        

        int[] optimalOrder = tsp.getOrder(adjMatrix, false);
        
        int[] resExpOptimalOrder = {0, 2, 4, 3, 1, 0};
        
        for(int i = 0; i< optimalOrder.length; i++){
            //System.out.println(optimalOrder[i] + "    ");
            assertEquals(resExpOptimalOrder[i], optimalOrder[i]);
        }
        
    }
    
}
