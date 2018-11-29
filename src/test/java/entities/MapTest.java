/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.util.List;
import javafx.util.Pair;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Chris
 */
public class MapTest {
    
    Reseau res;
    Parser parser;
    DemandeDeLivraisons ddl;
    String pathnameCityPlanXml = "./ressources/fichiersXML2018/petitPlan.xml";
    String pathnameDeliveryXml = "./ressources/fichiersXML2018/dl-petit-3.xml";
    
    @Before 
    public void initialize() {
        parser = new Parser();
        res = parser.parseCityPlan(pathnameCityPlanXml);
        
        ddl = parser.parseDelivery(pathnameDeliveryXml);
        assertNotNull(res);
        assertNotNull(ddl);
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
    
    @Test
    public void fillGraphTestWithNoTroncon() {
        
        // Given 
        
        Map map = new Map();
        String pathnameCityPlanXml = "./ressources/fichiersTestXml/petitPlanNoTroncon.xml";
        Reseau resNoTroncon = parser.parseCityPlan(pathnameCityPlanXml);
        
        // When
        map.fillMapIdAndCoordinate(resNoTroncon);
        map.fillGraph(resNoTroncon);
        
        // Then
        
        assertNull(map.getGraph());
        
    }
    
    @Test
    public void fillGraphTestWithIdOrigineDoesntExist() {
        
        // Given 
        
        Map map = new Map();
        String pathnameCityPlanXml = "./ressources/fichiersTestXml/petitPlanIdOrigineNotExists.xml";
        Reseau resIdONotExist = parser.parseCityPlan(pathnameCityPlanXml);
        Troncon tronconExpected = new Troncon("25175791", "69.979805", "Rue Danton", "1");
        
        // When
        map.fillMapIdAndCoordinate(resIdONotExist);
        map.fillGraph(resIdONotExist);
        
        // Then
        
        assertNotNull(map.getGraph());
        assertEquals(-1,map.getGraph().indexOf(0));
        assertNotEquals(tronconExpected.getDestination(), map.getGraph().get(174).get(0).getDestIndex());
        assertEquals(67.72544, map.getGraph().get(174).get(0).getLength(), 0.1);
    }
    
    @Test
    public void fillGraphTestWithZeroTronconValid() {
        // Given 
        
        Map map = new Map();
        String pathnameCityPlanXml = "./ressources/fichiersTestXml/petitPlanZeroTronconValid.xml";
        Reseau resNoTronconValid = parser.parseCityPlan(pathnameCityPlanXml);
        
        // When
        map.fillMapIdAndCoordinate(resNoTronconValid);
        map.fillGraph(resNoTronconValid);
        
        // Then
        
        assertNull(map.getGraph());
        
    }
    
    @Test
    public void fillTabDeliveryPointTestOK() {
        // Given
        
        Map map = new Map();
        map.fillMapIdAndCoordinate(res);
        
        Long idWareHouseExpected = 48830472L;
        int indexWareHouseExpected = map.getMapId().get(idWareHouseExpected);
        Pair<Integer,String> wareHouseExpected = new Pair<>(indexWareHouseExpected,"8:0:0");
        
        Long idDelPointExpected = 26317242L;
        int indexDelPointExpected = map.getMapId().get(idDelPointExpected);
        Pair<Integer,Integer> delPointExpected1 = new Pair<>(indexDelPointExpected,60);
        Long idDelPointExpected2 = 2650077854L;
        int indexDelPointExpected2 = map.getMapId().get(idDelPointExpected2);
        Pair<Integer,Integer> delPointExpected2 = new Pair<>(indexDelPointExpected2,180);
        Long idDelPointExpected3 = 55475025L;
        int indexDelPointExpected3 = map.getMapId().get(idDelPointExpected3);
        Pair<Integer,Integer> delPointExpected3 = new Pair<>(indexDelPointExpected3,60);
        
        // When
        
        map.fillTabDeliveryPoint(ddl);
        
        // Then
        
        Pair<Integer,String> wareHouse = map.getWareHouse();
        List<Pair<Integer,Integer>> tabDeliveryPoints = map.getTabDeliveryPoints();
        assertNotNull(wareHouse);
        assertNotNull(tabDeliveryPoints);
        
        assertEquals(wareHouse, wareHouseExpected);
        
        assertEquals(delPointExpected1, tabDeliveryPoints.get(0));
        assertEquals(delPointExpected2, tabDeliveryPoints.get(1));
        assertEquals(delPointExpected3, tabDeliveryPoints.get(2));
    }
    
    /**
     * Should not take into account the node, therefore
     * when we search the index in the mapId, it should return null
     */
    @Test
    public void fillMapIdAndCoordinateWithAttributeNull() {
        //Given
        
        Map map = new Map();
        Coordinate coordExpected = new Coordinate(4.857418,45.75406);
        Noeud noeudExpected = new Noeud("25175791", coordExpected);
        // On change à null la valeur de la latitude
        res.getNoeud()[0].getCoordinate().setLatitude(null);
        assertEquals("25175791", res.getNoeud()[0].getId());
        assertNull(res.getNoeud()[0].getCoordinate().getLatitude());
        
        // When
        
        map.fillMapIdAndCoordinate(res);
        
        // Then
        
        assertNotNull(map);
        assertNotNull(map.getCoordinateMax());
        assertNotNull(map.getCoordinateMin());
        assertNotNull(map.getMapId());
        
        Long idNodeExpected = Long.valueOf(noeudExpected.getId());
        
        assertNull(map.getMapId().get(idNodeExpected));
        assertNotEquals(noeudExpected, map.getCoordinate(0));
    }
    
    /**
     * The objects mapId and Coordinates should not be fill, their
     * value will be null
     */
    @Test
    public void fillMapIdAndCoordinateWithBaliseMissing() {
        //Given
        String pathnameXml = "./ressources/fichiersTestXml/petitPlanMissingBalise.xml";
        Reseau resAttributeNull = parser.parseCityPlan(pathnameXml);
        assertNotNull(resAttributeNull);
        assertNull(resAttributeNull.getNoeud());
        Map map = new Map();
        
        // When
        
        map.fillMapIdAndCoordinate(resAttributeNull);
        
        // Then
        
        assertNotNull(map);
        assertNull(map.getMapId());
        assertNull(map.getCoordinates());
    }
    
    @Test public void fillMapIdAndCoordinateWithIdIncorrect() {
        // Given
        
        String pathnameXml = "./ressources/fichiersTestXml/petitPlanValueIncorrect.xml";
        Reseau resIdIncorrect = parser.parseCityPlan(pathnameXml);
        assertNotNull(resIdIncorrect);
        assertEquals("-25175791", resIdIncorrect.getNoeud()[0].getId());
        
        Map map = new Map();
        
        // When
        
        map.fillMapIdAndCoordinate(resIdIncorrect);
        
        // Then
        
        Long idIncorrect = Long.valueOf("-25175791");
        assertNull(map.getMapId().get(idIncorrect));
    }
           
    /**
     * When two nodes have the same id, it is the last one which is registered
     * for the same key in the mapId and therefore in coordinates, it is also 
     * the last one
     */
    @Test public void fillMapIdAndCoordinateWithSameId() {
        // Given
        
        String pathnameXml = "./ressources/fichiersTestXml/fillMapIdSameId.xml";
        Reseau resIdIncorrect = parser.parseCityPlan(pathnameXml);
        assertNotNull(resIdIncorrect);
        assertEquals("2129259178", resIdIncorrect.getNoeud()[0].getId());
        assertEquals("2129259178", resIdIncorrect.getNoeud()[1].getId());
        
        Map map = new Map();
        
        // When
        
        map.fillMapIdAndCoordinate(resIdIncorrect);
        
        // Then
        
        Long lastId = Long.valueOf("2129259178");
        int lastIndex = map.getMapId().get(lastId);
        // We compare the longitude to be sure
        // that it is the last node which is 
        // registered in the mapId for the same key
        double longitude = map.getCoordinate(lastIndex).getLongitude();
        assertEquals(4.8, longitude, 0.1);
    }
    
    @Test
    public void fillMapIdAndCoordinateTestCoordMax() {
        // Given
        
        String pathnameXml = "./ressources/fichiersTestXml/fillMapIdCoordMax.xml";
        Reseau resTest = parser.parseCityPlan(pathnameXml);
        assertNotNull(resTest);
        
        Map map = new Map();
        
        // When
        
        map.fillMapIdAndCoordinate(resTest);
        
        // Then
        
        Long lastId = Long.valueOf("479185303");
        int index = map.getMapId().get(lastId);
        
        double latitudeCalculated = map.getCoordinate(index).getLatitude();
        assertEquals(89, latitudeCalculated, 0.1);
    }
    
    /**
     * When the id of the warehouse is not registered in the mapId, the graph
     * should be null
     */
    @Test 
    public void fillTabDeliveryPointsWithIdWarehouseInvalid() {
        // Given
        
        String pathnameXml = "./ressources/fichiersTestXml/dl-entrepotIdInvalid.xml";
        DemandeDeLivraisons ddlIdWareHouseInvalid = parser.parseDelivery(pathnameXml);
        assertNotNull(ddlIdWareHouseInvalid);
        
        Map map = new Map();
        
        // When
        map.fillMapIdAndCoordinate(res);
        map.fillTabDeliveryPoint(ddlIdWareHouseInvalid);
        
        // Then
        
        assertNull(map.getTabDeliveryPoints());
    }
            
    @Test 
    public void fillTabDeliveryPointsZeroDeliveryPointsValid() {
        // Given
        
        String pathnameXml = "./ressources/fichiersTestXml/dl-zeroDLValid.xml";
        DemandeDeLivraisons ddlZeroDL = parser.parseDelivery(pathnameXml);
        assertNotNull(ddlZeroDL);
        
        Map map = new Map();
        
        // When
        
        map.fillMapIdAndCoordinate(res);
        map.fillTabDeliveryPoint(ddlZeroDL);
        
        // Then
        
        assertNull(map.getTabDeliveryPoints());
    }
    
    @Test 
    public void fillTabDeliveryPointsWareHouse() {
        // Given
        
        String pathnameXml = "./ressources/fichiersTestXml/dl-missingBalise.xml";
        DemandeDeLivraisons ddlNoWH = parser.parseDelivery(pathnameXml);
        assertNotNull(ddlNoWH);
        
        Map map = new Map();
        
        // When
        
        map.fillMapIdAndCoordinate(res);
        map.fillTabDeliveryPoint(ddlNoWH);
        
        // Then
        
        assertNull(map.getTabDeliveryPoints());
    }
}
