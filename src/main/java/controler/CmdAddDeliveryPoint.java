package controler;

import entities.Itinerary;

import java.util.List;

import javafx.util.Pair;
import view.MainWindow;

/**
 * Represents the command of adding a delivery point.
 *
 * @author PLD-HEXA-301
 */
public class CmdAddDeliveryPoint implements Command {

    /**
     * The controller.
     */
    Controller controller;
    /**
     * The main window.
     */
    private MainWindow mainWindow;
    /**
     * The number of points added.
     */
    private int numberPointAdd;
    /**
     * Pair representing the new delivery point.
     * The key represents the index of the number to add, the value represents the duration of the new delivery.
     */
    private Pair<Integer, Integer> newDeliveryPoint;
    /**
     * Allows to know whether the point has been added or not
     */
    private boolean addPoint;

    /**
     * Constructor
     *
     * @param mainWindow
     * @param indexNewDeliveryPoint
     * @param duration
     * @param numberPointOriginal
     * @param controller
     */
    public CmdAddDeliveryPoint(MainWindow mainWindow, int indexNewDeliveryPoint, int duration, int numberPointOriginal, Controller controller) {
        this.mainWindow = mainWindow;
        this.newDeliveryPoint = new Pair<>(indexNewDeliveryPoint, duration);
        this.numberPointAdd = numberPointOriginal + 1;
        this.controller = controller;
    }

    @Override
    public void doCmd() {
        
        // We add the pair of the newDeliveryPoint
        mainWindow.getGraphicalView().getMap().getTabDeliveryPoints().add(newDeliveryPoint);

        // We compute the new itinerary with the new number of points added
        List<Itinerary> itineraries = mainWindow.getGraphicalView().getItineraries();
        addPoint = controller.getPathFinder().findAdditionalPath(mainWindow.getGraphicalView().getMap(), itineraries,
                numberPointAdd, false);

        if (addPoint) {
            // We increase the number of point added
            controller.getAddState().addNumberPoint();
            mainWindow.getGraphicalView().setItineraryIndex(null);
            mainWindow.getGraphicalView().repaint();
            mainWindow.getTextualView().displayListOfRounds();
            mainWindow.getTextualView().revalidate();
            mainWindow.getTextualView().repaint();
            mainWindow.showInformationConfirmationCommand("You have added the delivery point. A new round has been added"
                    + " and the first delivery man to finish will do it.");
        } else {
            int delPointToDelete = mainWindow.getGraphicalView().getMap().getTabDeliveryPoints().indexOf(newDeliveryPoint);
            mainWindow.getGraphicalView().getMap().getTabDeliveryPoints().remove(delPointToDelete);
            mainWindow.showError("Error when calculating"
                    + " routes");
        }
    }

    @Override
    public void undoCmd() {
        if (addPoint) {
            // We decrease the number of point added
            controller.getAddState().subtractPointNumber();
            // We remove the pair of the newDeliveryPoint
            int delPointToDelete = mainWindow.getGraphicalView().getMap().getTabDeliveryPoints().indexOf(newDeliveryPoint);
            mainWindow.getGraphicalView().getMap().getTabDeliveryPoints().remove(delPointToDelete);
            // We compute the new itinerary added without the del point deleted
            List<Itinerary> itineraries = mainWindow.getGraphicalView().getItineraries();
            boolean addItinerary = controller.getPathFinder().findAdditionalPath(mainWindow.getGraphicalView().getMap(), itineraries, numberPointAdd - 1, true);
            if (addItinerary) {
                mainWindow.getGraphicalView().setItineraryIndex(null);
                mainWindow.getGraphicalView().repaint();

                mainWindow.getTextualView().displayListOfRounds();
                mainWindow.getTextualView().revalidate();
                mainWindow.getTextualView().repaint();
            } else {
                mainWindow.showError("Error when calculating routes");
            }
        }
    }
}
