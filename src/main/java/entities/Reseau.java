package entities;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "reseau")
//@JsonIgnoreProperties(ignoreUnknown = true)
public class Reseau {
    
    @JacksonXmlElementWrapper(localName = "noeud", useWrapping = false)
    private Noeud[] noeud;
    
    @JacksonXmlElementWrapper(localName = "troncon", useWrapping = false)
    private Troncon[] troncon;

    public Reseau() {
    }

    public Reseau(Noeud[] noeud, Troncon[] troncon) {
            this.noeud = noeud;
            this.troncon = troncon;
    }

    public Noeud[] getNoeud() {
            return noeud;
    }

    public void setNoeud(Noeud[] noeud) {
            this.noeud = noeud;
    }

    public Troncon[] getTroncon() {
            return troncon;
    }

    public void setTroncon(Troncon[] troncon) {
            this.troncon = troncon;
    }

    @Override
    public String toString() {
            return "Reseau [noeud=" + Arrays.toString(noeud) + ", troncon=" + Arrays.toString(troncon) + "]";
    }

}
