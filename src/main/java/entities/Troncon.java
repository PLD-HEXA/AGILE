package entities;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import java.util.Objects;

public class Troncon {

	@JacksonXmlProperty(localName = "destination", isAttribute = true)
	private String destination;
	
	@JacksonXmlProperty(localName = "longueur", isAttribute = true)
	private String longueur;
	
	@JacksonXmlProperty(localName = "nomRue", isAttribute = true)
	private String nomRue;
	
	@JacksonXmlProperty(localName = "origine", isAttribute = true)
	private String origine;
	
	public Troncon() {
	}

	public Troncon(String destination, String longueur, String nomRue, String origine) {
		this.destination = destination;
		this.longueur = longueur;
		this.nomRue = nomRue;
		this.origine = origine;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getLongueur() {
		return longueur;
	}

	public void setLongueur(String longueur) {
		this.longueur = longueur;
	}

	public String getNomRue() {
		return nomRue;
	}

	public void setNomRue(String nomRue) {
		this.nomRue = nomRue;
	}

	public String getOrigine() {
		return origine;
	}

	public void setOrigine(String origine) {
		this.origine = origine;
	}

	@Override
	public String toString() {
		return "Troncon [destination=" + destination + ", longueur=" + longueur + ", nomRue=" + nomRue + ", origine="
				+ origine + "]";
	}

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
