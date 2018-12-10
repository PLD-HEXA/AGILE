/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.algorithms;

import entities.Edge;
import entities.Subset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * Implementation of the branch and bound algorithm to solve the traveling salesman problem (TSP)
 * @author Youssef
 */
public class TSP {
    
    int[] optimalOrder;
    int lastSeen;
    double[][] adjMatrix;
    int nbNodes;
    Comparator<Integer> comparator;
    Comparator<Edge> comparator2;
    double[][] cost;
    //int indexWarehouse;
    
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
    public int[] getOrder(double[][] adjMatrix, boolean optimalRes){
        
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
        
        comparator2 = new Comparator<Edge>() {
                @Override
                public int compare(Edge v1, Edge v2) {
                    if(v1.getCost() - v2.getCost() > 0)
                        return 1;
                    else if(v1.getCost() - v2.getCost() < 0)
                        return -1;
                    return 0;
                }
            };
        if(optimalRes){
            sp = optimalPermut(seen, 1, notSeen, nbNodes-1, 0, sp);
        }
        else{
            sp = nonOptimalPermut(seen, 1, notSeen, nbNodes-1, 0, sp);
        }
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
    public double optimalPermut(int[] seen, int nbSeen, Integer[] notSeen, int nbNotSeen, double length, double sp){
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
            /*
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
    	
            dmin+=tempL;*/
            
            double dmin = kruscal(notSeen);
        
        
            if(!(sp<length+dmin)){
                for(int j=0;j<nbNotSeen;j++){
                    seen[nbSeen]=order[j];
                    lastSeen=seen[nbSeen];
                    order[j]=order[nbNotSeen-1];
                    sp=optimalPermut(seen, nbSeen+1, order, nbNotSeen-1,length+cost[seen[nbSeen-1]][seen[nbSeen]],sp);
                    order[j]=seen[nbSeen];
                }
            }
        }
        return sp;
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
    public double nonOptimalPermut(int[] seen, int nbSeen, Integer[] notSeen, int nbNotSeen, double length, double sp){
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
                    sp=nonOptimalPermut(seen, nbSeen+1, order, nbNotSeen-1,length+cost[seen[nbSeen-1]][seen[nbSeen]],sp);
                    order[j]=seen[nbSeen];
                }
            }
        }
        return sp;
    }
    
    
    // A utility function to find set of an element i 
    // (uses path compression technique) 
    public int find(HashMap<Integer, Subset> subsets, int i) 
    { 
        // find root and make root as parent of i (path compression)    
        if (subsets.get(i).getParent() != i){
            subsets.get(i).setParent(find(subsets, subsets.get(i).getParent()));
        }
        
        return subsets.get(i).getParent();
    } 
    
    // A function that does union of two sets of x and y 
    // (uses union by rank) 
    void Union(HashMap<Integer, Subset> subsets, int x, int y) 
    { 
        int xroot = find(subsets, x); 
        int yroot = find(subsets, y); 
  
        // Attach smaller rank tree under root of high rank tree 
        // (Union by Rank) 
        if (subsets.get(xroot).getRank() < subsets.get(yroot).getRank()){
            subsets.get(xroot).setParent(yroot);
        }
        else if (subsets.get(xroot).getRank()< subsets.get(yroot).getRank()){
            subsets.get(yroot).setParent(xroot);
        }
  
        // If ranks are same, then make one as root and increment 
        // its rank by one 
        else
        { 
            subsets.get(yroot).setParent(xroot);
            subsets.get(xroot).incrementRank();
            
        } 
    } 
    
    public double kruscal (Integer[] notSeen){
        //res will store the sum of the weight of every edge of the MST
        double res = 0;
        
        
        //Create the edges
        int i;
        int j;
        List<Edge> edges = new ArrayList<>();
        //List<Edge> edgesWithWarehouse = new ArrayList<>();
        for(i = 0; i< notSeen.length; i++){
            for(j = 0; j<i; j++){
                edges.add(new Edge(notSeen[i],notSeen[j] , Math.min(cost[notSeen[i]][notSeen[j]], cost[notSeen[j]][notSeen[i]]) ));
            }
        }
        
        
        if(lastSeen != 0){
            for(i = 0; i< notSeen.length; i++){
                edges.add(new Edge(notSeen[i],lastSeen , Math.min(cost[notSeen[i]][lastSeen], cost[lastSeen][notSeen[i]]) ));
                edges.add(new Edge(notSeen[i],0 , Math.min(cost[notSeen[i]][0], cost[0][notSeen[i]]) )); 
            }           
        }
        
        else{
            for(i = 0; i< notSeen.length; i++){
                edges.add(new Edge(notSeen[i],0 , Math.min(cost[notSeen[i]][0], cost[0][notSeen[i]]) ));
            }
        }
        
        
        //Sort the edges by increasing weight
        Collections.sort(edges, comparator2);
        
        HashMap<Integer, Subset> subsets = new HashMap<>();
        subsets.put(lastSeen, new Subset(lastSeen, 0));
        for(i = 0; i< notSeen.length; i++){
            subsets.put(notSeen[i], new Subset(notSeen[i], 0));
        }
        subsets.put(0, new Subset(0, 0));
        
        
        //Pour chaque vertice taken by increasing weight
        int addedEdges = 0;
        int nbMaxAddedEdges = subsets.size()-1;
        int x;
        int y;
        
        
        for(Edge edge :edges){
            x = find(subsets, edge.getVertice1()); 
            y = find(subsets, edge.getVertice2());
  
            // If including this edge doesn't cause cycle, 
            // include it in result and increment the index  
            // of result for next edge 
            if (x != y) 
            { 
                
                res += edge.getCost();
                addedEdges++;
                Union(subsets, x, y); 
            } 
            // Else discard the next_edge 
            
            if(addedEdges == nbMaxAddedEdges){
                break;
            }   
        }
        return res;
        
        
    }
    
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
        
        while (notSeenTemp.size() >0){
            double cheapestInsertCost = Double.MAX_VALUE;
            for(i =0; i< list.size()-1; i++){
                for(Integer nodeTemp: notSeenTemp){
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