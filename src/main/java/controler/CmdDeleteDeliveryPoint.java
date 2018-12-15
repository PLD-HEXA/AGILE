/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controler;

import javafx.util.Pair;
import view.MainWindow;

/**
 * Represents the command of deleting a delivery point.
 * @author PLD-HEXA-301
 */
public class CmdDeleteDeliveryPoint implements Command  {

	 /**
     * The controler.
     */
    Controler controler;
    /**
     * The main window.
     */
    private MainWindow mainWindow;
	/**
	 * The index of the delivery point to delete.
	 */
	private int indexDeliveryPoint;
    /**
     * A pair representing the delivery point to delete.
     * The key represents the index of the itinerary containing the delivery point.
     * The value represents the index of the delivery point inside the itinerary.
     */
    private Pair<Integer,Integer> itineraryIndex;
    
    /**
     * Constructor
     * @param mainWindow
     * @param indexDeliveryPoint
     * @param controler
     * @param itineraryIndex
     */
    public CmdDeleteDeliveryPoint(MainWindow mainWindow, int indexDeliveryPoint, Controler controler, Pair<Integer,Integer> itineraryIndex) {
        this.mainWindow = mainWindow;
        this.indexDeliveryPoint = indexDeliveryPoint;
        this.itineraryIndex = itineraryIndex;
        this.controler = controler;
    }
    
    @Override
    public void doCmd() {
        controler.getDeleteState().addNumberDeliveryPointDeleted();
        
        mainWindow.getGraphicalView().getIndexToDelete().add(indexDeliveryPoint);
        mainWindow.getGraphicalView().repaint();
        
        mainWindow.getTextualView().getIndexItineraryToDelete().add(itineraryIndex);
        mainWindow.getTextualView().displayListOfRounds();
        
        mainWindow.showInformationConfirmationCommand("You have deleted the delivery point.");
    }

    @Override
    public void undoCmd() {
        controler.getDeleteState().soustractNumberDeliveryPointDeleted();
        
        int indexDeliveryPointToAdd = mainWindow.getGraphicalView().getIndexToDelete().indexOf(this.indexDeliveryPoint);
        mainWindow.getGraphicalView().getIndexToDelete().remove(indexDeliveryPointToAdd);
        
        int indexItineraryPointToAdd = mainWindow.getTextualView().getIndexItineraryToDelete().indexOf(this.itineraryIndex);
        mainWindow.getTextualView().getIndexItineraryToDelete().remove(indexDeliveryPointToAdd);
        
        mainWindow.getGraphicalView().repaint();
        
        mainWindow.getTextualView().displayListOfRounds();
    }
    
}
