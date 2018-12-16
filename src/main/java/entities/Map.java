package entities;

import entities.algorithms.Dijkstra;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javafx.util.Pair;


/**
 * Map contains various information (e.g. attribute of the class) about the map
 * and the deliveries that the user has given in his xml document
 *
 * @author PLD-HEXA-301
 */
public class Map {

    /**
     * the coordinate with the minimum latitude and the minimum longitude
     */
    private Coordinate coordinateMin;

    /**
     * the coordinate with the maximum latitude and the maximum longitude
     */
    private Coordinate coordinateMax;

    /**
     * graph is a list which contains list of Segment. The index in the first
     * list represents the index of the coordinates in the city plan. The second
     * list of Segment represents the reachable Segment from this coordinate
     */
    private List<List<Segment>> graph;

    /**
     * mapId is a HashMap with the key representing the id in the xml document
     * and the value which is the new index that we define corresponding to the
     * id
     */
    private HashMap<Long, Integer> mapId;

    /**
     * This array allows to map an index and his corresponding coordinate
     */
    private Coordinate[] coordinates;

    /**
     * wareHouse is a Pair with the first element representing the id (adress)
     * of the warehouse and the second element the departure time for the
     * deliveries tour of the day
     */
    private Pair<Integer, String> wareHouse;

    /**
     * tabDeliveryPoints is a List of Pair with the first element representing
     * the index (adress) of the delivery point and the second element which
     * represents the duration at the delivery point in second
     */
    private List<Pair<Integer, Integer>> tabDeliveryPoints;

    /**
     * unreachablePoints contains the index of all the points that we cannot
     * reach from the warehouse
     */
    private List<Integer> unreachablePoints;

    /**
     * nonReturnPoints contains the index of all the points from which there is
     * no possible return in the graph
     */
    private List<Integer> nonReturnPoints;

    /**
     * Default constructor of the class Map
     */
    public Map() {
        coordinateMax = new Coordinate(-90.0, -180.0);
        coordinateMin = new Coordinate(90.0, 180.0);
        mapId = new HashMap<>();
        graph = new ArrayList<>();
        tabDeliveryPoints = new ArrayList<>();
    }

    /**
     * Gives all the information of the attribute of the class in the form of a
     * string
     *
     * @return a String with the different attribute data of the class Map
     */
    @Override
    public String toString() {
        return "Map{" + "coordinateMin=" + coordinateMin + ", coordinateMax="
                + coordinateMax + ", graph=" + graph + ", mapId=" + mapId
                + ", coordinates=" + coordinates + ", wareHouse=" + wareHouse
                + ", tabDeliveryPoints=" + tabDeliveryPoints + '}';
    }

    /**
     * Gets coordinateMin
     *
     * @return CoordinateMin which contains the minimum latitude and the minimum
     * longitude
     */
    public Coordinate getCoordinateMin() {
        return coordinateMin;
    }

    /**
     * Sets coordinateMin
     *
     * @param coordinateMin which contains the minimum latitude and the minimum
     * longitude
     */
    public void setCoordinateMin(Coordinate coordinateMin) {
        this.coordinateMin = coordinateMin;
    }

    /**
     * Gets coordinateMax
     *
     * @return CoordinateMax which contains the maximum latitude and the maximum
     * longitude
     */
    public Coordinate getCoordinateMax() {
        return coordinateMax;
    }

    /**
     * Sets coordinateMax
     *
     * @param coordinateMax which contains the maximum latitude and the maximum
     * longitude
     */
    public void setCoordinateMax(Coordinate coordinateMax) {
        this.coordinateMax = coordinateMax;
    }

    /**
     * Gets graph
     *
     * @return Graph
     */
    public List<List<Segment>> getGraph() {
        return graph;
    }

    /**
     * Sets graph
     *
     * @param graph
     */
    public void setGraph(List<List<Segment>> graph) {
        this.graph = graph;
    }

