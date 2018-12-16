package entities;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import java.util.Objects;

/**
 * @author PLD-HEXA-301
 *
 * The {@code Livraison} class represents an object that has the same structure
 * as the element represented by the {@code Livraison} tag in the parsed
 * DeliveryPoints XML file.
 */
public class Livraison {

    /**
     * Contains the delivery address represented by the {@code adresse}
     * attribute of the tag {@code Livraison}
     */
    @JacksonXmlProperty(localName = "adresse", isAttribute = true)
    private String adresse;

    /**
     * Contains the delivery time represented by the {@code duree} attribute of
     * the {@code Livraison} tag
     */
    @JacksonXmlProperty(localName = "duree", isAttribute = true)
    private Integer duree;

    /**
     * Default constructor of {@code Livraison}
     */
    public Livraison() {
    }

    /**
     * Constructor of {@code Livraison} object that represents an object with
     * the attributes {@code addresse} and {@code duree} initialized.
     *
     * @param adresse The {@code adresse} attribute
     * @param duree The {@code duree} attribute
     */
    public Livraison(String adresse, Integer duree) {
        this.adresse = adresse;
        this.duree = duree;
    }

    /**
     * Gets the value of the {@code adresse} attribute
     *
     * @return a string that represents the delivery address (id of the
     * delivery)
     */
    public String getId() {
        return adresse;
    }

    /**
     * Sets the value of the {@code addresse} attribute
     *
     * @param adresse New value of attribute {@code adresse}
     */
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    /**
     * Gets the value of the {@code duree} attribute
     *
     * @return an integer that represents the duration of the delivery
     */
    public Integer getDuree() {
        return duree;
    }

    /**
     * Sets the value of the {@code duree} attribute
     *
     * @param duree New value of attribute {@code duree}
     */
    public void setDuree(Integer duree) {
        this.duree = duree;
    }

    /**
     * Overrides of the {@code toString} method
     *
     * @return the different data of the object in a String format
     */
    @Override
    public String toString() {
        return "Noeud [adresse=" + adresse + ", duree=" + duree + "]";
    }

    /**
     * Overrides of the {@code equals} method
     *
     * @param obj
     * 
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
        final Livraison other = (Livraison) obj;
        if (!Objects.equals(this.adresse, other.adresse)) {
            return false;
        }
        if (!Objects.equals(this.duree, other.duree)) {
            return false;
        }
        return true;
    }
}
