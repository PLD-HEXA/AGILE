package entities;

import java.util.Date;

/**
 *
 * @author PLD-HEXA-301
 *
 * The class DeliveryPoint contains all the data necessary to identify a
 * delivery point and the time spent in order to deliver the object
 */
public class DeliveryPoint {

    /**
     * Contains the longitude and latitude of the delivery point
     */
    private Coordinate coordinate;

    /**
     * Arrival time at the delivery point
     */
    private Date arrivalTime;

    /**
     * Departure time at the delivery point
     */
    private Date departureTime;

    /**
     * Creates a delivery point with the attributes coordinate, arrivalTime and
     * departureTime initialized.
     *
     * @param coordinate
     * @param arrivalTime
     * @param departureTime
     */
    public DeliveryPoint(Coordinate coordinate, Date arrivalTime, Date departureTime) {
        this.coordinate = coordinate;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
    }

    /**
     * Gets the coodinate of the delivery point
     *
     * @return the coordinate of the delivery point
     */
    public Coordinate getCoordinate() {
        return coordinate;
    }

    /**
     * Sets the coordinate of the delivery point
     *
     * @param coordinate The coordinate of the delivery point
     */
    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    /**
     * Gets the arrival time at the delivery point
     *
     * @return a Date with the format hh:mm:ss
     */
    public Date getArrivalTime() {
        return arrivalTime;
    }

    /**
     * Sets the arrival time at the delivery point
     *
     * @param arrivalTime a Date in format hh:mm:ss
     */
    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    /**
     * Gets the departure time at the delivery point
     *
     * @return a Date with the format hh:mm:ss
     */
    public Date getDepartureTime() {
        return departureTime;
    }

    /**
     * Sets the departure time at the delivery point
     *
     * @param departureTime a Date in format hh:mm:ss
     */
    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    /**
     * Overrides the {@code toString} method
     *
     * @return the data of the attribut in the form of a String
     */
    @Override
    public String toString() {
        return "DeliveryPoint [coordinate=" + coordinate + ", arrivalTime=" + arrivalTime + ", departureTime="
                + departureTime + "]";
    }

}
