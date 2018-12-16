package entities;

import java.util.Arrays;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.util.Objects;

/**
 * @author PLD-HEXA-301
 *
 * The class {@code DeliveryRequest} represents an object that has the same
 * structure as the analyzed XML DeliveryPoints file. The parsed XML file has a
 * root tag {@code DeliveryRequest}
 */
@JacksonXmlRootElement(localName = "demandeDeLivraisons")
public class DeliveryRequest {

    /**
     * Contains element represented by the tag {@code warehouse}
     */
    @JacksonXmlElementWrapper(localName = "entrepot", useWrapping = false)
    private Warehouse warehouse;

    /**
     * Contains elements represented by the tag {@code delivery}
     */
    @JacksonXmlElementWrapper(localName = "livraison", useWrapping = false)
    private Delivery[] delivery;

    /**
     * TODO Function never used
     * Default constructor of {
     *
     * @DemandeDeLivraisons}.
     */
    public DeliveryRequest() {
    }

    /**
     * TODO Function never used
     * Creates an object {@code DeliveryRequest} with the {@code warehouse}
     * and {@code delivery} attributes initialized.
     *
     * @param warehouse The {@code warehouse} attribute
     * @param delivery The {@code delivery} attribute
     */
    public DeliveryRequest(Warehouse warehouse, Delivery[] delivery) {
        this.warehouse = warehouse;
        this.delivery = delivery;
    }

    /**
     * Gets the value of the {@code warehouse} attribute      s
     *
     * @return the attribute warehouse that contains the data of the warehouse
     *     
     */
    public Warehouse getWarehouse() {
        return warehouse;
    }

    /**
     * Sets the value of the attribute {@code warehouse}     
     *
     * @param warehouse New value of {@code warehouse} attribute    
     */
    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    /**
     * Gets the value of the {@code delivery} attribute
     *
     * @return a table that contains all deliveries of the object
     * {@code DeliveryRequest}      
     */
    public Delivery[] getDelivery() {
        return delivery;
    }

    /**
     * Sets the value of the {@code delivery} attribute
     *
     * @param delivery New value of the {@code delivery} attribute
     */
    public void setDelivery(Delivery[] delivery) {
        this.delivery = delivery;
    }

    /**
     * Overrides of the {@code toString} method
     *
     * @return the different data of the object in a string format
     */
    @Override
    public String toString() {
        return "Network [warehouse=" + warehouse + ", delivery=" + Arrays.toString(delivery) + "]";
    }

    /**
     * Overrides of the {@code equals}
     *
     * @param obj
     * @return true if the object is equal, false otherwise
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
        final DeliveryRequest other = (DeliveryRequest) obj;
        if (!Objects.equals(this.warehouse, other.warehouse)) {
            return false;
        }
        return Arrays.deepEquals(this.delivery, other.delivery);
    }
}