    /**
     * Gets mapId
     *
     * @return mapId
     */
    public HashMap<Long, Integer> getMapId() {
        return mapId;
    }

    /**
     * Sets mapId
     *
     * @param mapId
     */
    public void setMapId(HashMap<Long, Integer> mapId) {
        this.mapId = mapId;
    }

    /**
     * Gets a coordinate in the array coordinates
     *
     * @param index the corresponding indexof the coordinate in coordinates
     * @return the coordinate to the corresponding index
     */
    public Coordinate getCoordinate(int index) {
        return this.coordinates[index];
    }

    /**
     * Gets unreachablePoints
     *
     * @return a list of Integer with the index of the unreachablePoints
     */
    public List<Integer> getUnreachablePoints() {
        return unreachablePoints;
    }

    /**
     * Gets coordinates
     *
     * @return an array with the coordinates
     */
    public Coordinate[] getCoordinates() {
        return coordinates;
    }

    /**
     * Sets coordinates
     *
     * @param coordinates
     */
    public void setCoordinates(Coordinate[] coordinates) {
        this.coordinates = coordinates;
    }

    /**
     * Gets waraHouse
     *
     * @return wareHouse, a Pair<Integer,String>
     */
    public Pair<Integer, String> getWareHouse() {
        return wareHouse;
    }

    /**
     * Sets wareHouse
     *
     * @param wareHouse
     */
    public void setWareHouse(Pair<Integer, String> wareHouse) {
        this.wareHouse = wareHouse;
    }

    /**
     * Gets tabDeliveryPoints
     *
     * @return tabDeliveryPoints
     */
    public List<Pair<Integer, Integer>> getTabDeliveryPoints() {
        return tabDeliveryPoints;
    }

    /**
     * Gets nonReturnPoints
     *
     * @return a list of Integer with the index of the non return points
     */
    public List<Integer> getNonReturnPoints() {
        return nonReturnPoints;
    }

    /**
     * Sets tabDeliveryPoints
     *
     * @param tabDeliveryPoints
     */
    public void setTabDeliveryPoints(List<Pair<Integer, Integer>> tabDeliveryPoints) {
        this.tabDeliveryPoints = tabDeliveryPoints;
    }

    /**
     * Fills mapId and Coordinates with the data contained in param res. The
     * data must be valid (e.g. id of the node is a number and is > 0 and a
     * number, non-null tag noeud or troncon, non-null attribute, length > 0,
     * ...). If data in res are invalid, mapId and coordinates are null
     *
     * @param res contains the information retrieved from the xml document in
     * order to draw the map
     */
    public void fillMapIdAndCoordinate(Reseau res) {
        if (res.getNoeud() != null && res.getNoeud().length >= 0
                && res.getTroncon() != null && res.getTroncon().length >= 0) {

            int coordinatesLength = res.getNoeud().length;
            coordinates = new Coordinate[coordinatesLength];
            int j = 0;
            for (int i = 0; i < coordinatesLength; i++) {
                Coordinate coord = res.getNoeud()[i].getCoordinate();
                try {
                    Long id = Long.valueOf(res.getNoeud()[i].getId());
                    if (validCoordinate(coord) && id > 0) {
                        checkMinMaxCoord(coord);
                        mapId.put(id, j);
                        coordinates[j] = res.getNoeud()[i].getCoordinate();

                        // initialization of the different list needed for graph
                        List<Segment> listSegment = new ArrayList<>();
                        graph.add(j, listSegment);
                        j++;
                    } else {
                        mapId = null;
                        coordinates = null;
                        break;
                    }
                } catch (NumberFormatException ex) {
                    mapId = null;
                    coordinates = null;
                    break;
                }
            }
            if (coordinates != null && coordinates.length == 0) {
                mapId = null;
                coordinates = null;
            }
        } else {
            mapId = null;
            coordinates = null;
        }
    }

