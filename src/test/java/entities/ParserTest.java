/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 *
 * @author Chris
 */
public class ParserTest {
    
    /**
     * 
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
        Reseau res = parser.parseCityPlan(pathnameXml);
        
        // Then
        assertNotNull(res);
        assertEquals(coordExpected, res.getNoeud()[0].getCoordinate());
        assertEquals(noeudExpected, res.getNoeud()[0]);
        assertEquals(tronconExpected, res.getTroncon()[0]);
    }
    
    /**
     * When attribute is missing, the value of the attribute is null
     */
    @Test
    public void testParseCityPlanMissingAttribute() {
        // Given
        
        Parser parser = new Parser();
        String pathnameXml = "./ressources/fichiersTestXml/petitPlanMissingAttribute.xml";
        
        // When
        Reseau res = parser.parseCityPlan(pathnameXml);
        
        // Then
        assertNotNull(res);
        assertEquals(null, res.getNoeud()[0].getCoordinate().getLatitude());
        
    }
    
    @Test
    public void testParseCityPlanWithWrongExtension() {
        // Given
        
        Parser parser = new Parser();
        String pathnameXml = "./ressources/fichiersTestXml/petitPlanWrongExtension.txt";
        
        // When
        Reseau res = parser.parseCityPlan(pathnameXml);
        
        // Then
        assertNull(res);
    }
    
    /**
     * When the name of a balise is not correct, return null
     */
    @Test
    public void testParseCityPlanWithWrongBalise() {
        // Given
        
        Parser parser = new Parser();
        String pathnameXml = "./ressources/fichiersTestXml/petitPlanWrongBalise.xml";
        
        // When
        Reseau res = parser.parseCityPlan(pathnameXml);
        
        // Then
        assertNull(res);
        
    }
    
    /**
     * When a balise is missing, nothing change for the parser, the 
     * value for the object that represents the balise is null
     */
    @Test
    public void testParseCityPlanWithBaliseMissing() {
        // Given
        
        Parser parser = new Parser();
        String pathnameXml = "./ressources/fichiersTestXml/petitPlanMissingBalise.xml";
        
        // When
        Reseau res = parser.parseCityPlan(pathnameXml);
        
        // Then
        assertNotNull(res);
        assertNull(res.getNoeud());
    }
    
    /**
     * No change when parsing the document, the error will be
     * detected when we fill the map
     */
    @Test
    public void testParseCityPlanWithValueIncorrect() {
        // Given
        
        Parser parser = new Parser();
        String pathnameXml = "./ressources/fichiersTestXml/petitPlanValueIncorrect.xml";
        
        // When
        Reseau res = parser.parseCityPlan(pathnameXml);
        
        // Then
        assertNotNull(res);
    }
    
    /**
     * Should return null when an attribute is not expected
     */
    @Test
    public void testParseCityPlanWithAttributeAdd() {
        // Given
        
        Parser parser = new Parser();
        String pathnameXml = "./ressources/fichiersTestXml/petitPlanAttributeAdd.xml";
        
        // When
        Reseau res = parser.parseCityPlan(pathnameXml);
        
        // Then
        assertNull(res);
    }
    
    @Test
    public void testParseDeliveryWithFileOK() {
        // Given
        
        Parser parser = new Parser();
        String pathnameXml = "./ressources/fichiersXML2018/dl-petit-3.xml";
        Livraison livraisonExpected = new Livraison("26317242", 60);
        Entrepot entrepotExpected = new Entrepot("48830472", "8:0:0");
        
        // When
        
        DemandeDeLivraisons ddl = parser.parseDelivery(pathnameXml);
        
        // Then
        
        assertNotNull(ddl);
        assertNotNull(ddl.getEntrepot());
        assertNotNull(ddl.getLivraison()[0]);
        assertEquals(entrepotExpected, ddl.getEntrepot());
        assertEquals(livraisonExpected, ddl.getLivraison()[0]);
    }
    
    @Test
    public void testParseDeliveryMissingAttribute() {
        // Given
        
        Parser parser = new Parser();
        String pathnameXml = "./ressources/fichiersTestXml/dl-MissingAttribute.xml";
        
        // When
        
        DemandeDeLivraisons ddl = parser.parseDelivery(pathnameXml);
         
        // Then
        
        assertNotNull(ddl);
        
    }
    
    @Test
    public void testParseDeliveryWithWrongExtension() {
        // Given
        
        Parser parser = new Parser();
        String pathnameXml = "./ressources/fichiersTestXml/dl-WrongExtension.txt";
        
        // When
        
        DemandeDeLivraisons ddl = parser.parseDelivery(pathnameXml);
         
        
        // Then
        
        assertNull(ddl);
    }
    
    /**
     * When the name of a balise is not correct, return null
     */
    @Test
    public void testParseDeliveryWithWrongBalise() {
        // Given
        
        Parser parser = new Parser();
        String pathnameXml = "./ressources/fichiersTestXml/dl-WrongBalise.xml";
        
        // When
        
        DemandeDeLivraisons ddl = parser.parseDelivery(pathnameXml);
        
        
        // Then
        
        assertNull(ddl);
        
    }
    
    /**
     * When a balise is missing, return null
     */
    @Test
    public void testParseDeliveryWithBaliseMissing() {
        // Given
        
        Parser parser = new Parser();
        String pathnameXml = "./ressources/fichiersTestXml/dl-MissingBalise.xml";
        
        // When
        
        DemandeDeLivraisons ddl = parser.parseDelivery(pathnameXml);
        
        // Then
        
        assertNotNull(ddl);
        assertNull(ddl.getEntrepot());
    }
    
    /**
     * No change when parsing the document, the error will be
     * detect when we fill the map
     */
    @Test
    public void testParseDeliveryWithValueIncorrect() {
        // Given
        
        Parser parser = new Parser();
        String pathnameXml = "./ressources/fichiersTestXml/dl-ValueIncorrect.xml";
        
        // When
        
        DemandeDeLivraisons ddl = parser.parseDelivery(pathnameXml);
        
        // Then
        
        assertNotNull(ddl);
    }
    
    /**
     * Should return null when an attribute is not expected
     */
    @Test
    public void testParseDeliveryWithAttributeAdd() {
        // Given
        
        Parser parser = new Parser();
        String pathnameXml = "./ressources/fichiersTestXml/dl-AttributeAdd.xml";
        
        // When
        
        DemandeDeLivraisons ddl = parser.parseDelivery(pathnameXml);
            
        // Then
        
        assertNull(ddl);
    }
}
