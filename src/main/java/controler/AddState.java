/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controler;

import view.MainWindow;

/**
 *
 * @author Chris
 */
public class AddState extends DefaultState {

    /**
     * Represents the duration entered by the user when he wants to add a new
     * delivery point
     */
    private int duration;
    
    /**
     * Number of delivery point original. Increase when a new delivery point is
     * added and decrease if the user has clicked on undo
     */
    private int numberPointOriginal;

    public AddState() {
        numberPointOriginal = 0;
    }

    public int getNumberPointOriginal() {
        return numberPointOriginal;
    }

    public void setNumberPointOriginal(int numberPointOriginal) {
        this.numberPointOriginal = numberPointOriginal;
    }

    public void addNumberPoint() {
        numberPointOriginal++;
    }

    public void soustractNumberPoint() {
        numberPointOriginal--;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public void mouseClick(Controler controler, MainWindow mainWindow, CmdList cmdList, int x, int y) {
        x /= mainWindow.getGraphicalView().getScale();
        y /= mainWindow.getGraphicalView().getScale();
        double latitude = mainWindow.getGraphicalView().getLatMax() - (y + mainWindow.getGraphicalView().getPointradius()) / mainWindow.getGraphicalView().getWidthScale();
        double longitude = mainWindow.getGraphicalView().getLongMax() - (mainWindow.getGraphicalView().getMapSize() - x - mainWindow.getGraphicalView().getPointradius()) / mainWindow.getGraphicalView().getHeightScale();
        double minDistance = 0.0025; // minimal distance to get the point corresponding to x and y on the map
        double distance;
        Integer indexNewDeliveryPoint = null;

        // To retrieve the index of the new delivery point added
        int numberOfCoordinates = mainWindow.getGraphicalView().getMap().getCoordinates().length;
        for (int i = 0; i < numberOfCoordinates; i++) {
            double curLatitude = mainWindow.getGraphicalView().getMap().getCoordinates()[i].getLatitude();
            double curLongitude = mainWindow.getGraphicalView().getMap().getCoordinates()[i].getLongitude();
            distance = Math.sqrt(Math.pow(latitude - curLatitude, 2) + Math.pow(longitude - curLongitude, 2));
            if (distance < minDistance) {
                minDistance = distance;
                indexNewDeliveryPoint = i;
            }
        }
        if (indexNewDeliveryPoint != null) {
            cmdList.add(new CmdAddDeliveryPoint(mainWindow, indexNewDeliveryPoint, duration, numberPointOriginal, controler));
            controler.setCurState(controler.computeState);
        }
    }
}
