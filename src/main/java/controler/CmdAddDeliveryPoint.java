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
    private int indexNewDeliveryPoint;
    private int duration;
    private int numberPointAdd;
    
    public CmdAddDeliveryPoint(MainWindow mainWindow, int indexNewDeliveryPoint, int duration, int numberPointOriginal, Controler controler) {
        this.mainWindow = mainWindow;
        this.indexNewDeliveryPoint = indexNewDeliveryPoint;
        this.duration = duration;
        this.numberPointAdd = numberPointOriginal;
        this.controler = controler;
    }

    
    @Override
    public void doCmd() {
        controler.getAddState().addNumberPoint();
        Pair<Integer,Integer> newDeliveryPoint = new Pair<>(indexNewDeliveryPoint, duration);
        mainWindow.getGraphicalView().getMap().getTabDeliveryPoints().add(newDeliveryPoint);
        List<Itinerary> itineraries = mainWindow.getGraphicalView().getItineraries();
        boolean addItinerary = controler.getPathFinder().findAdditionalPath(mainWindow.getGraphicalView().getMap(), itineraries,
                                (numberPointAdd+1));
        if(addItinerary == true) {
                //mainWindow.getGraphicalView().setItineraries(itineraries);
                mainWindow.getGraphicalView().setItineraryIndex(null);
                mainWindow.getGraphicalView().setDeliveryPointIndex(null);
                mainWindow.getGraphicalView().repaint();
                mainWindow.getTextualView().setItineraries(itineraries);
                mainWindow.getTextualView().setItineraryIndex(null);
                mainWindow.getTextualView().setDeliveryPointIndex(null);
                mainWindow.getTextualView().displayListOfRounds();
                mainWindow.getTextualView().revalidate();
                mainWindow.getTextualView().repaint();
                mainWindow.showInformationConfirmationCommand("You have added the delivery point. A new round has been added"
                        + " and the first delivery man to finish will do it.");
                // On est deja dans cet etat
                // controler.setCurState(controler.computeState);
        }
        else {
                //TODO : pop up erreur de calcul
                mainWindow.showError("Error when calculating"
                        + " routes");
        }
    }

    @Override
    public void undoCmd() {
        controler.getAddState().soustractNumberPoint();
        int indexToDelete = mainWindow.getGraphicalView().getMap().getTabDeliveryPoints().size();
        mainWindow.getGraphicalView().getItineraries().remove(mainWindow.getGraphicalView().getItineraries().size()-1-numberPointAdd);
        mainWindow.getGraphicalView().getMap().getTabDeliveryPoints().remove(indexToDelete-1);
        boolean addItinerary  = true;
                //controler.getPathFinder().findAdditionalPath(mainWindow.getGraphicalView().getMap(), itineraries,
                   //                 numberPointAdd);
        if(addItinerary == true) {
                //mainWindow.getGraphicalView().setItineraries(itineraries);
                mainWindow.getGraphicalView().setItineraryIndex(null);
                mainWindow.getGraphicalView().setDeliveryPointIndex(null);
                mainWindow.getGraphicalView().repaint();
                mainWindow.getTextualView().setItineraries(mainWindow.getGraphicalView().getItineraries());
                mainWindow.getTextualView().setItineraryIndex(null);
                mainWindow.getTextualView().setDeliveryPointIndex(null);
                mainWindow.getTextualView().displayListOfRounds();
                mainWindow.getTextualView().revalidate();
                mainWindow.getTextualView().repaint();
                // On est deja dans ce etat
                // controler.setCurState(controler.computeState);
        }
        else {
                //TODO : pop up erreur de calcul
                mainWindow.showError("Error when calculating"
                        + " routes");
        }
    }
    
}
