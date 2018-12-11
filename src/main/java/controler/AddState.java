/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controler;

import javafx.util.Pair;
import view.MainWindow;

/**
 *
 * @author Chris
 */
public class AddState extends DefaultState {
    
    private int duration;
    private int numberPointOriginal;

    public AddState() {
    }
    
    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
    
    @Override
    public void mouseClick(Controler controler, MainWindow mainWindow, CmdList cmdList, int x,int y) {
            System.out.println("X : " + x);
            System.out.println("Y : " + y);
            double latitude = mainWindow.getGraphicalView().getLatMax()-(y+mainWindow.getGraphicalView().getPointradius())/mainWindow.getGraphicalView().getWidthScale();
            double longitude =mainWindow.getGraphicalView().getLongMax()-(mainWindow.getGraphicalView().getWidth()-x-mainWindow.getGraphicalView().getPointradius())/mainWindow.getGraphicalView().getHeightScale();
            double minDistance=0.0025; // distance minimale 
            double distance;
            Integer indexNewDeliveryPoint = null;
            
            
            int numberOfDeliveryPoints = mainWindow.getGraphicalView().getMap().getTabDeliveryPoints().size();
            for(int i=0;i<numberOfDeliveryPoints;i++) {
                    double curLatitude = mainWindow.getGraphicalView().getMap().getCoordinates()[i].getLatitude();
                    double curLongitude = mainWindow.getGraphicalView().getMap().getCoordinates()[i].getLongitude();
                    distance=Math.sqrt(Math.pow(latitude-curLatitude, 2)+Math.pow(longitude-curLongitude, 2));
                    System.out.println(distance);
                    if(distance < minDistance) {
                            minDistance=distance;
                            indexNewDeliveryPoint=i;
                    }
            }
            System.out.println(minDistance);
            if(indexNewDeliveryPoint != null) {
                
                cmdList.add(new CmdAddDeliveryPoint(mainWindow, indexNewDeliveryPoint, duration, controler.getPathFinder()));
                controler.setCurState(controler.computeState);
            }
    }
}

