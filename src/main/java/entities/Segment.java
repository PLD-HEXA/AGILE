package entities;

import java.util.Objects;

/**
 *
 * @author PLD-HEXA-301
 *
 * The class Segment represents a segment on the city plan
 */
public class Segment {

    /**
     * The index of the destination
     */
    private int destIndex;

    /**
     * The name of the street
     */
    private String streetName;

    /**
     * The length of the segment
     */
    private double length;

    /**
     * Creates a Segment with the attributes destIndex, streetName and length
     * initialized
     *
     * @param destIndex
     * @param streetName
     * @param length
     */
    public Segment(int destIndex, String streetName, double length) {
        this.destIndex = destIndex;
        this.streetName = streetName;
        this.length = length;
    }

    /**
     * Overrides the {@code toString} method
     *
     * @return the data of the attribut in the form of a String
     */
    @Override
    public String toString() {
        return "Segment{"
                + "destId=" + destIndex
                + ", streetName='" + streetName + '\''
                + ", length=" + length
                + '}';
    }

    /**
     * Gets the index of the destination
     *
     * @return an int
     */
    public int getDestIndex() {
        return destIndex;
    }

    /**
     * Sets the index of the destination
     *
     * @param destIndex an int which represents the index of the destination
     */
    public void setDestIndex(int destIndex) {
        this.destIndex = destIndex;
    }

    /**
     * Gets the name of the street
     *
     * @return a String
     */
    public String getStreetName() {
        return streetName;
    }

    /**
     * Sets the name of the street
     *
     * @param streetName a String which represents the name of the street
     */
    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    /**
     * Gets the length of the Segment
     *
     * @return a double which represents the length of the segment
     */
    public double getLength() {
        return length;
    }

    /**
     * Sets the length of the segment
     *
     * @param length a double
     */
    public void setLength(double length) {
        this.length = length;
    }

    /**
     * Overrides the {@code equals} method
     *
     * @param obj
     *
     * @return true if the param obj is equal
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Segment other = (Segment) obj;
        if (this.destIndex != other.destIndex) {
            return false;
        }
        if (Double.doubleToLongBits(this.length) != Double.doubleToLongBits(other.length)) {
            return false;
        }
        if (!Objects.equals(this.streetName, other.streetName)) {
            return false;
        }
        return true;
    }

}