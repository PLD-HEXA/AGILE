/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import com.fasterxml.jackson.databind.JsonMappingException;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Chris
 */
public class MapTest {
    
    Reseau res;
    Parser parser;
    String pathnameXml = "./ressources/fichiersXML2018/petitPlan.xml";
    
    @Before 
    public void initialize() {
        parser = new Parser();
        try {
            res = parser.parseCityPlan(pathnameXml);
        } catch (JsonMappingException ex) {
            System.out.println("Erreur lors du mappaing du fichier : ");
            Logger.getLogger(MapTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            System.out.println("Erreur lors de l'ouverture du fichier : ");
            Logger.getLogger(MapTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        assertNotNull(res);
    }
    
    @Test
    public void validateCoordinateTestWithCoordOKAndNotOK() {
        // Given
        
        Map map = new Map();
        Coordinate coordinateValid = new Coordinate(45.75406,4.857418);
        Coordinate coordinateNotValid = new Coordinate(-845.75406,4.857418);
        
        // When
        
        boolean valid = map.validCoordinate(coordinateValid);
        boolean valid2 = map.validCoordinate(coordinateNotValid);
        
        // Then
        
        assertEquals(true, valid);
        assertEquals(false, valid2);
    }
    
    @Test
    public void fillMapIdAndCoordinateTestWithResOK() {
        
        //Given
        
        Map map = new Map();
        Coordinate coordExpected = new Coordinate(4.857418,45.75406);
        Noeud noeudExpected = new Noeud("25175791", coordExpected);
        Troncon tronconExpected = new Troncon("25175791", "69.979805", "Rue Danton", "25175778");
        
        
        // When
        
        map.fillMapIdAndCoordinate(res);
        
        // Then
        
        assertNotNull(map);
        assertNotNull(map.getCoordinateMax());
        assertNotNull(map.getCoordinateMin());
        assertNotNull(map.getMapId());
        
        Long idOrigine = Long.valueOf(tronconExpected.getOrigine());
        Long idNodeExpected = Long.valueOf(noeudExpected.getId());
        int indexFirstTronconCalculate = map.getMapId().get(idOrigine);
        int indexFirstNodeCalculate = map.getMapId().get(idNodeExpected);
        
        assertEquals(0, indexFirstNodeCalculate);
        assertNotEquals(0, indexFirstTronconCalculate);
        assertNotEquals(25175778, indexFirstTronconCalculate);
        
    }
    
    @Test
    public void fillGraphTestWithCityPlanValid() {
        
        
        // Given 
        
        Map map = new Map();
        Long idOrigine = 25175791L;
        int indexOrigine = 0;
        Long idDestinationSeg1 = 25175778L;
        int indexDestinationSeg1 = 28;
        double lengthSeg1 = 69.979805;
        Long idDestinationSeg2 = 2117622723L;
        int indexDestinationSeg2 = 81;
        double lengthSeg2= 136.00636;
        Segment segExpected = new Segment(indexDestinationSeg1,"Rue Danton",lengthSeg1);
        Segment segExpected2 = new Segment(indexDestinationSeg2,"Rue de l'Abondance",lengthSeg2);
        
        // When
        
        map.fillMapIdAndCoordinate(res);
        map.fillGraph(res);
        
        //Then
        
        int indexDestSeg1 = map.getMapId().get(idDestinationSeg1);
        int indexDestSeg2 = map.getMapId().get(idDestinationSeg2);
        
        
        List<List<Segment>> graphResult = map.getGraph();
        List<Segment> listResultFirst = graphResult.get(0);
        
        assertNotNull(graphResult);
        assertNotNull(listResultFirst);
        
        int indexOrigineCalculate = map.getMapId().get(idOrigine);
        assertEquals(indexOrigine, indexOrigineCalculate);
        
        assertEquals(segExpected, listResultFirst.get(0));
        assertEquals(segExpected2, listResultFirst.get(1));
    }
}