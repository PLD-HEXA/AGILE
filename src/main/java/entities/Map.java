package entities;

import java.util.ArrayList;
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
    private List<Pair<Integer,Integer>> tabDeliveryPoints;


    public Map() {
        coordinateMax = new Coordinate(-90.0,-180.0);
        coordinateMin = new Coordinate(90.0,180.0);
        mapId = new HashMap<Long,Integer>();
        graph = new ArrayList<>();
        tabDeliveryPoints = new ArrayList<>();
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

    public Coordinate getCoordinate(int index) {
        return this.coordinates[index];
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

    public List<Pair<Integer, Integer>> getTabDeliveryPoints() {
        return tabDeliveryPoints;
    }

    public void setTabDeliveryPoints(List<Pair<Integer, Integer>> tabDeliveryPoints) {
        this.tabDeliveryPoints = tabDeliveryPoints;
    }
    
    public void fillMapIdAndCoordinate(Reseau res) {
        // Cas ou les balises noeuds ou troncons etaient manquantes
        if (res.getNoeud() != null && res.getNoeud().length != 0 &&
            res.getTroncon() != null && res.getTroncon().length != 0) {
            
            int coordinatesLength = res.getNoeud().length;
            coordinates = new Coordinate[coordinatesLength];
            int j = 0;
            for(int i = 0; i < coordinatesLength; i++) {
                // Ici, qu'est-ce qu'on en fait de la coordinate
                    Coordinate coord = res.getNoeud()[i].getCoordinate();
                    Long id = Long.valueOf(res.getNoeud()[i].getId());
                    if(validCoordinate(coord) && id>0) {
                            checkMinMaxCoord(coord);
                            mapId.put(id, j);
                            coordinates[j] = res.getNoeud()[i].getCoordinate();

                            // initialisation des list dans graph
                            List<Segment> listSegment = new ArrayList<>();
                            graph.add(j,listSegment);
                            j++;
                    }
            }
            if (coordinates.length == 0) {
                mapId = null;
                coordinates = null;
            }
        } else {
            mapId = null;
            coordinates = null;
        }
    }

    public void fillGraph(Reseau res) {
        if (res.getTroncon() != null && res.getTroncon().length != 0) {
            Troncon[] troncon = res.getTroncon();
            int compteurObjet = 0;
            for (int i=0; i<troncon.length; i++) {
                Long idOrigine = Long.valueOf(troncon[i].getOrigine());
                if (idOrigine > 0 && mapId.get(idOrigine) != null) {
                    int indexOrigine = mapId.get(idOrigine);
                    Long idDestination = Long.valueOf(troncon[i].getDestination());
                    // We verify that the id of the destination exists in the mapId, 
                    // otherwise, we do not take into account the segment
                    if (idDestination > 0 && mapId.get(idDestination) != null) {
                        int indexDestination = mapId.get(idDestination);
                        if (troncon[i].getLongueur() != null) {
                            double length = Double.valueOf(troncon[i].getLongueur()); 
                            if (length >= 0) {
                                // On ajoute que si la longueur est supérieure à 0
                                // et que l'id est un id connu
                                Segment segment = new Segment (indexDestination, troncon[i].getNomRue(), length);
                                graph.get(indexOrigine).add(segment);
                                compteurObjet++;
                            }
                        }
                    }
                } 
            }
            if (compteurObjet == 0) {
                graph = null;
            }
        }
        else {
            graph = null;
        }
    }
    
    public void fillTabDeliveryPoint(DemandeDeLivraisons ddl) {
        // On remplit d'abord l'objet wareHouse
        if (ddl.getEntrepot() != null && ddl.getLivraison() != null &&
                ddl.getLivraison().length != 0 && ddl.getEntrepot().getAdresse() != null) {
            
            Long idEntrepot = Long.valueOf(ddl.getEntrepot().getAdresse());
            if (idEntrepot > 0) {
                int indexEntrepot = mapId.get(idEntrepot);
                // Verifier que l'heure de depart est valide
                verifyHour(ddl.getEntrepot().getHeureDepart());

                wareHouse = new Pair<>(indexEntrepot,ddl.getEntrepot().getHeureDepart());

                // On remplit maintenant les deliveryPoint
                Livraison[] livraison = ddl.getLivraison();
                for (int i = 0; i < livraison.length; i++) {
                    // On r�cup�re l'index de la livraison
                    Long idLivraison = Long.valueOf(livraison[i].getId());
                    if (idLivraison > 0 && mapId.get(idLivraison) != null) {
                        int indexLivraison = mapId.get(idLivraison);

                        //Verifier que la duree est bien superieure � 0 si non fait dans le parser
                        int dureeLivraison = livraison[i].getDuree();
                        if (dureeLivraison > 0) {
                            tabDeliveryPoints.add(new Pair<>(indexLivraison, dureeLivraison));
                        }
                    }
                }
                if (tabDeliveryPoints.isEmpty()) {
                    tabDeliveryPoints = null;
                }
            }
            else {
                tabDeliveryPoints = null;
            }
        }
        else {
            tabDeliveryPoints = null;
        }
    }
    
    // Verifier que l'heure de depart est correcte
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
    
    private boolean validCoordinate(Coordinate coord) {
        if (coord.getLatitude() == null || coord.getLongitude() == null) {
            return false;
        }
        
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
