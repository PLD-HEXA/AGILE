/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author PLD-HEXA-301
 */
public class Edge {
    int vertice1;
    int vertice2;
    double cost;

    public Edge(int vertice1, int vertice2, double cost) {
        this.vertice1 = vertice1;
        this.vertice2 = vertice2;
        this.cost = cost;
    }

    public int getVertice1() {
        return vertice1;
    }

    public int getVertice2() {
        return vertice2;
    }

    public double getCost() {
        return cost;
    }

    public void setVertice1(int vertice1) {
        this.vertice1 = vertice1;
    }

    public void setVertice2(int vertice2) {
        this.vertice2 = vertice2;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
    
    @Override
    public boolean equals(Object other){
        Edge myOther = (Edge)other;
        if(myOther.getCost() == this.cost && myOther.getVertice1() == this.vertice1 && myOther.getVertice2() == this.vertice2){
            return true;
        }
        return false;
    }
    
    
}
