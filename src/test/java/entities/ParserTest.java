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
}
