package entities;

import java.util.Date;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import java.util.Objects;



public class Entrepot    {
	
	@JacksonXmlProperty(localName = "adresse", isAttribute = true)
	private String adresse;
	
	@JacksonXmlProperty(localName = "heureDepart", isAttribute = true)
	private String heureDepart;

        public Entrepot() {
        }
	
	public Entrepot(String adresse, String heureDepart) {
		this.adresse = adresse;
		this.heureDepart = heureDepart;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getHeureDepart() {
		return heureDepart;
	}
	
	public void setHeureDepart(String heureDepart) {
		this.heureDepart= heureDepart;
	}

    @Override
    public String toString() {
        return "Entrepot{" + "adresse=" + adresse + ", heureDepart=" + heureDepart + '}';
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
        final Entrepot other = (Entrepot) obj;
        if (!Objects.equals(this.adresse, other.adresse)) {
            return false;
        }
        if (!Objects.equals(this.heureDepart, other.heureDepart)) {
            return false;
        }
        return true;
    }

      

}
