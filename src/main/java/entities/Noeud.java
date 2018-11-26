package entities;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class Noeud {
	
	@JacksonXmlProperty(localName = "id", isAttribute = true)
	private String id;
	
	@JsonUnwrapped
    private Coordinate coordinate;
	
	
	public Noeud() {
	}


	public Noeud(String id, Coordinate coordinate) {
		this.id = id;
		this.coordinate = coordinate;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public Coordinate getCoordinate() {
		return coordinate;
	}


	public void setCoordinate(Coordinate coordinate) {
		this.coordinate = coordinate;
	}


	@Override
	public String toString() {
		return "Noeud [id=" + id + ", coordinate=" + coordinate + "]";
	}

}

