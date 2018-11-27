package entities;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;



public class Entrepot    {
	
	@JacksonXmlProperty(localName = "id", isAttribute = true)
	private String id;
	
	@JsonUnwrapped
	private Date heureDepart;
	
	



	public Entrepot(String id, Date heureDepart) {
		this.id = id;
		this.heureDepart = heureDepart;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public Date getHeureDepart() {
		return heureDepart;
	}
	
	public void setHeureDepart(Date heureDepart) {
		this.heureDepart= heureDepart;
	}


	@Override
	public String toString() {
		return "Noeud [id=" + id + ", heureDepart=" + heureDepart + "]";
	}



}
