package entities;

import java.util.List;

/**
 *
 * @author PLD-HEXA-301
 *
 * The class itinerary contains the data of a delivery tour
 */
public class Itinerary {

    /**
     * The list of the delivery point which make up the delivery tour
     */
    List<DeliveryPoint> generalPath;

    /**
     * The list of the coordinate which make up the delivery tour
     */
    List<Coordinate> detailedPath;

    /**
     * Creates an itinerary with the attributes generalPath and detailedPath
     * initialized
     *
     * @param generalPath
     * @param detailedPath
     */
    public Itinerary(List<DeliveryPoint> generalPath, List<Coordinate> detailedPath) {
        super();
        this.generalPath = generalPath;
        this.detailedPath = detailedPath;
    }

    /**
     * Default constructor of Itinerary
     */
    public Itinerary() {

    }

    /**
     * Gets the list of delivery points of the itinerary
     *
     * @return a list of DeliveryPoint
     */
    public List<DeliveryPoint> getGeneralPath() {
        return generalPath;
    }

    /**
     * Gets the list of coordinates of the itinerary
     *
     * @return a list of Coordinate
     */
    public List<Coordinate> getDetailedPath() {
        return detailedPath;
    }

    /**
     * Sets the generalPath
     *
     * @param generalPath the list of DeliveryPoint which make up the general
     * path of the itinerary
     */
    public void setGeneralPath(List<DeliveryPoint> generalPath) {
        this.generalPath = generalPath;
    }

    /**
     * Sets the detailedPath
     *
     * @param detailedPath the list of Coordinate which make up the detailed
     * path of the itinerary
     */
    public void setDetailedPath(List<Coordinate> detailedPath) {
        this.detailedPath = detailedPath;
    }

    /**
     * Overrides the {@code toString} method
     *
     * @return the data of the attribut in the form of a String
     */
    @Override
    public String toString() {
        return "Itinerary [generalPath=" + generalPath + ", detailedPath=" + detailedPath + "]";
    }

}
