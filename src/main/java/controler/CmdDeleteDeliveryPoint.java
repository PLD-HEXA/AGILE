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
public class CmdDeleteDeliveryPoint implements Command  {

    private MainWindow mainWindow;
    private int indexDeliveryPoint;
    private Pair<Integer,Integer> itineraryIndex;
    private Controler controler;
    
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
