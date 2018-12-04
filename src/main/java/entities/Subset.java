/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author youss
 */
public class Subset {
    int parent;
    int rank;

    public Subset(int parent, int rank) {
        this.parent = parent;
        this.rank = rank;
    }

    public int getParent() {
        return parent;
    }

    public int getRank() {
        return rank;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public void incrementRank(){
        this.rank++;
    }
    
}
