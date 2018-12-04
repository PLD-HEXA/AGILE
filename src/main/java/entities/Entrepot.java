package entities;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import java.util.Objects;

/**
 * @author PLD-HEXA-301
 * 
 * The {@code Entrepot} class represents an object that has the same structure as the element
 * represented by the tag {@code entrepot} in the parsed DeliveryPoints XML file.
 *
 */
public class Entrepot    {
	
    /**
     * Contains the warehouse address (id) represented by the {@code adresse} 
     * attribute of the tag {@code entrepot} 
     */
    @JacksonXmlProperty(localName = "adresse", isAttribute = true)
    private String adresse;
	
    /**
     * Contains the departure time represented by the
     * {@code heureDepart} attribute of the tag {@code entrepot}
     */
    @JacksonXmlProperty(localName = "heureDepart", isAttribute = true)
    private String heureDepart;

    /**
     * Default constructor of {@code Entrepot}
     */
    public Entrepot() {
    }
	
    /**
    * Constructor of {@code Entrepot} with the attributes
    * {@code address} and {@code heureDepart} initialized.
    *
    * @param adresse The {@code address} attribute
    * @param heureDepart the attribute {@code heureDepart}
    */
    public Entrepot(String adresse, String heureDepart) {
            this.adresse = adresse;
            this.heureDepart = heureDepart;
    }

    /**
     * Gets the value of the {@code adresse} attribute
     * 
     * @return a string that represents the address (id) of the warehouse
     */
    public String getAdresse() {
            return adresse;
    }

    /**
     * Sets the value of the {@code adresse} attribute
     *
     * @param adresse New value of attribute {@code adresse}
     */
    public void setAdresse(String adresse) {
            this.adresse = adresse;
    }

    /**
     * Gets the value of the attribute {@ heureDepart}
     * 
     * @return a string that represents the departure time of the warehouse.
     */
    public String getHeureDepart() {
            return heureDepart;
    }
	
    /**
     * Sets the value of the attribute {@ heureDepart}
     * @param heureDepart  New value of the attribute {@ heureDepart}
     */
    public void setHeureDepart(String heureDepart) {
            this.heureDepart= heureDepart;
    }

    /**
     * Overrides of the {@code toString} method 
     * 
     * @return the different data of the object in a string format
     */
    @Override
    public String toString() {
        return "Entrepot{" + "adresse=" + adresse + ", heureDepart=" + heureDepart + '}';
    }

    /**
     * Overrides of the {@code equals} method
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
        final Entrepot other = (Entrepot) obj;
        if (!Objects.equals(this.adresse, other.adresse)) {
            return false;
        }
        if (!Objects.equals(this.heureDepart, other.heureDepart)) {
            return false;
        }
        return true;
    }
}
