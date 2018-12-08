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
public class CmdDeleteDeliveryPoint implements Command  {

    private MainWindow mainWindow;
    private int indexDeliveryPoint;
    
    public CmdDeleteDeliveryPoint(MainWindow mainWindow, int indexDeliveryPoint) {
        this.mainWindow = mainWindow;
        this.indexDeliveryPoint = indexDeliveryPoint;
    }
    
    @Override
    public void doCmd() {
        mainWindow.getGraphicalView().getIndexToDelete().add(indexDeliveryPoint);
        mainWindow.getGraphicalView().repaint();
        mainWindow.showInformationDeleteState("You have deleted the delivery point.");
    }

    @Override
    public void undoCmd() {
        int indexDeliveryPointToAdd = mainWindow.getGraphicalView().getIndexToDelete().indexOf(this.indexDeliveryPoint);
        mainWindow.getGraphicalView().getIndexToDelete().remove(indexDeliveryPointToAdd);
        mainWindow.getGraphicalView().repaint();
    }
    
}
