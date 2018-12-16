package controler;

import entities.Itinerary;

import java.util.List;

import javafx.util.Pair;
import view.GraphicalView;
import view.MainWindow;

/**
 * Represents the state after the button has clicked on the delete button.
 *
 * @author PLD-HEXA-301
 */
public class DeleteState extends DefaultState {

    /**
     * Number of points deleted.
     */
    private int numberDeliveryPointDeleted;

    /**
     * Constructor.
     */
    public DeleteState() {
        this.numberDeliveryPointDeleted = 0;
    }

    /**
     * TODO Function never used
     * Returns numberDeliveryPointDeleted.
     *
     * @return numberDeliveryPointDeleted
     */
    public int getNumberDeliveryPointDeleted() {
        return numberDeliveryPointDeleted;
    }

    /**
     * TODO Function never used
     * Sets numberDeliveryPointDeleted to the given value.
     *
     * @param numberDeliveryPointDeleted
     */
    public void setNumberDeliveryPointDeleted(int numberDeliveryPointDeleted) {
        this.numberDeliveryPointDeleted = numberDeliveryPointDeleted;
    }

    /**
     * Increments numberDeliveryPointDeleted.
     */
    public void addNumberDeliveryPointDeleted() {
        numberDeliveryPointDeleted++;
    }

    /**
     * Decrements numberDeliveryPointDeleted.
     */
    public void subtractNumberDeliveryPointDeleted() {
        numberDeliveryPointDeleted--;
    }

    @Override
    public void mouseClick(Controller controller, MainWindow mainWindow, CmdList cmdList, int x, int y) {
        x /= mainWindow.getGraphicalView().getScale(); // Allows to get the x and y corresponding when the
        // user has zoomed
        y /= mainWindow.getGraphicalView().getScale();
        double latitude = mainWindow.getGraphicalView().getLatMax()
                - (y + GraphicalView.getPointradius()) / mainWindow.getGraphicalView().getWidthScale();
        double longitude = mainWindow.getGraphicalView().getLongMax()
                - (mainWindow.getGraphicalView().getMapSize() - x - GraphicalView.getPointradius())
                / mainWindow.getGraphicalView().getHeightScale();
        double minDistance = 0.0062; // minimal distance to get the point clicked in the map
        // corresponding to the delivery point we want to delete in the delivery point
        // tab
        double distance;
        Integer nearestDeliveryPoint = null;
        int numberOfDeliveryPoints = mainWindow.getGraphicalView().getMap().getTabDeliveryPoints().size();
        for (int i = 0; i < numberOfDeliveryPoints; i++) {
            double curLatitude = mainWindow.getGraphicalView().getMap().getCoordinates()[mainWindow.getGraphicalView()
                    .getMap().getTabDeliveryPoints().get(i).getKey()].getLatitude();
            double curLongitude = mainWindow.getGraphicalView().getMap().getCoordinates()[mainWindow.getGraphicalView()
                    .getMap().getTabDeliveryPoints().get(i).getKey()].getLongitude();
            distance = Math.sqrt(Math.pow(latitude - curLatitude, 2) + Math.pow(longitude - curLongitude, 2));
            if (distance < minDistance) {
                minDistance = distance;
                nearestDeliveryPoint = i;
            }
        }

        if (nearestDeliveryPoint != null) {
            Pair<Integer, Integer> itineraryIndex = findItineraryPointCorresponding(mainWindow, nearestDeliveryPoint);
            if (mainWindow.getGraphicalView().getMap().getTabDeliveryPoints().size() > numberDeliveryPointDeleted) {
                cmdList.add(new CmdDeleteDeliveryPoint(mainWindow, nearestDeliveryPoint, controller, itineraryIndex));
                controller.setCurState(controller.computeState);
            } else {
                mainWindow.showError("All the delivery points have been deleted");
                controller.setCurState(controller.computeState);
            }
        }
    }

    /**
     * Finds the itinerary that includes this delivery point and the index of this
     * delivery point in the itinerary list
     *
     * @param mainWindow
     * @param nearestDeliveryPoint the index of the delivery point clicked by the
     *                             user
     * @return a pair representing the index of the itinerary in the list of
     * itinerary (first) and the index of the delivery point in the
     * itinerary (second)
     */
    public Pair<Integer, Integer> findItineraryPointCorresponding(MainWindow mainWindow, int nearestDeliveryPoint) {
        boolean globalFound = false;
        boolean localFound = false;
        int itineraryNumber = 0;
        int deliveryPointNumber = 0;
        int numberOfStops;
        List<Itinerary> itineraries = mainWindow.getGraphicalView().getItineraries();
        int numberOfItineraries = itineraries.size();
        while (!globalFound && itineraryNumber < numberOfItineraries) {
            deliveryPointNumber = 0;
            numberOfStops = itineraries.get(itineraryNumber).getGeneralPath().size();
            while (!localFound && deliveryPointNumber < numberOfStops) {
                // If it's the good delivery point
                if (itineraries.get(itineraryNumber).getGeneralPath().get(deliveryPointNumber)
                        .getCoordinate() == mainWindow.getGraphicalView().getMap().getCoordinates()[mainWindow
                        .getGraphicalView().getMap().getTabDeliveryPoints().get(nearestDeliveryPoint)
                        .getKey()]) {
                    localFound = true;
                    globalFound = true;
                } else {
                    deliveryPointNumber++;
                }
            }
            if (!localFound) {
                itineraryNumber++;
            }
        }
        Pair<Integer, Integer> result = new Pair<>(itineraryNumber, deliveryPointNumber);
        return result;
    }
}
