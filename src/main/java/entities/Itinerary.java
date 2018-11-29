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
    List<Coordinate> detailedPath;
    
    public Itinerary(){
        
    }

	public List<DeliveryPoint> getGeneralPath() {
		return generalPath;
	}

	public List<Coordinate> getDetailedPath() {
		return detailedPath;
	}

	public void setGeneralPath(List<DeliveryPoint> generalPath) {
		this.generalPath = generalPath;
	}

	public void setDetailedPath(List<Coordinate> detailedPath) {
		this.detailedPath = detailedPath;
	}
	
	
    
    
}
