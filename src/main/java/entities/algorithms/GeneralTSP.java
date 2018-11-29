/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import javafx.util.Pair;

/**
 * Implementation of the branch and bound algorithm to solve the traveling salesman problem (TSP)
 * @author Youssef
 */
public class GeneralTSP {
    
    int[] optimalOrder;
    int lastSeen;
    double[][] adjMatrix;
    int nbNodes;
    Comparator<Integer> comparator;
    Comparator<Pair<Pair<Integer,Integer>,Double>> comparator2;
    double[][] cost;
    //int indexWarehouse;
    
    /**
     * Default Constructor
     */
    public GeneralTSP(){}
    
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
    public int[] getOrder(double[][] adjMatrix, int nbDeliveryMen){
        
        cost = adjMatrix;
        //this.indexWarehouse = indexWarehouse;
        nbNodes = adjMatrix.length;
        

        int[] seen = new int[nbNodes+ nbDeliveryMen-1];
        Integer[] notSeen = new Integer[nbNodes - 1]; 
        optimalOrder = new int[nbNodes + nbDeliveryMen];
        int nbDeliveriesByDeliveryMan = (adjMatrix.length-1)/(nbDeliveryMen);
        int rest = (adjMatrix.length-1)%(nbDeliveryMen);
        
        for(int i = 0; i< optimalOrder.length; i++){
            optimalOrder[i] = -1;
        }
        optimalOrder[0] = 0;
        int cmpt = 0;
        for(int i = 0; i< nbDeliveryMen; i++){
            if(i < rest){
                optimalOrder[cmpt+ nbDeliveriesByDeliveryMan+2] = 0;
                cmpt += nbDeliveriesByDeliveryMan+2;
            }
            else{
                optimalOrder[cmpt+ nbDeliveriesByDeliveryMan+1] = 0;
                cmpt += nbDeliveriesByDeliveryMan+1;
            }
        }
        
        
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
        
        comparator2 = new Comparator<Pair<Pair<Integer,Integer>,Double>>() {
                @Override
                public int compare(Pair<Pair<Integer,Integer>,Double> v1, Pair<Pair<Integer,Integer>,Double> v2) {
                    if(v1.getValue() - v2.getValue() > 0)
                        return 1;
                    return -1;
                }
            };
           
        sp = permut(seen, 1, notSeen, nbNodes-1, 0, sp, nbDeliveriesByDeliveryMan);
        System.out.println("length of the shortest path :" + sp);
        
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
    public double permut(int[] seen, int nbSeen, Integer[] notSeen, int nbNotSeen, double length, double sp, int nbZerosLeft){
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
                
        else if(nbSeen >1 && optimalOrder[nbSeen] == 0)
        {
            for(int i=0;i<nbNotSeen;i++){
    		order[i]=notSeen[i];
            }
            seen[nbSeen]=0;
            lastSeen=seen[nbSeen];
            nbZerosLeft --;
            //notSeen[j]=notSeen[nbNotSeen-1];
            sp=permut(seen, nbSeen+1, order, nbNotSeen,length+cost[seen[nbSeen-1]][seen[nbSeen]],sp, nbZerosLeft);
            //notSeen[j]=seen[nbSeen];
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
    	
            dmin+=tempL;
            */
            double dmin = kruscal(notSeen, nbZerosLeft);
        
        
            if(!(sp<length+dmin)){
                for(int j=0;j<nbNotSeen;j++){
                    seen[nbSeen]=order[j];
                    lastSeen=seen[nbSeen];
                    order[j]=order[nbNotSeen-1];
                    sp=permut(seen, nbSeen+1, order, nbNotSeen-1,length+cost[seen[nbSeen-1]][seen[nbSeen]],sp, nbZerosLeft);
                    order[j]=seen[nbSeen];
                }
            }
        }
        return sp;
    }
    
    
    // A utility function to find set of an element i 
    // (uses path compression technique) 
    public int find(HashMap<Integer, Pair<Integer, Integer>> subsets, int i) 
    { 
        // find root and make root as parent of i (path compression)    
        if (subsets.get(i).getKey() != i){
            subsets.put(i, new Pair<>(find(subsets, subsets.get(i).getKey()) , subsets.get(i).getValue()));
        }
        
        return subsets.get(i).getKey();
    } 
    
    // A function that does union of two sets of x and y 
    // (uses union by rank) 
    void Union(HashMap<Integer, Pair<Integer, Integer>> subsets, int x, int y) 
    { 
        int xroot = find(subsets, x); 
        int yroot = find(subsets, y); 
  
        // Attach smaller rank tree under root of high rank tree 
        // (Union by Rank) 
        if (subsets.get(xroot).getValue() < subsets.get(yroot).getValue()){
            subsets.put(xroot, new Pair<>(yroot, subsets.get(xroot).getValue()));
        }
        else if (subsets.get(xroot).getValue() < subsets.get(yroot).getValue()){
            subsets.put(yroot, new Pair<>(xroot, subsets.get(yroot).getValue()));
        }
  
        // If ranks are same, then make one as root and increment 
        // its rank by one 
        else
        { 
            subsets.put(yroot, new Pair<>(xroot, subsets.get(yroot).getValue()));
            subsets.put(xroot, new Pair<>(xroot, subsets.get(xroot).getValue()+1));
            
        } 
    } 
    
    public double kruscal (Integer[] notSeen, int nbZerosLeft){
        //res will store the sum of the weight of every edge of the MST
        double res = 0;
        
        
        //Create the vertices of the sub graph
        /*if(lastSeen != 0){
            Integer [] vertices = new Integer[notSeen.length + 2];
            vertices [0] = lastSeen;
            for(int i = 1; i< vertices.length -1; i++){
                vertices[i] = notSeen[i-1];
            }
            vertices[vertices.length-1] = 0;
        }*/
        
        //Create the vertices of the sub graph
        /*else{
            Integer [] vertices = new Integer[notSeen.length + 1];
            vertices [0] = lastSeen;
            for(int i = 1; i< vertices.length; i++){
                vertices[i] = notSeen[i-1];
            }
        }*/
        
        //Create the edges
        List<Pair<Pair<Integer,Integer>, Double>> edges = new ArrayList<>();
        for(int i = 0; i< notSeen.length; i++){
            for(int j = 0; j<i; j++){
                edges.add(new Pair<>(new Pair<>( notSeen[i],notSeen[j] ) , Math.min(cost[notSeen[i]][notSeen[j]], cost[notSeen[j]][notSeen[i]]) ));
            }
        }
        
        
        if(lastSeen != 0){
            for(int i = 0; i< notSeen.length; i++){
                edges.add(new Pair<>(new Pair<>( notSeen[i],lastSeen ) , Math.min(cost[notSeen[i]][lastSeen], cost[lastSeen][notSeen[i]]) ));
            }
            for(int i = 0; i< notSeen.length; i++){
                edges.add(new Pair<>(new Pair<>( notSeen[i],0 ) , Math.min(cost[notSeen[i]][0], cost[0][notSeen[i]]) ));
            }
        }
        
        else{
            for(int i = 0; i< notSeen.length; i++){
                edges.add(new Pair<>(new Pair<>( notSeen[i],0 ) , Math.min(cost[notSeen[i]][0], cost[0][notSeen[i]]) ));
            }
        }
        
        //Sort the edges by increasing weight
        Collections.sort(edges, comparator2);
        
        
        HashMap<Integer, Pair<Integer, Integer>> subsets = new HashMap<>();
        subsets.put(lastSeen, new Pair<>(lastSeen, 0));
        for(int i = 0; i< notSeen.length; i++){
            subsets.put(notSeen[i], new Pair<>(notSeen[i], 0));
        }
        subsets.put(0, new Pair<>(0, 0));
        
        
        //Pour chaque vertice taken by increasing weight
        int addedEdges = 0;
        int nbMaxAddedEdges = subsets.size()-1;
        int x;
        int y;
        boolean selectedEdge = false;
        double lengthSelectedEdge = 0;
        for(Pair<Pair<Integer,Integer>, Double> edge :edges){
            x = find(subsets, edge.getKey().getKey()); 
            y = find(subsets, edge.getKey().getValue());
            
            if(!selectedEdge && (edge.getKey().getKey() == 0 || edge.getKey().getValue() == 0) ){
                    lengthSelectedEdge = edge.getValue();
                    selectedEdge = true;
                }
  
            // If including this edge doesn't cause cycle, 
            // include it in result and increment the index  
            // of result for next edge 
            if (x != y) 
            { 
                
                res += edge.getValue();
                addedEdges++;
                Union(subsets, x, y); 
            } 
            // Else discard the next_edge 
            
            if(addedEdges == nbMaxAddedEdges){
                break;
            }
            
        }
        
        if(lastSeen != 0){
            res+= 2*(nbZerosLeft-1)* lengthSelectedEdge;
        }
        
        else{
            res+= 2*(nbZerosLeft-1)* lengthSelectedEdge + lengthSelectedEdge;
        }
        return res;
        
        
    }
    
}