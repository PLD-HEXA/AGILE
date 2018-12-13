/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controler;

import entities.Itinerary;
import entities.algorithms.PathFinder;
import java.util.List;
import javafx.util.Pair;
import view.MainWindow;

/**
 *
 * @author Chris
 */
public class CmdAddDeliveryPoint implements Command {

    Controler controler;
    private MainWindow mainWindow;
    private int numberPointAdd;
    private Pair<Integer,Integer> newDeliveryPoint;
    
    public CmdAddDeliveryPoint(MainWindow mainWindow, int indexNewDeliveryPoint, int duration, int numberPointOriginal, Controler controler) {
        this.mainWindow = mainWindow;
        this.newDeliveryPoint = newDeliveryPoint = new Pair<>(indexNewDeliveryPoint, duration);
        this.numberPointAdd = numberPointOriginal+1;
        this.controler = controler;
    }

    @Override
    public void doCmd() {
        // We increase the number of point added
        controler.getAddState().addNumberPoint();
        // We add the pair of the newDeliveryPoint
        mainWindow.getGraphicalView().getMap().getTabDeliveryPoints().add(newDeliveryPoint);
        
        // We compute the new itinerary with the new number of points added
        List<Itinerary> itineraries = mainWindow.getGraphicalView().getItineraries();
        boolean addItinerary = controler.getPathFinder().findAdditionalPath(mainWindow.getGraphicalView().getMap(), itineraries,
                                numberPointAdd, false);
        
        if(addItinerary == true) {
                mainWindow.getGraphicalView().setItineraryIndex(null);
                mainWindow.getGraphicalView().repaint();
                mainWindow.getTextualView().displayListOfRounds();
                mainWindow.getTextualView().revalidate();
                mainWindow.getTextualView().repaint();
                mainWindow.showInformationConfirmationCommand("You have added the delivery point. A new round has been added"
                        + " and the first delivery man to finish will do it.");
        }
        else {
                mainWindow.showError("Error when calculating"
                        + " routes");
        }
    }

    @Override
    public void undoCmd() {
        // We decrease the number of point added
        controler.getAddState().soustractNumberPoint();
        // We remove the pair of the newDeliveryPoint
        int delPointToDelete = mainWindow.getGraphicalView().getMap().getTabDeliveryPoints().indexOf(newDeliveryPoint);
        mainWindow.getGraphicalView().getMap().getTabDeliveryPoints().remove(delPointToDelete);
        // We compute the new itinerary added without the del point deleted
        List<Itinerary> itineraries = mainWindow.getGraphicalView().getItineraries();
        boolean addItinerary = controler.getPathFinder().findAdditionalPath(mainWindow.getGraphicalView().getMap(), itineraries,numberPointAdd-1, true);
        
        if(addItinerary == true) {
                mainWindow.getGraphicalView().setItineraryIndex(null);
                mainWindow.getGraphicalView().repaint();
                
                mainWindow.getTextualView().displayListOfRounds();
                mainWindow.getTextualView().revalidate();
                mainWindow.getTextualView().repaint();
        }
        else {
                mainWindow.showError("Error when calculating"
                        + " routes");
        }
    }
    
}
