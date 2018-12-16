package entities;

import java.util.Arrays;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * The class {@Code Network} represents an object that has the same structure as
 * the analyzed XML City plan file. The parsed XML file has a root tag:
 * {@Code Network}
 *
 * @author PLD-HEXA-301
 */
@JacksonXmlRootElement(localName = "reseau")
public class Network {

    /**
     * Contains the elements represented by the tag {@code node}
     */
    @JacksonXmlElementWrapper(localName = "noeud", useWrapping = false)
    private Node[] node;

    /**
     * Contains the elements represented by the tag {@code troncon}
     */
    @JacksonXmlElementWrapper(localName = "troncon", useWrapping = false)
    private Troncon[] troncon;

    /**
     * Defaults constructor of {@Code Network}
     */
    public Network() {
    }

    /**
     * Constructor of {@Code Network} with the attributes {@code node} and
     * {@code troncon}initialized.
     *
     * @param node    The {@code node} attribute
     * @param troncon The {@code troncon} attribute      
     */
    public Network(Node[] node, Troncon[] troncon) {
        this.node = node;
        this.troncon = troncon;
    }

    /**
     * Gets the value of the {@code node} attribute
     *
     * @return an array that contains all the nodes of the {@Code Network} object
     */
    public Node[] getNode() {
        return node;
    }

    /**
     * Sets the value of the {@code node} attribute
     *
     * @param node New value of the attribute {@code node}
     */
    public void setNode(Node[] node) {
        this.node = node;
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
        return "Network [node=" + Arrays.toString(node) + ", troncon=" + Arrays.toString(troncon) + "]";
    }
}
