package entities;

import java.util.Arrays;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import java.util.Objects;

public class DemandeDeLivraisons {
	
    @JacksonXmlElementWrapper(localName = "entrepot", useWrapping = false)
    private Entrepot entrepot;

    @JacksonXmlElementWrapper(localName = "livraison", useWrapping = false)
    private Livraison[] livraison;

    public DemandeDeLivraisons() {
    }

    public DemandeDeLivraisons(Entrepot entrepot, Livraison[] livraison) {
           this.entrepot = entrepot;
           this.livraison =  livraison;
    }

    public Entrepot getEntrepot() {
        return entrepot;
    }

    public void setEntrepot(Entrepot entrepot) {
        this.entrepot = entrepot;
    }

    public Livraison[] getLivraison() {
        return livraison;
    }

    public void setLivraison(Livraison[] livraison) {
        this.livraison = livraison;
    }
    @Override
    public String toString() {
        return "Reseau [entrepot=" + entrepot + ", livraison=" + Arrays.toString(livraison) + "]";
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
        final DemandeDeLivraisons other = (DemandeDeLivraisons) obj;
        if (!Objects.equals(this.entrepot, other.entrepot)) {
            return false;
        }
        if (!Arrays.deepEquals(this.livraison, other.livraison)) {
            return false;
        }
        return true;
    }
    
    

}

	

