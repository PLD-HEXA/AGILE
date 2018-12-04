package entities.algorithms;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Implementation of the branch and bound algorithm to solve the traveling salesman problem (TSP)
 * @author PLD-HEXA-301
 */
public class TSP {
    
    private int[] optimalOrder;
    private int lastSeen;
    private double[][] adjMatrix;
    private int nbNodes;
    private Comparator<Integer> comparator;
    private double[][] cost;
    
    /**
     * Default Constructor
     */
    public TSP(){}
    
    /**
     * Find the solution for the salesman problem with the passed parameters
     * @param graph Graph in which the salesman problem is solved
     * @param targets List of targets to reach between starting and arrvial points
     * @param startingPoint Starting point
     * @param arrivalPoint Arrival point
     * @param priorities List of priorities associated to the targets (if null, the priorities are not taken in consideration to
     * find the best path)
     * @param costsTab tab containing the costs from any target or starting/arrival point to any other target or starting/arrival point. 
     * If null, the tab is generated in the method.
     * @return Tab of Coordinates representing the solution for the salesman problem
     */
    public int[] getOrder(double[][] adjMatrix){
        
        cost = adjMatrix;
        //this.indexWarehouse = indexWarehouse;
        nbNodes = adjMatrix.length;
        

        int[] seen = new int[nbNodes];
        Integer[] notSeen = new Integer[nbNodes - 1]; 
        optimalOrder = new int[nbNodes + 1];
        optimalOrder[0] = 0;
        optimalOrder[nbNodes] = 0;
        
        //On remplit le tableau de not seen
        for(int k = 0; k < (nbNodes-1); k++){   
                notSeen[k] = k+1;        
        }

        seen[0] = 0;
        lastSeen = 0;
        double sp = Double.MAX_VALUE;
        
        comparator = new Comparator<Integer>() {
                @Override
                public int compare(Integer v1, Integer v2) {
                    if(cost[lastSeen][v1]- cost[lastSeen][v2] > 0)
                        return 1;
                    return -1;
                }
            };
           
        sp = permut(seen, 1, notSeen, nbNodes-1, 0, sp);
//        System.out.println("length of the shortest path :" + sp);
        
        //Coordinate[] res = new Coordinate[nbNodes+1];
        
        //for(i = 0; i< nbNodes+1; i++){
        //    res[i] = nodes.get(optimalOrder[i]);
        //}
        
        return optimalOrder;
    }
    
    /**
     * Recursive method using the branch and bound principle
     * @param seen Tab of seen vertices
     * @param nbSeen Number of seen vertices
     * @param notSeen Tab of not seen vertices
     * @param nbNotSeen Number of not seen vertices
     * @param length Current length of the path
     * @param sp Current length of the shortest path
     * @return Length of the shortest path found
     */
    public double permut(int[] seen, int nbSeen, Integer[] notSeen, int nbNotSeen, double length, double sp){
        Integer[] order = new Integer[nbNotSeen];
        if(nbNotSeen == 0){
            double finalCost = length + cost[seen[nbSeen-1]][0];
            if(sp>finalCost){
                sp=finalCost;
                for(int i = 1; i< nbSeen; i++){
                    optimalOrder[i] = seen[i];
                }
            }
        }
        else{
            for(int i=0;i<nbNotSeen;i++){
    		order[i]=notSeen[i];
            }
            Arrays.sort(order, comparator);
            
            double dmin = 0;
            double tempL = Double.MAX_VALUE;
            for(int i=0;i<nbNotSeen;i++){
    		double temp = Double.MAX_VALUE;
    		for(int j=0;j<nbNotSeen;j++){
                    if(cost[notSeen[i]][notSeen[j]]<temp){
                        temp = cost[notSeen[i]][notSeen[j]];
                    }
    		}
    		if(cost[notSeen[i]][0]<temp){
                    temp = cost[notSeen[i]][0];
                }
					
                if(tempL>cost[seen[nbSeen-1]][notSeen[i]]){
                    tempL = cost[seen[nbSeen-1]][notSeen[i]];
                }
				
    		dmin+=temp;
            }
    	
            dmin+=tempL;
        
        
            if(!(sp<length+dmin)){
                for(int j=0;j<nbNotSeen;j++){
                    seen[nbSeen]=order[j];
                    lastSeen=seen[nbSeen];
                    order[j]=order[nbNotSeen-1];
                    sp=permut(seen, nbSeen+1, order, nbNotSeen-1,length+cost[seen[nbSeen-1]][seen[nbSeen]],sp);
                    order[j]=seen[nbSeen];
                }
            }
        }
        return sp;
    }
    
}