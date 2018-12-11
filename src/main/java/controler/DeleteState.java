/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controler;

import entities.Itinerary;
import java.util.List;
import javafx.util.Pair;
import view.MainWindow;

/**
 *
 * @author Chris
 */
public class DeleteState extends DefaultState {
    
    private int numberDeliveryPointDeleted;

    public DeleteState() {
        this.numberDeliveryPointDeleted = 0;
    }

    public int getNumberDeliveryPointDeleted() {
        return numberDeliveryPointDeleted;
    }

    public void setNumberDeliveryPointDeleted(int numberDeliveryPointDeleted) {
        this.numberDeliveryPointDeleted = numberDeliveryPointDeleted;
    }
    
    public void addNumberDeliveryPointDeleted() {
        numberDeliveryPointDeleted++;
    }
    
    public void soustractNumberDeliveryPointDeleted() {
        numberDeliveryPointDeleted--;
    }
    
    // Faire la meme chose que pour mouseListener mais une fois qu'on est dans cet état
    @Override
    public void mouseClick(Controler controler, MainWindow mainWindow, CmdList cmdList, int x,int y) {
            x/=mainWindow.getGraphicalView().getScale();
            y/=mainWindow.getGraphicalView().getScale();
            double latitude = mainWindow.getGraphicalView().getLatMax()-(y+mainWindow.getGraphicalView().getPointradius())/mainWindow.getGraphicalView().getWidthScale();
            double longitude =mainWindow.getGraphicalView().getLongMax()-(mainWindow.getGraphicalView().getMapSize()-x-mainWindow.getGraphicalView().getPointradius())/mainWindow.getGraphicalView().getHeightScale();
            double minDistance=0.0062; // distance minimale 
            double distance;
            Integer nearestDeliveryPoint = null;
            int numberOfDeliveryPoints = mainWindow.getGraphicalView().getMap().getTabDeliveryPoints().size();
            for(int i=0;i<numberOfDeliveryPoints;i++) {
                    double curLatitude = mainWindow.getGraphicalView().getMap().getCoordinates()[mainWindow.getGraphicalView().getMap().getTabDeliveryPoints().get(i).getKey()].getLatitude();
                    double curLongitude = mainWindow.getGraphicalView().getMap().getCoordinates()[mainWindow.getGraphicalView().getMap().getTabDeliveryPoints().get(i).getKey()].getLongitude();
                    distance=Math.sqrt(Math.pow(latitude-curLatitude, 2)+Math.pow(longitude-curLongitude, 2));
                    System.out.println(distance);
                    if(distance < minDistance) {
                            minDistance=distance;
                            nearestDeliveryPoint=i;
                    }
            }
            System.out.println(minDistance);
            if(nearestDeliveryPoint != null) {
                Pair<Integer,Integer> itineraryIndex = findItineraryPointCorresponding(mainWindow, nearestDeliveryPoint);
                if (mainWindow.getGraphicalView().getMap().getTabDeliveryPoints().size() > numberDeliveryPointDeleted) {
                    cmdList.add(new CmdDeleteDeliveryPoint(mainWindow,nearestDeliveryPoint, controler, itineraryIndex));
                    controler.setCurState(controler.computeState);
                } else {
                    mainWindow.showError("All the delivery points have been deleted");
                    controler.setCurState(controler.computeState);
                }
            }
    }
    
    //Finding the itinerary that includes this delivery point
    public Pair<Integer,Integer> findItineraryPointCorresponding(MainWindow mainWindow, int nearestDeliveryPoint) {
			boolean globalFound = false;
			boolean localFound = false;
			int itineraryNumber=0;
			int deliveryPointNumber=0;
			int numberOfStops;
			List<Itinerary> itineraries = mainWindow.getGraphicalView().getItineraries();
			int numberOfItineraries = itineraries.size();
			while (!globalFound && itineraryNumber< numberOfItineraries) {
				deliveryPointNumber=0;
				numberOfStops = itineraries.get(itineraryNumber).getGeneralPath().size();
				while(!localFound && deliveryPointNumber < numberOfStops) {
					//If it's the good delivery point
					if(itineraries.get(itineraryNumber).getGeneralPath().get(deliveryPointNumber).getCoordinate() == mainWindow.getGraphicalView().getMap().getCoordinates()[mainWindow.getGraphicalView().getMap().getTabDeliveryPoints().get(nearestDeliveryPoint).getKey()]) {
						localFound=true;
						globalFound=true;
					}
					else {
						deliveryPointNumber++;
					}
				}
				if(!localFound) {
					itineraryNumber++;
				}
			}
                        Pair<Integer,Integer> result = new Pair<>(itineraryNumber, deliveryPointNumber);
                        return result;
        }
}

