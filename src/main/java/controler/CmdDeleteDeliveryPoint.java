package controler;

import javafx.util.Pair;
import view.MainWindow;

/**
 * Represents the command of deleting a delivery point.
 *
 * @author PLD-HEXA-301
 */
public class CmdDeleteDeliveryPoint implements Command {

    /**
     * The controller.
     */
    private Controller controller;

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
    private Pair<Integer, Integer> itineraryIndex;

    /**
     * Constructor
     *
     * @param mainWindow
     * @param indexDeliveryPoint
     * @param controller
     * @param itineraryIndex
     */
    public CmdDeleteDeliveryPoint(MainWindow mainWindow, int indexDeliveryPoint, Controller controller, Pair<Integer, Integer> itineraryIndex) {
        this.mainWindow = mainWindow;
        this.indexDeliveryPoint = indexDeliveryPoint;
        this.itineraryIndex = itineraryIndex;
        this.controller = controller;
    }

    @Override
    public void doCmd() {
        controller.getDeleteState().addNumberDeliveryPointDeleted();

        mainWindow.getGraphicalView().getIndexToDelete().add(indexDeliveryPoint);
        mainWindow.getGraphicalView().repaint();

        mainWindow.getTextualView().getIndexItineraryToDelete().add(itineraryIndex);
        mainWindow.getTextualView().displayListOfRounds();

        mainWindow.showInformationConfirmationCommand("You have deleted the delivery point.");
    }

    @Override
    public void undoCmd() {
        controller.getDeleteState().subtractNumberDeliveryPointDeleted();

        int indexDeliveryPointToAdd = mainWindow.getGraphicalView().getIndexToDelete().indexOf(this.indexDeliveryPoint);
        mainWindow.getGraphicalView().getIndexToDelete().remove(indexDeliveryPointToAdd);

        // TODO: the variable indexItineraryPointToAdd is never used
        int indexItineraryPointToAdd = mainWindow.getTextualView().getIndexItineraryToDelete().indexOf(this.itineraryIndex);
        mainWindow.getTextualView().getIndexItineraryToDelete().remove(indexDeliveryPointToAdd);

        mainWindow.getGraphicalView().repaint();

        mainWindow.getTextualView().displayListOfRounds();
    }

}
