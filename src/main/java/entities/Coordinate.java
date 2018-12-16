package entities;

import java.util.Objects;

/**
 *
 * @author PLD-HEXA-301
 *
 * The {@code Coordinate} class represents an object that encapsulates the
 * {@code latitude} and {@code longitude} attributes of the {@code Node} tag in
 * the parsed XML file.
 *
 */
public class Coordinate {

    /**
     * Contains the longitude of the node represented by the {@code longitude}
     * attribute of the tag {@code Node}
     */
    private Double longitude;

    /**
     * Contains the latitude of the node represented by the {@code longitude}
     * attribute of the tag {@code Node}
     */
    private Double latitude;

    /**
     * Creates a {@code Coordinate} object.
     */
    public Coordinate() {
    }

    /**
     * Creates a {@code Coordinate} object with the attributes {@code longitude}
     * and {@code latitude} initialized.
     *
     * @param longitude
     * @param latitude
     */
    public Coordinate(Double longitude, Double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    /**
     * Overrides the {@code toString} method
     *
     * @return the data of the attribut in the form of a String
     */
    @Override
    public String toString() {
        return "Coordinate{"
                + "longitude=" + longitude
                + ", latitude=" + latitude
                + '}';
    }

    /**
     * Gets the value of the {@code longitude} attribute
     *
     * @return a real number {
     * @Double Code} that represents the longitude.
     */
    public Double getLongitude() {
        return longitude;
    }

    /**
     * Sets the value of the {@code longitude} attribute
     *
     * @param longitude New attribute value {@code longitude}
     */
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    /**
     * Gets the value of the attribute {@code latitude}
     *
     * @return a real number {@code Double} that represents the latitude.
     */
    public Double getLatitude() {
        return latitude;
    }

    /**
     * Sets the value of the {@code latitude} attribute
     *
     * @param latitude New attribute value {@code latitude}
     */
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    /**
     * Overrides the {@code equals} method
     *
     * @param obj
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
        final Coordinate other = (Coordinate) obj;
        if (!Objects.equals(this.longitude, other.longitude)) {
            return false;
        }
        if (!Objects.equals(this.latitude, other.latitude)) {
            return false;
        }
        return true;
    }
}
