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

    public Parser( ) {
    }

    public Reseau parseCityPlan(String xmlFileName) throws JsonParseException, JsonMappingException, IOException {
            ObjectMapper objectMapper = new XmlMapper();
            // To modify if necessary for checking
            objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            // Reads from XML and converts to POJO
            Reseau res = objectMapper.readValue(StringUtils.toEncodedString(Files.readAllBytes(Paths.get(xmlFileName)), StandardCharsets.UTF_8),Reseau.class);
            return res;       
    }
    
    public DemandeDeLivraisons parseDelivery(String xmlFileName) throws JsonParseException, JsonMappingException, IOException {
            ObjectMapper objectMapper = new XmlMapper();
            // To modify if necessary for checking
            objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            // Reads from XML and converts to POJO
            DemandeDeLivraisons ddl = objectMapper.readValue(StringUtils.toEncodedString(Files.readAllBytes(Paths.get(xmlFileName)), StandardCharsets.UTF_8),DemandeDeLivraisons.class);
            return ddl;       
    }
     
}
