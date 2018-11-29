/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import entities.algorithms.GeneralTSP;
import entities.algorithms.TSP;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author youss
 */
public class GeneralTSPTest {

 /**
     * Test the get order method of the class TSP. 
     */
    /*
    @Test
    public void testGetOrder() {      
        GeneralTSP tsp = new GeneralTSP();
        double[][] adjMatrix = { {0,8,4,9,9},
                                 {8,0,6.0,7,10},
                                 {4,6,0,5.0,6},
                                 {9,7.0,5,0,4},
                                 {9,10,6.0,4,0} };
        
        

        int[] optimalOrder = tsp.getOrder(adjMatrix, 1);
        
        int[] resExpOptimalOrder = {0, 2, 4, 3, 1, 0};
        
        for(int i = 0; i< optimalOrder.length; i++){
            //System.out.println(optimalOrder[i] + "    ");
            //assertEquals(resExpOptimalOrder[i], optimalOrder[i]);
        }
        
    }
    */
    
    /**
     * Test the get order method of the class TSP. 
     */
    
    @Test
    public void testPerformance() {      
        GeneralTSP tsp = new GeneralTSP();
        double[][] adjMatrix = new double[40][40];
        
        for(int i = 0; i< adjMatrix.length; i++){
            for(int j = 0; j< adjMatrix.length; j++){
                if(i == j){
                    adjMatrix[i][j] = 0;
                }
                else if (i == j+1){
                    adjMatrix[i][j] = 1;
                }
                else {
                    adjMatrix[i][j] = 2;
                }
            }
        }
        adjMatrix[0][39] = 1;
        
        
        

        int[] optimalOrder = tsp.getOrder(adjMatrix, 1);
        
        //int[] resExpOptimalOrder = {0, 2, 4, 3, 1, 0};
        
        /*for(int i = 0; i< optimalOrder.length; i++){
            //System.out.println(optimalOrder[i] + "    ");
            assertEquals(resExpOptimalOrder[i], optimalOrder[i]);
        }*/
        
        for(int i = 0; i< optimalOrder.length; i++){
            System.out.println(optimalOrder[i] + "    ");
        }
        
    }
    
    
    /**
     * Test the get order method of the class TSP. 
     */
    /*
    @Test
    public void testPerformance2() {      
        GeneralTSP tsp = new GeneralTSP();
        double[][] adjMatrix = new double[20][20];
        
        for(int i = 0; i< adjMatrix.length; i++){
            for(int j = 0; j< adjMatrix.length; j++){
                
                    adjMatrix[i][j] = Math.random()*20 +20;
                
            }
        }
        
        
        

        int[] optimalOrder = tsp.getOrder(adjMatrix, 3);
        
        //int[] resExpOptimalOrder = {0, 2, 4, 3, 1, 0};
        
        
        
        for(int i = 0; i< optimalOrder.length; i++){
            System.out.println(optimalOrder[i] + "    ");
        }
        
    }*/
}

