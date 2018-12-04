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
import java.util.logging.Level;
import java.util.logging.Logger;

public class Parser {

    public Parser( ) {
    }

    public Reseau parseCityPlan(String xmlFileName){
        if (xmlFileName.endsWith(".xml")){
            ObjectMapper objectMapper = new XmlMapper();
            // To modify if necessary for checking
            objectMapper.enable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            objectMapper.enable(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES);
            // Reads from XML and converts to POJO
            Reseau res = null;
            try {
                res = objectMapper.readValue(StringUtils.toEncodedString(Files.readAllBytes(Paths.get(xmlFileName)), StandardCharsets.UTF_8),Reseau.class);
            } catch (JsonParseException ex) {
                Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            } catch (JsonMappingException ex) {
                Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            } catch (IOException ex) {
                Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
            return res;      
        }
        else {
            return null;
        }
    }
    
    public DemandeDeLivraisons parseDelivery(String xmlFileName){
        if (xmlFileName.endsWith(".xml")){  
            ObjectMapper objectMapper = new XmlMapper();
            objectMapper.enable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            objectMapper.enable(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES);
            // Reads from XML and converts to POJO
            DemandeDeLivraisons ddl = null;
            try {
                ddl = objectMapper.readValue(StringUtils.toEncodedString(Files.readAllBytes(Paths.get(xmlFileName)), StandardCharsets.UTF_8),DemandeDeLivraisons.class);
            }catch (JsonParseException ex) {
                Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            } catch (JsonMappingException ex) {
                Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            } catch (IOException ex) {
                Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
            return ddl;
        }
        else {
            return null;
        }
    }
     
}
