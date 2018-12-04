/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.algorithms;

import entities.Segment;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 *
 */
public class Dijkstra {
    private double[] distance;
    private Integer[] predecessor;
    private List<List<Segment>> graph;

    public Dijkstra(List<List<Segment>> graph) {
        this.graph = graph;
        distance = new double[graph.size()];
        predecessor = new Integer[graph.size()];       
    }

    public double[] getDistance() {
        return distance;
    }

    public Integer[] getPredecessor() {
        return predecessor;
    }
    
    
    public double getDistance(Integer s1, Integer s2){
        List<Segment> segments = graph.get(s1);
        for(Segment segment: segments){
            if(segment.getDestIndex() == s2){
                return segment.getLength();
            }
        }
        return Double.MAX_VALUE;
    }
    
    private void release (Integer s1, Integer s2){
        if(distance[s2]> distance[s1]+ getDistance(s1,s2) ){
            distance[s2] = distance[s1]+ getDistance(s1,s2);
            predecessor[s2] = s1;
        }
    }
    
    public void executeDijkstra(Integer s0, List<Integer> targets){
        //Reinitialize distance and predecessors arrow to empty arrays
        distance = new double[graph.size()];
        predecessor = new Integer[graph.size()];
        
        int nbPointsToReach = targets.size();
        int length = graph.size();
        boolean[] black = new boolean[length];
        PriorityQueue<Integer> grey = new PriorityQueue(11, new Comparator<Integer>(){
            @Override
            public int compare(Integer s1, Integer s2) {
                if(distance[s1] > distance [s2]){
                    return 1;
                }
                else if(distance[s1] < distance [s2]){
                    return -1;
                }
                else{
                    return 0;
                }     
            }
        });
        boolean[] white = new boolean[length];
        for(int i = 0; i< length; i++){
            distance[i] = Double.MAX_VALUE;
            predecessor[i] = null;
            white[i] = true;
            black[i] = false;
        }
        
        distance[s0] = 0;
        white[s0] = false;
        grey.offer(s0);
        
        Integer si;
        while(!grey.isEmpty()){
            si = grey.poll();
            for(Segment segment: graph.get(si)){
                if(!black[segment.getDestIndex()]){
                    release(si, segment.getDestIndex());
                    if(white[segment.getDestIndex()]){
                        white[segment.getDestIndex()] = false;
                        grey.offer(segment.getDestIndex());
                    }
                }
            }
            black[si] = true;
            
           
            if(targets.contains(si)){
                nbPointsToReach--; 
            }
            
            
            if(nbPointsToReach == 0){
                break;
            }
        }
    }
    
    
   
    
}
