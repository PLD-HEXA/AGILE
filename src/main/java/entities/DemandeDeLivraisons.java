package entities;

import java.util.Arrays;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.Objects;

/**
 * The class {@code DemandeDeLivraisons} represents an object that has the same
 * structure as the analyzed XML DeliveryPoints file. The parsed XML file has a
 * root tag {@code DemandeDeLivraisons}
 *
 * @author PLD-HEXA-301
 */
@JacksonXmlRootElement(localName = "demandeDeLivraisons")
public class DemandeDeLivraisons {

    /**
     * Contains element represented by the tag {@code entrepot}
     */
    @JacksonXmlElementWrapper(localName = "entrepot", useWrapping = false)
    private Entrepot entrepot;

    /**
     * Contains elements represented by the tag {@code livraison}
     */
    @JacksonXmlElementWrapper(localName = "livraison", useWrapping = false)
    private Livraison[] livraison;

    /**
     * Default constructor of {DemandeDeLivraisons}.
     */
    public DemandeDeLivraisons() {
    }

    /**
     * Creates an object {@code DemandeDeLivraisons} with the {@code entrepot}
     * and {@code livraison} attributes initialized.
     *
     * @param entrepot  The {@code entrepot} attribute
     * @param livraison The {@code livraison} attribute
     */
    public DemandeDeLivraisons(Entrepot entrepot, Livraison[] livraison) {
        this.entrepot = entrepot;
        this.livraison = livraison;
    }

    /**
     * Gets the value of the {@code entrepot} attribute      s
     *
     * @return the attribute entrepot that contains the data of the warehouse
     *     
     */
    public Entrepot getEntrepot() {
        return entrepot;
    }

    /**
     * Sets the value of the attribute {@code entrepot}     
     *
     * @param entrepot New value of {@code entrepot} attribute    
     */
    public void setEntrepot(Entrepot entrepot) {
        this.entrepot = entrepot;
    }

    /**
     * Gets the value of the {@code livraison} attribute
     *
     * @return a table that contains all deliveries of the object
     * {@code DemandeDeLivraisons}      
     */
    public Livraison[] getLivraison() {
        return livraison;
    }

    /**
     * Sets the value of the {@code livraison} attribute
     *
     * @param livraison New value of the {@code livraison} attribute
     */
    public void setLivraison(Livraison[] livraison) {
        this.livraison = livraison;
    }

    /**
     * Overrides of the {@code toString} method
     *
     * @return the different data of the object in a string format
     */
    @Override
    public String toString() {
        return "Reseau [entrepot=" + entrepot + ", livraison=" + Arrays.toString(livraison) + "]";
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
        final DemandeDeLivraisons other = (DemandeDeLivraisons) obj;
        if (!Objects.equals(this.entrepot, other.entrepot)) {
            return false;
        }
        return Arrays.deepEquals(this.livraison, other.livraison);
    }
}
