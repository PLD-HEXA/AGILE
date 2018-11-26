package entities;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

public class Map {
    private Coordinate coordinateMin;

    private Coordinate coordinateMax;


    private List<List<Segment>> graph;



    private HashMap<Long, Integer> mapId;

    private Coordinate[] coordinates;

    private int wareHouse;


    private int[] tabDeliveryPoints;



    public Map(Coordinate coordinateMin, Coordinate coordinateMax) {
        this.coordinateMin = coordinateMin;
        this.coordinateMax = coordinateMax;
    }

   

    @Override

    public String toString() {
        return "Map{" +
                "coordinateMin=" + coordinateMin +
                ", coordinateMax=" + coordinateMax +
                ", graph=" + graph +
                ", mapId=" + mapId +
                ", coordinates=" + Arrays.toString(coordinates) +
                ", wareHouse=" + wareHouse +
                ", taxDeliveryPoints=" + Arrays.toString(tabDeliveryPoints) +
                '}';
    }





	public Coordinate getCoordinateMin() {
        return coordinateMin;
    }

    public void setCoordinateMin(Coordinate coordinateMin) {
        this.coordinateMin = coordinateMin;
    }

    public Coordinate getCoordinateMax() {
        return coordinateMax;
    }

    public void setCoordinateMax(Coordinate coordinateMax) {
        this.coordinateMax = coordinateMax;
    }


    public List<List<Segment>> getGraph() {
        return graph;
    }

    public void setGraph(List<List<Segment>> graph) {
    }
   
    public void setGraph(Vector<List<Segment>> graph) {

        this.graph = graph;
    }

    public HashMap<Long, Integer> getMapId() {
        return mapId;
    }

    public void setMapId(HashMap<Long, Integer> mapId) {
        this.mapId = mapId;
    }

    public Coordinate[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinate[] coordinates) {
        this.coordinates = coordinates;
    }

    public int getWareHouse() {
        return wareHouse;
    }

    public void setWareHouse(int wareHouse) {
        this.wareHouse = wareHouse;
    }

    public int[] getTabDeliveryPoints() {

        return tabDeliveryPoints;
    }

    public void setTabDeliveryPoints(int[] tabDeliveryPoints) {
        this.tabDeliveryPoints = tabDeliveryPoints;

        
    }

    
}
