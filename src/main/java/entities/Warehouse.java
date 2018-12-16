package entities;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.Objects;

/**
 * The {@code Warehouse} class represents an object that has the same structure
 * as the element represented by the tag {@code entrepot} in the parsed
 * DeliveryPoints XML file.
 *
 * @author PLD-HEXA-301
 */
public class Warehouse {
    /**
     * Contains the warehouse address (id) represented by the {@code address}
     * attribute of the tag {@code warehouse}
     */
    @JacksonXmlProperty(localName = "address", isAttribute = true)
    private String address;

    /**
     * Contains the departure time represented by the {@code departureHour}
     * attribute of the tag {@code warehouse}
     */
    @JacksonXmlProperty(localName = "departureHour", isAttribute = true)
    private String departureHour;

    /**
     * TODO Function never used
     * Default constructor of {@code Warehouse}
     */
    public Warehouse() {
    }

    /**
     * Constructor of {@code Warehouse} with the attributes {@code address} and
     * {@code departureHour} initialized.
     *
     * @param address       The {@code address} attribute
     * @param departureHour the attribute {@code departureHour}
     */
    public Warehouse(String address, String departureHour) {
        this.address = address;
        this.departureHour = departureHour;
    }

    /**
     * Gets the value of the {@code address} attribute
     *
     * @return a string that represents the address (id) of the warehouse
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the value of the {@code address} attribute
     *
     * @param address NewValue of attribute {@code address}
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets the value of the attribute {@code departureHour}
     *
     * @return a string that represents the departure time of the warehouse.
     */
    public String getDepartureHour() {
        return departureHour;
    }

    /**
     * Sets the value of the attribute {@code departureHour}
     *
     * @param departureHour New value of the attribute {@code departureHour}
     */
    public void setDepartureHour(String departureHour) {
        this.departureHour = departureHour;
    }

    /**
     * Overrides of the {@code toString} method
     *
     * @return the different data of the object in a String format
     */
    @Override
    public String toString() {
        return "Warehouse{" + "address=" + address + ", departureHour=" + departureHour + '}';
    }

    /**
     * Overrides of the {@code equals} method
     *
     * @param obj
     * @return true if obj is equal, false otherwise
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
        final Warehouse other = (Warehouse) obj;
        if (!Objects.equals(this.address, other.address)) {
            return false;
        }
        return Objects.equals(this.departureHour, other.departureHour);
    }
}
