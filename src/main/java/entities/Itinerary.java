/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.util.List;

/**
 *
 * @author youss
 */
public class Itinerary {
    List<DeliveryPoint> generalPath;
    List<Coordinate> deltailedPath;
    
    public Itinerary(){
        
    }

    public Itinerary(List<DeliveryPoint> generalPath, List<Coordinate> deltailedPath) {
        this.generalPath = generalPath;
        this.deltailedPath = deltailedPath;
    }

    public List<DeliveryPoint> getGeneralPath() {
        return generalPath;
    }

    public List<Coordinate> getDeltailedPath() {
        return deltailedPath;
    }

    public void setGeneralPath(List<DeliveryPoint> generalPath) {
        this.generalPath = generalPath;
    }

    public void setDeltailedPath(List<Coordinate> deltailedPath) {
        this.deltailedPath = deltailedPath;
    }
    
    
    
    
}
