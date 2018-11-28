package entities;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import java.util.Objects;

public class Livraison {
	
    @JacksonXmlProperty(localName = "adresse", isAttribute = true)
    private String adresse;

    @JacksonXmlProperty(localName = "duree", isAttribute = true)
    private Integer duree;

    public Livraison() {
    }

    public Livraison(String adresse, Integer duree) {
            this.adresse = adresse;
            this.duree = duree;
    }


    public String getId() {
            return adresse;
    }

    public void setAdresse(String adresse) {
            this.adresse = adresse;
    }

    public Integer getDuree() {
            return duree;
    }

    public void setDuree(Integer duree) {
            this.duree = duree;
    }

    @Override
    public String toString() {
            return "Noeud [adresse=" + adresse + ", duree=" + duree + "]";
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
        final Livraison other = (Livraison) obj;
        if (!Objects.equals(this.adresse, other.adresse)) {
            return false;
        }
        if (!Objects.equals(this.duree, other.duree)) {
            return false;
        }
        return true;
    }

    
}
