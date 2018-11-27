package entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Map {
    private Coordinate coordinateMin;

    private Coordinate coordinateMax;

    private List<List<Segment>> graph;

    private HashMap<Long, Integer> mapId;

    private Coordinate[] coordinates;

    // Pareil que pour tabDeliveryPoints : pair<index,depart>
    private int wareHouse;
  
    private int[] tabDeliveryPoints;
    

    public Map() {
        coordinateMax = new Coordinate(-90.0,-180.0);
        coordinateMin = new Coordinate(90.0,180.0);
        mapId = new HashMap<Long,Integer>();
        graph = new ArrayList<>();
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
        this.graph = graph;
    }

    public HashMap<Long, Integer> getMapId() {
        return mapId;
    }

    public void setMapId(HashMap<Long, Integer> mapId) {
        this.mapId = mapId;
    }

    public Coordinate getCoordinate(int index) {
        return this.coordinates[index];
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
    
    public void fillMapIdAndCoordinate(Reseau res) {
        int coordinatesLength = res.getNoeud().length;
        coordinates = new Coordinate[coordinatesLength];
        for(int i = 0; i < coordinatesLength; i++) {
            // Ici, qu'est-ce qu'on en fait de la coordinate
            Coordinate coord = res.getNoeud()[i].getCoordinate();
            if(validCoordinate(coord)) {
                checkMinMaxCoord(coord);
                Long id = Long.valueOf(res.getNoeud()[i].getId());
                mapId.put(id, i);
                coordinates[i] = res.getNoeud()[i].getCoordinate();
                List<Segment> listSegment = new ArrayList<>();
                graph.add(i,listSegment);
            }
        }
    }

    public void fillGraph(Reseau res) {
        Troncon[] troncon = res.getTroncon();
        for (int i=0; i<troncon.length; i++) {
            int indexOrigine = mapId.get(Long.valueOf(troncon[i].getOrigine()));
            // faille : est-ce que l'Id de la destination est forcément une origine de base? --> Vérification
            int indexDestination = mapId.get(Long.valueOf(troncon[i].getDestination()));
            double length = Double.valueOf(troncon[i].getLongueur());
            Segment segment = new Segment (indexDestination, troncon[i].getNomRue(), length);
            
            graph.get(indexOrigine).add(segment);
        }
    }
    
    public boolean validCoordinate(Coordinate coord) {
        // Faire les verifs liées à une latitude et une longitude
        if (coord.getLatitude() > 90.0 || coord.getLatitude() < -90.0 || coord.getLongitude() > 180.0
                || coord.getLongitude() < -180.0) {
            return false;
        }
        return true;
    }

    private void checkMinMaxCoord(Coordinate coord) {
        if (coord.getLatitude() > coordinateMax.getLatitude()) {
            coordinateMax.setLatitude(coord.getLatitude());
        } else if (coord.getLatitude() < coordinateMin.getLatitude()){
            coordinateMin.setLatitude(coord.getLatitude());
        }
        if (coord.getLongitude() > coordinateMax.getLongitude()) {
            coordinateMax.setLongitude(coord.getLongitude());
        } else if (coord.getLongitude() < coordinateMin.getLongitude()) {
            coordinateMin.setLongitude(coord.getLongitude());
        }
    }
}
