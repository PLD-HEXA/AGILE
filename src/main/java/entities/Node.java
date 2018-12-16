package entities;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.Objects;

/**
 * The {@code Node} class represents an object that has the same structure as
 * the element represented by the tag {@code Node} in the parsed City plan XML
 * file.
 *
 * @author PLD-HEXA-301
 */
public class Node {

    /**
     * Contains the id of the node represented by the attribute {@code id} of
     * the tag {@code Node}
     */
    @JacksonXmlProperty(localName = "id", isAttribute = true)
    private String id;

    /**
     * Contains the coordinate of the node. The {@code latitude} and
     * {@code longitude} attributes of the tag{@code Node} are encapsulated in
     * a {@code Coordinate} object
     */
    @JsonUnwrapped
    private Coordinate coordinate;

    /**
     * Default constructor of {@code Node}
     */
    public Node() {
    }

    /**
     * Constructor of {@Code Node} that represents an object with the
     * attributes {@code id} and {@code coordinate}
     *
     * @param id         The {@code id} attribute
     * @param coordinate The {@code coordinate} attribute
     */
    public Node(String id, Coordinate coordinate) {
        this.id = id;
        this.coordinate = coordinate;
    }

    /**
     * Gets the value of the {@code id} attribute
     *
     * @return a string that represents the id of the node.
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the {@code id} attribute
     *
     * @param id New value of the {@code id} attribute      
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the value of the {@code coordinate} attribute
     *
     * @return a {@code Coordinate} object that represents the coordinates of
     * the node.
     */
    public Coordinate getCoordinate() {
        return coordinate;
    }

    /**
     * Sets the value of the {@code coordinate} attribute    
     *
     * @param coordinate New value of the {@code coordinate} attribute      
     */
    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    /**
     * Overrides of the {@code toString} method
     *
     * @return the different data of the object in a String format
     */
    @Override
    public String toString() {
        return "Node [id=" + id + ", coordinate=" + coordinate + "]";
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
        final Node other = (Node) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return Objects.equals(this.coordinate, other.coordinate);
    }
}
