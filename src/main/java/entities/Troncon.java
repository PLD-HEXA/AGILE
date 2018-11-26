package entities;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

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
	
}
