package entities;

import java.util.Arrays;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * The class {@Code Reseau} represents an object that has the same structure as
 * the analyzed XML City plan file. The parsed XML file has a root tag:
 * {@Code Reseau}
 *
 * @author PLD-HEXA-301
 */
@JacksonXmlRootElement(localName = "reseau")
public class Reseau {

    /**
     * Contains the elements represented by the tag {@code node}
     */
    @JacksonXmlElementWrapper(localName = "noeud", useWrapping = false)
    private Noeud[] noeud;

    /**
     * Contains the elements represented by the tag {@code troncon}
     */
    @JacksonXmlElementWrapper(localName = "troncon", useWrapping = false)
    private Troncon[] troncon;

    /**
     * TODO never used
     * Defaults constructor of {@Code Reseau}
     */
    public Reseau() {
    }

    /**
     * TODO never used
     * Constructor of {@Code Reseau} with the attributes {@code node} and
     * {@code troncon}initialized.
     *
     * @param noeud   The {@code node} attribute
     * @param troncon The {@code troncon} attribute      
     */
    public Reseau(Noeud[] noeud, Troncon[] troncon) {
        this.noeud = noeud;
        this.troncon = troncon;
    }

    /**
     * Gets the value of the {@code node} attribute
     *
     * @return an array that contains all the nodes of the {@Code Reseau} object
     */
    public Noeud[] getNoeud() {
        return noeud;
    }

    /**
     * Sets the value of the {@code noeud} attribute
     *
     * @param noeud New value of the attribute {@code noeud}
     */
    public void setNoeud(Noeud[] noeud) {
        this.noeud = noeud;
    }

    /**
     * Gets the value of the {@code node} attribute
     *
     * @return the value of {@code troncon} attribute
     */
    public Troncon[] getTroncon() {
        return troncon;
    }

    /**
     * Sets the value of the attribute {@code troncon}
     *
     * @param troncon New value of {@code troncon} attribute
     */
    public void setTroncon(Troncon[] troncon) {
        this.troncon = troncon;
    }

    /**
     * Overrides of the {@code toString} method
     *
     * @return the different data of the object in a string format
     */
    @Override
    public String toString() {
        return "Reseau [noeud=" + Arrays.toString(noeud) + ", troncon=" + Arrays.toString(troncon) + "]";
    }
}
