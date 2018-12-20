package entities;

import java.util.Arrays;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * The class represents an object that has the same structure as
 * the analyzed XML City plan file. The parsed XML file has a root tag:
 * Reseau
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
     * Defaults constructor
     */
    public Reseau() {
    }

    /**
     * Constructor
     * @param noeud   The nodes
     * @param troncon The segments     
     */
    public Reseau(Noeud[] noeud, Troncon[] troncon) {
        this.noeud = noeud;
        this.troncon = troncon;
    }

    /**
     * Gets the value of the nodes
     *
     * @return an array that contains all the nodes of the Reseau object
     */
    public Noeud[] getNoeud() {
        return noeud;
    }

    /**
     * Sets the value of the nodes attribute
     *
     * @param noeud New value of the attribute nodes
     */
    public void setNoeud(Noeud[] noeud) {
        this.noeud = noeud;
    }

    /**
     * Gets the value of the segments attribute
     *
     * @return the value of the segments attribute
     */
    public Troncon[] getTroncon() {
        return troncon;
    }

    /**
     * Sets the value of the attribute segments
     *
     * @param troncon New value of segments attribute
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
