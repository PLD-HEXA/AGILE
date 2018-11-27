package entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import javafx.util.Pair;

public class Map {
    private Coordinate coordinateMin;

    private Coordinate coordinateMax;

    private List<List<Segment>> graph;

    private HashMap<Long, Integer> mapId;

    private Coordinate[] coordinates;
    
    private Pair<Integer,String> wareHouse;
    
    // Changer pour avoir 2 infos : l'index + le temps pour livrer (pair<index,duree> ou Livraison changer l'id)
    private HashMap<Integer,Integer> tabDeliveryPoints;

    public Map() {
        coordinateMax = new Coordinate(-90.0,-180.0);
        coordinateMin = new Coordinate(90.0,180.0);
        mapId = new HashMap<Long,Integer>();
        graph = new ArrayList<>();
        tabDeliveryPoints = new HashMap<>();
    }

    @Override
    public String toString() {
        return "Map{" + "coordinateMin=" + coordinateMin + ", coordinateMax=" 
                + coordinateMax + ", graph=" + graph + ", mapId=" + mapId 
                + ", coordinates=" + coordinates + ", wareHouse=" + wareHouse
                + ", tabDeliveryPoints=" + tabDeliveryPoints + '}';
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

    public Coordinate[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinate[] coordinates) {
        this.coordinates = coordinates;
    }

    public Pair<Integer, String> getWareHouse() {
        return wareHouse;
    }

    public void setWareHouse(Pair<Integer, String> wareHouse) {
        this.wareHouse = wareHouse;
    }

    public HashMap<Integer, Integer> getTabDeliveryPoints() {
        return tabDeliveryPoints;
    }

    public void setTabDeliveryPoints(HashMap<Integer, Integer> tabDeliveryPoints) {
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
                        
                        // initialisation des list dans graph
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
    
    public void fillTabDeliveryPoint(DemandeDeLivraisons ddl) {
        // On remplit d'abord l'objet wareHouse
        Long idEntrepot = Long.valueOf(ddl.getEntrepot().getAdresse());
        int indexEntrepot = mapId.get(idEntrepot);
        // Vérifier que l'heure de départ est valide
        verifyHour(ddl.getEntrepot().getHeureDepart());
        
        wareHouse = new Pair<>(indexEntrepot,ddl.getEntrepot().getHeureDepart());
        
        // On remplit maintenant les deliveryPoint
        Livraison[] livraison = ddl.getLivraison();
        for (int i = 0; i < livraison.length; i++) {
            // On récupére l'index de la livraison
            Long idLivraison = Long.valueOf(livraison[i].getId());
            int indexLivraison = mapId.get(idLivraison);
            
            //Verifier que la duree est bien superieure à 0 si non fait dans le parser
            int dureeLivraison = livraison[i].getDuree();
            if (dureeLivraison > 0) {
                tabDeliveryPoints.put(indexLivraison, dureeLivraison);
            }
        }
    }
    
    // Vérifier que l'heure de départ est correcte
    private boolean verifyHour(String hourToVerify) {
        String[] hourDecomposed = hourToVerify.split(":");
        int heure = Integer.valueOf(hourDecomposed[0]);
        int minute = Integer.valueOf(hourDecomposed[1]);
        int second = Integer.valueOf(hourDecomposed[2]);
        if (heure < 0 || heure > 23 || minute < 0 || minute > 59 || 
                second < 0 || second > 59) {
            System.err.println("Erreur dans l'heure de depart de l'entrepot");
            return false;
        }
        return true;
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
