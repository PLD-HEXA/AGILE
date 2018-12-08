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
public class DeleteState extends DefaultState {
    
    // Ajouter un bouton (inputView) pour supprimer. Au clique on rentre dans l'état
    // DeleteState. Si il il clique sur une icone, on doCmd pour enlever ce point
    // Et si il fait annuler on undo
    
    // Faire la meme chose que pour mouseListener mais une fois qu'on est dans cet état
    @Override
    public void mouseClick(Controler controler, MainWindow mainWindow, CmdList cmdList, int x,int y) {
            System.out.println("X : " + x);
            System.out.println("Y : " + y);
            double latitude = mainWindow.getGraphicalView().getLatMax()-(y+mainWindow.getGraphicalView().getPointradius())/mainWindow.getGraphicalView().getWidthScale();
            double longitude =mainWindow.getGraphicalView().getLongMax()-(mainWindow.getGraphicalView().getWidth()-x-mainWindow.getGraphicalView().getPointradius())/mainWindow.getGraphicalView().getHeightScale();
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
                    cmdList.add(new CmdDeleteDeliveryPoint(mainWindow,nearestDeliveryPoint));
                    controler.setCurState(controler.computeState);
            }
    }
}