    /**
     * Fills the graph with the data contained in the object res. The data must
     * be valid (e.g. id of the segment origine and segment destination are
     * numbers and are > 0, non-null tag and id of the segment origine and
     * segment destination contained in mapId, ...) If data in res are invalid,
     * graph is null
     *
     * @param res contains the information retrieve from the xml document in
     * order to draw the map
     */
    public void fillGraph(Reseau res) {
        if (res.getTroncon() != null && res.getTroncon().length != 0) {
            Troncon[] troncon = res.getTroncon();
            int compteurObjet = 0;
            for (int i = 0; i < troncon.length; i++) {
                try {
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
                                    Segment segment = new Segment(indexDestination, troncon[i].getNomRue(), length);
                                    graph.get(indexOrigine).add(segment);
                                    compteurObjet++;
                                } else {
                                    graph = null;
                                    break;
                                }
                            } else {
                                graph = null;
                                break;
                            }
                        } else {
                            graph = null;
                            break;
                        }
                    } else {
                        graph = null;
                        break;
                    }
                } catch (NumberFormatException e) {
                    graph = null;
                    break;
                }
            }
            if (compteurObjet == 0) {
                graph = null;
            }
        } else {
            graph = null;
        }
    }

    /**
     * Fills the list of unreachablePoints with the help of Dijkstra algorithm
     */
    public void fillUnreachablePoints() {
        this.unreachablePoints = new ArrayList<>();

        //Find the unreachable points in the graph
        Dijkstra dijkstra = new Dijkstra(this.graph);
        dijkstra.executeDijkstra(this.wareHouse.getKey(), null);
        double[] distance = dijkstra.getDistance();
        for (int i = 0; i < distance.length; i++) {
            if (distance[i] == Double.MAX_VALUE) {
                this.unreachablePoints.add(i);
            }
        }
    }

    /**
     * Fills the list of nonReturnPoints
     */
    public void fillNonReturnPoints() {
        this.nonReturnPoints = new ArrayList<>();

        //Find the points from which there is no possible return in the graph
        List<Integer> tempNonReturnPoints1 = new ArrayList<>();
        for (int i = 0; i < graph.size(); i++) {
            if (graph.get(i).isEmpty()) {
                nonReturnPoints.add(i);
                tempNonReturnPoints1.add(i);
            }
        }

        List<Integer> tempNonReturnPoints2;
        while (!tempNonReturnPoints1.isEmpty()) {
            tempNonReturnPoints2 = new ArrayList<>();
            for (int i = 0; i < graph.size(); i++) {
                if (graph.get(i).size() == 1 && tempNonReturnPoints1.contains(graph.get(i).get(0).getDestIndex())) {
                    tempNonReturnPoints2.add(i);
                    nonReturnPoints.add(i);
                }
            }
            tempNonReturnPoints1 = new ArrayList<>();
            tempNonReturnPoints1.addAll(tempNonReturnPoints2);
        }
    }

    /**
     * Fills tabDeliveryPoints with the data contained in param ddl. The data
     * must be valid (e.g. attribute adress > 0, non-null tag Entrepot and
     * livraison, non-null attribute, attribute duration > 0, attribute adress
     * which represents an id must be contained in mapId, ...). If data in res
     * are invalid, tabDeliveryPoints is null
     *
     * @param ddl contains the information retrieve from the xml document in
     * order to draw the deliveryPoints on the city plan
     */
    public void fillTabDeliveryPoint(DemandeDeLivraisons ddl) {
        // We fill the object wareHouse first
        if (ddl.getEntrepot() != null && ddl.getLivraison() != null
                && ddl.getLivraison().length >= 0 && ddl.getEntrepot().getAdresse() != null) {
            try {
                Long idEntrepot = Long.valueOf(ddl.getEntrepot().getAdresse());
                if (idEntrepot != null && idEntrepot > 0 && mapId.get(idEntrepot) != null) {
                    int indexEntrepot = mapId.get(idEntrepot);
                    // We verify that the departure time is valid and with the
                    // right format
                    verifyHour(ddl.getEntrepot().getHeureDepart());

                    wareHouse = new Pair<>(indexEntrepot, ddl.getEntrepot().getHeureDepart());

                    // On fills the delivery points
                    Livraison[] livraison = ddl.getLivraison();
                    for (int i = 0; i < livraison.length; i++) {
                        // We get back the index of the delivery
                        Long idLivraison = Long.valueOf(livraison[i].getId());
                        if (idLivraison > 0 && mapId.get(idLivraison) != null && livraison[i].getDuree() != null) {
                            int indexLivraison = mapId.get(idLivraison);
                            int dureeLivraison = livraison[i].getDuree();
                            if (dureeLivraison >= 0) {
                                tabDeliveryPoints.add(new Pair<>(indexLivraison, dureeLivraison));
                            } else {
                                wareHouse = null;
                                tabDeliveryPoints = null;
                                break;
                            }
                        } else {
                            wareHouse = null;
                            tabDeliveryPoints = null;
                            break;
                        }
                    }
                    if (tabDeliveryPoints != null && tabDeliveryPoints.isEmpty()) {
                        wareHouse = null;
                        tabDeliveryPoints = null;
                    }
                } else {
                    wareHouse = null;
                    tabDeliveryPoints = null;
                }
            } catch (NumberFormatException e) {
                wareHouse = null;
                tabDeliveryPoints = null;
            }
        } else {
            wareHouse = null;
            tabDeliveryPoints = null;
        }
    }

    /**
     * Verifies if the hour given in parameter is valid and in the right format
     *
     * @param hourToVerify a String
     * @return true if the param hourToVerify is valid, false otherwise
     */
    private boolean verifyHour(String hourToVerify) {

        // We verify that the format is valid
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        sdf.setLenient(true);
        Date d = new Date();
        try {
            d = sdf.parse(hourToVerify);
        } catch (ParseException e) {
            System.err.println("Error with departure time at the warehouse : " + e);
            return false;
        }

        String[] hourDecomposed = hourToVerify.split(":");
        int heure = Integer.valueOf(hourDecomposed[0]);
        int minute = Integer.valueOf(hourDecomposed[1]);
        int second = Integer.valueOf(hourDecomposed[2]);
        if (heure < 0 || heure > 23 || minute < 0 || minute > 59
                || second < 0 || second > 59) {
            System.err.println("Error with departure time at the warehouse");
            return false;
        }
        return true;
    }

    /**
     * Validates if the coordinate given in parameter is valid.
     *
     * @param coord the coordinate with his longitude and latitude to verify
     * @return true if the coordinate is valid, false otherwise
     */
    private boolean validCoordinate(Coordinate coord) {
        if (coord.getLatitude() == null || coord.getLongitude() == null) {
            return false;
        }

        if (coord.getLatitude() > 90.0 || coord.getLatitude() < -90.0 || coord.getLongitude() > 180.0
                || coord.getLongitude() < -180.0) {
            return false;
        }

        return true;
    }

    /**
     * Checks if the coordinate given in paramater has a longitude or/and a
     * latitude greater than those of coordinateMax or lower than those of
     * coordinateMin. If the latitude and/or longitude are greater or lower, we
     * replace the corresponding attribute in coordinateMax and/or coordinateMin
     *
     * @param coord a Coordinate
     */
    private void checkMinMaxCoord(Coordinate coord) {
        if (coord.getLatitude() > coordinateMax.getLatitude()) {
            coordinateMax.setLatitude(coord.getLatitude());
        } else if (coord.getLatitude() < coordinateMin.getLatitude()) {
            coordinateMin.setLatitude(coord.getLatitude());
        }
        if (coord.getLongitude() > coordinateMax.getLongitude()) {
            coordinateMax.setLongitude(coord.getLongitude());
        } else if (coord.getLongitude() < coordinateMin.getLongitude()) {
            coordinateMin.setLongitude(coord.getLongitude());
        }
    }
}
