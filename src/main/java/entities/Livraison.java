package entities;



import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class Livraison {
	
	@JacksonXmlProperty(localName = "id", isAttribute = true)
	private String id;
	
	@JsonUnwrapped
	private Integer duree;
	
	



	public Livraison(String id, Integer duree) {
		this.id = id;
		this.duree = duree;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}
	
	public Integer getdDuree() {
		return duree;
	}


	public void setDuree(Integer duree) {
		this.duree = duree;
	}


	


	@Override
	public String toString() {
		return "Noeud [id=" + id + ", duree=" + duree + "]";
	}


	
	
	

}
