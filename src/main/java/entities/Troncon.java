package entities;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import java.util.Objects;

/**
 * @author PLD-HEXA-301
 *
 * The class {@Code Troncon} represents an object that has the same structure as
 * the element represented by the {@code troncon} tag in the parsed City plan
 * XML file.
 *
 */
public class Troncon {

    /**
     * Contains the destination represented by the {@code destination} attribute
     * of the tag {@code troncon}
     */
    @JacksonXmlProperty(localName = "destination", isAttribute = true)
    private String destination;

    /**
     * Contains the length represented by the {@code longueur} attribute of the
     * tag {@code troncon}
     */
    @JacksonXmlProperty(localName = "longueur", isAttribute = true)
    private String longueur;

    /**
     * Contains the name of the street represented by the {@code nomRue}
     * attribute of the tag {@code troncon}
     */
    @JacksonXmlProperty(localName = "nomRue", isAttribute = true)
    private String nomRue;

    /**
     * Contains the origin represented by the attribute {@code origine} of the
     * tag {@code troncon}.
     */
    @JacksonXmlProperty(localName = "origine", isAttribute = true)
    private String origine;

    /**
     * Default constructor of {@code Troncon}
     */
    public Troncon() {
    }

    /**
     * Constructor of {@code Troncon} with the attributes {@code destination},
     * {@code longueur}, {@code nomRue} and {@code origine} initialized.
     *
     * @param destination The {@code destination} attribute
     * @param longueur The {@code longueur} attribute
     * @param nomRue The {@code nomRue} attribute
     * @param origine The {@code origine} attribute
     */
    public Troncon(String destination, String longueur, String nomRue, String origine) {
        this.destination = destination;
        this.longueur = longueur;
        this.nomRue = nomRue;
        this.origine = origine;
    }

    /**
     * Gets the value of the {@code destination} attribute
     *
     * @return a String that represents the id of the destination of the
     * segment.
     */
    public String getDestination() {
        return destination;
    }

    /**
     * Change the value of the {@code destination} attribute
     *
     * @param destination New value of attribute {@code destination}
     */
    public void setDestination(String destination) {
        this.destination = destination;
    }

    /**
     * Gets the value of the {@code longueur} attribute
     *
     * @return a String that represents the length of the segment.
     */
    public String getLongueur() {
        return longueur;
    }

    /**
     * Sets the value of the {@code longueur} attribute
     *
     * @param longueur New value of attribute {@code longueur}
     */
    public void setLongueur(String longueur) {
        this.longueur = longueur;
    }

    /**
     * Gets the value of the {@code nomRue} attribute
     *
     * @return a string that represents the name of the street.
     */
    public String getNomRue() {
        return nomRue;
    }

    /**
     * Sets the value of the attribute {@code nomRue}
     *
     * @param nomRue New value of attribute {@code nomRue}
     */
    public void setNomRue(String nomRue) {
        this.nomRue = nomRue;
    }

    /**
     * Gets the value of the attribute {@code origine}
     *
     * @return a string that represents the id of the segment.
     */
    public String getOrigine() {
        return origine;
    }

    /**
     * Sets the value of the attribute {@code origine}
     *
     * @param origine New value of attribute {@code origine}
     */
    public void setOrigine(String origine) {
        this.origine = origine;
    }

    /**
     * Overrides of the {@code toString} method
     *
     * @return the different data of the object in a String format
     */
    @Override
    public String toString() {
        return "Troncon [destination=" + destination + ", longueur=" + longueur + ", nomRue=" + nomRue + ", origine="
                + origine + "]";
    }

    /**
     * Overrides of the {@code equals} method
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
        final Troncon other = (Troncon) obj;
        if (!Objects.equals(this.destination, other.destination)) {
            return false;
        }
        if (!Objects.equals(this.longueur, other.longueur)) {
            return false;
        }
        if (!Objects.equals(this.nomRue, other.nomRue)) {
            return false;
        }
        if (!Objects.equals(this.origine, other.origine)) {
            return false;
        }
        return true;
    }
}
