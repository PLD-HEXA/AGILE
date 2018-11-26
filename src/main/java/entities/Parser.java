package entities;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class Parser {

        private Map map;

        public Parser(Map map) {
            this.map = map;
            // En parler demain : il faut latitude max et min ainsi que longitude max et min
            Coordinate coordMax = new Coordinate(-90.0,-180.0);
            Coordinate coordMin = new Coordinate(90.0,180.0);
            this.map.setCoordinateMax(coordMax);
            this.map.setCoordinateMin(coordMin);
        }
        
        
	public static Reseau parseCityPlan(String xmlFileName) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new XmlMapper();
                // To modify if necessary for checking
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        // Reads from XML and converts to POJO
                Reseau res = objectMapper.readValue(StringUtils.toEncodedString(Files.readAllBytes(Paths.get(xmlFileName)), StandardCharsets.UTF_8),Reseau.class);
                return res;       
	}
        
        public void fillMapIdAndCoordinate(Reseau res) {
            int coordinatesLength = res.getNoeud().length;
            Coordinate[] coordinates = new Coordinate[coordinatesLength];
            for(int i = 0; i < coordinatesLength; i++) {
                // Ici, qu'est-ce qu'on en fait de la coordinate
                    Coordinate coord = res.getNoeud()[i].getCoordinate();
                    if(validCoordinate(coord)) {
                            checkMinMaxCoord(coord);
                            Long id = Long.valueOf(res.getNoeud()[i].getId());
                            map.getMapId().put(id, i);
                            coordinates[i] = res.getNoeud()[i].getCoordinate();
                    }
            }
            map.setCoordinates(coordinates);
        }
        
        public void fillGraph(Reseau res) {
            Troncon[] troncon = res.getTroncon();
            for (int i=0; i<troncon.length; i++) {
                int indexOrigine = map.getMapId().get(Long.valueOf(troncon[i].getOrigine()));
                // faille : est-ce que l'Id de la destination est forcément une origine de bas e? --> Vérification
                int indexDestination = map.getMapId().get(Long.valueOf(troncon[i].getDestination()));
                int length = Integer.valueOf(troncon[i].getLongueur());
                Segment segment = new Segment (indexDestination, troncon[i].getNomRue(), length);
                map.getGraph().get(indexOrigine).add(segment);
            }
        }
        private boolean validCoordinate(Coordinate coord) {
            // Faire les verifs liées à une latitude et une longitude
            if (coord.getLatitude() > 90.0 || coord.getLatitude() < -90.0 || coord.getLongitude() > 180.0
                    || coord.getLongitude() < -180.0) {
                return false;
            }
            return true;
        }
        
        private void checkMinMaxCoord(Coordinate coord) {
            if (coord.getLatitude() > map.getCoordinateMax().getLatitude()) {
                map.getCoordinateMax().setLatitude(coord.getLatitude());
            } else if (coord.getLatitude() < map.getCoordinateMin().getLatitude()){
                map.getCoordinateMin().setLatitude(coord.getLatitude());
            }
            if (coord.getLongitude() > map.getCoordinateMax().getLongitude()) {
                map.getCoordinateMax().setLongitude(coord.getLongitude());
            } else if (coord.getLongitude() < map.getCoordinateMin().getLongitude()) {
                map.getCoordinateMin().setLongitude(coord.getLongitude());
            }
        }
}
