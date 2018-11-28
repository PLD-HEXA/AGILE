/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.IOException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 *
 * @author Chris
 */
public class ParserTest {
    
    /**
     * Test of toString method, of class Coordinate.
     */
    @Test
    public void testParseCityPlanWithFileOK() {
        // Given
        
        Parser parser = new Parser();
        String pathnameXml = "./ressources/fichiersXML2018/petitPlan.xml";
        Coordinate coordExpected = new Coordinate(4.857418,45.75406);
        Noeud noeudExpected = new Noeud("25175791", coordExpected);
        Troncon tronconExpected = new Troncon("25175778", "69.979805", "Rue Danton", "25175791");
        
        // When
        Reseau res = null;
        try {
            res = parser.parseCityPlan(pathnameXml);
        } catch (IOException e) {
            System.out.println("Erreur lors de l'ouverture du fichier : " + e);
        }
        
        // Then
        assertNotNull(res);
        assertEquals(coordExpected, res.getNoeud()[0].getCoordinate());
        assertEquals(noeudExpected, res.getNoeud()[0]);
        assertEquals(tronconExpected, res.getTroncon()[0]);
    }
    
    @Test
    public void testParseDeliveryWithFileOK() {
        // Given
        
        Parser parser = new Parser();
        String pathnameXml = "./ressources/fichiersXML2018/dl-petit-3.xml";
        Livraison livraisonExpected = new Livraison("26317242", 60);
        Entrepot entrepotExpected = new Entrepot("48830472", "8:0:0");
        DemandeDeLivraisons ddl = new DemandeDeLivraisons();
        
        // When
        
        try {
            ddl = parser.parseDelivery(pathnameXml);
        } catch (IOException e) {
            System.out.println("Erreur lors de l'ouverture du fichier : " + e);
        }
        
        // Then
        
        assertNotNull(ddl);
        assertNotNull(ddl.getEntrepot());
        assertNotNull(ddl.getLivraison()[0]);
        assertEquals(entrepotExpected, ddl.getEntrepot());
        assertEquals(livraisonExpected, ddl.getLivraison()[0]);
    }
}
