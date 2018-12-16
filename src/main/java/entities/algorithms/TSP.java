package entities.algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Implementation of the branch and bound algorithm to solve the traveling salesman problem (TSP)
 * @author  PLD-HEXA-301
 */
public class TSP {
    
    /**
     * Tab containing the solution of the salesman problem
     */
    private int[] optimalOrder;
    
    /**
     * Last vertice seen in the recursive "permut" algorithm
     */
    private int lastSeen;

    /**
     * Comparator to sort the branches to explore first (start with the closest vertices)
     */
    Comparator<Integer> comparator;
    
    /**
     * Matrix representing the graph for which we want to resolve the salesman problem
     */
    double[][] cost;
    
    /**
     * Default Constructor
     */
    public TSP(){}

    /**
     * Find the solution for the salesman problem using branch and bound algorithm 
     * @param graphMatrix Matrix representing the graph for which we want to resolve the salesman problem.
     *                    The starting/arrival point of the salesman problem corresponds to the first index in the graph matrix.
     * @return Tab of indexes containing the resolution of the salesman problem for the graph represented by the graph matrix.
     */
    public int[] getOrder(double[][] graphMatrix){
        
        //Contruct the data structures to use the recursive "permut" algorithm
        cost = graphMatrix;
        int nbNodes = graphMatrix.length;
        int[] seen = new int[nbNodes];
        Integer[] notSeen = new Integer[nbNodes - 1];
        
        //Tab containing the solution of the salesman problem
        optimalOrder = new int[nbNodes + 1];
        optimalOrder[0] = 0;
        optimalOrder[nbNodes] = 0;
        
        //Filling th data structures for the first call of the recursive "permut" method
        for(int k = 0; k < (nbNodes-1); k++){   
                notSeen[k] = k+1;        
        }
        seen[0] = 0;
        lastSeen = 0;
        double sp = Double.MAX_VALUE;
        
        //Comparator to sort the branches to explore first (start with the closest nodes)
        comparator = new Comparator<Integer>() {
                @Override
                public int compare(Integer v1, Integer v2) {
                    if(cost[lastSeen][v1]- cost[lastSeen][v2] > 0)
                        return 1;
                    return -1;
                }
            };
        
        //Call of the permut method 
        permut(seen, 1, notSeen, nbNodes-1, 0, sp);
   
        return optimalOrder;
    }
    
    
    /**
     * Recursive method using the branch and bound principle with the non optimal heuristic "cheapest insert"
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
            
            double dmin = cheapestInsert(notSeen);
        
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
    
 
    /**
     * Implementation of the cheapest insert heuristic which is a non optimal heuristic. 
     * @param notSeen Tab of not seen vertices
     * @return Approximation of the cost to pass by all the "not seen" vertices using the cheapest insert heuristic.
     */
    public double cheapestInsert(Integer[] notSeen){
        List<Integer> list = new ArrayList<>();
        list.add(lastSeen);
        list.add(0);
              
        List<Integer> notSeenTemp = new ArrayList<>();
        for(Integer node: notSeen){
            notSeenTemp.add(node);
        }
        
        int i = 0;
        int chosenIndex=0;
        Integer chosenNode = null;
        
        //While all the inserts have not been done
        while (notSeenTemp.size() >0){
            double cheapestInsertCost = Double.MAX_VALUE;
            //For each position in the list
            for(i =0; i< list.size()-1; i++){
                //For each "not seen" node not yet inserted
                for(Integer nodeTemp: notSeenTemp){
                    //find the cheapest insert
                    if(cheapestInsertCost>(cost[list.get(i)][nodeTemp] + cost[nodeTemp][list.get(i+1)] - cost[list.get(i)][list.get(i+1)])){
                        cheapestInsertCost=cost[list.get(i)][nodeTemp] + cost[nodeTemp][list.get(i+1)] - cost[list.get(i)][list.get(i+1)];
                        chosenIndex = i+1;
                        chosenNode = nodeTemp;                              
                    }
                    
                }
                
            }
            list.add(chosenIndex, chosenNode);
            notSeenTemp.remove(chosenNode);
        }
        
        double res =0;
        for(i = 0; i< list.size()-1; i++){
            res+= cost[list.get(i)][list.get(i+1)];
        }
        return res;
    }
    
}