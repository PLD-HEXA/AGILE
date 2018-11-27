package entities;

import java.util.Arrays;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

public class DemandeDelivraisons {
	
    @JacksonXmlElementWrapper(localName = "entrepot", useWrapping = false)
    private Entrepot[] entrepot;

   @JacksonXmlElementWrapper(localName = "livraison", useWrapping = false)
   private Livraison[] livraison;

   public DemandeDelivraisons() {
   }

   public DemandeDelivraisons(Entrepot[] entrepot, Livraison[] livraison) {
           this.entrepot = entrepot;
           this.livraison =  livraison;
   }

   public Entrepot[] getentrepot() {
           return entrepot;
   }

   public void setentrepot(Entrepot[] entrepot) {
           this.entrepot = entrepot;
   }

   public Livraison[] getlivraison() {
           return livraison;
   }

   public void setlivraison(Livraison[] livraison) {
           this.livraison = livraison;
   }

   @Override
   public String toString() {
           return "Reseau [entrepot=" + Arrays.toString(entrepot) + ", livraison=" + Arrays.toString(livraison) + "]";
   }

}

	

