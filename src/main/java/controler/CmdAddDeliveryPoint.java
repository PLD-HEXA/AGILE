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

    PathFinder pathFinder;
    private MainWindow mainWindow;
    private int indexNewDeliveryPoint;
    private int duration;
    private int numberPointAdd;
    
    public CmdAddDeliveryPoint(MainWindow mainWindow, int indexNewDeliveryPoint, int duration, PathFinder pathFinder) {
        this.mainWindow = mainWindow;
        this.indexNewDeliveryPoint = indexNewDeliveryPoint;
        this.duration = duration;
        this.numberPointAdd = 0;
        this.pathFinder = pathFinder;
    }

    
    @Override
    public void doCmd() {
        numberPointAdd++;
        Pair<Integer,Integer> newDeliveryPoint = new Pair<>(indexNewDeliveryPoint, duration);
        mainWindow.getGraphicalView().getMap().getTabDeliveryPoints().add(newDeliveryPoint);
        List<Itinerary> itineraries = mainWindow.getGraphicalView().getItineraries();
        boolean addItinerary = pathFinder.findAdditionalPath(itineraries, mainWindow.getGraphicalView().getMap(),
                                numberPointAdd);
        if(addItinerary == true) {
                //mainWindow.getGraphicalView().setItineraries(itineraries);
                mainWindow.getGraphicalView().setNearestDeliveryPoint(null);
                mainWindow.getGraphicalView().repaint();
                mainWindow.getTextualView().setItineraries(itineraries);
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

    @Override
    public void undoCmd() {
        numberPointAdd--;
        int indexToDelete = mainWindow.getGraphicalView().getMap().getTabDeliveryPoints().size();
        List<Itinerary> itineraries = mainWindow.getGraphicalView().getItineraries();
        mainWindow.getGraphicalView().getMap().getTabDeliveryPoints().remove(indexToDelete);
        boolean addItinerary = pathFinder.findAdditionalPath(itineraries, mainWindow.getGraphicalView().getMap(),
                                numberPointAdd);
        if(addItinerary == true) {
                //mainWindow.getGraphicalView().setItineraries(itineraries);
                mainWindow.getGraphicalView().setNearestDeliveryPoint(null);
                mainWindow.getGraphicalView().setNearestDeliveryPoint(null);
                mainWindow.getGraphicalView().repaint();
                mainWindow.getTextualView().setItineraries(itineraries);
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
