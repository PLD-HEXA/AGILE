package controler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.swing.JFileChooser;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import entities.Coordinate;
import entities.Map;
import entities.Reseau;
import entities.Segment;
import view.MainWindow;

public class DefaultState implements State {

	@Override
	public void loadPlan(Controler controler, MainWindow mainWindow) {
		mainWindow.displayMessage("Load plan");
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File("/"));
		chooser.changeToParentDirectory();
		mainWindow.add(chooser);
		int returnValue = chooser.showOpenDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = chooser.getSelectedFile();
			// Ici rajouter l'appel a la methode qui traite l'xml
			Reseau reseau = controler.getParser().parseCityPlan(selectedFile.toString());
      if (reseau != null) {
          Map map = new Map();
          map.fillMapIdAndCoordinate(reseau);
          if (map.getMapId() != null && map.getCoordinates() != null) {
              map.fillGraph(reseau);
              if (map.getGraph() != null) {
                  mainWindow.getTextualView().setItineraries(null);
                  mainWindow.getTextualView().repaint();
                  mainWindow.getGraphicalView().setItineraries(null);
                  mainWindow.getGraphicalView().setMap(map);
                  mainWindow.getGraphicalView().setNearestDeliveryPoint(null);
                  mainWindow.getGraphicalView().repaint();
                  controler.setCurState(controler.planState);
              }
              else {
                  mainWindow.showError("The content"
                          + " of the input xml file is invalid.");
                  // Le contenu du xml est incorrect
                  // Ici, due à un tronçon dont l'un des attributs à une valeur incorrecte
              }
          }
          else {
              mainWindow.showError("The content of"
                      + " the input xml file is invalid.");
              // Le plan n'est pas valide (Ici, 
              // car un attribut est incorrect ou bien aucune infos n'est valable)
          }
      } 
      else {
          mainWindow.showError("The input xml file"
                  + " is invalid");
          // Le plan n'est pas valide (Ici, cas
          // ou l'extension est incorrecte, une balise non connue est ajoutée,
          // un attribut est rajoutée)
      }
		}
	}

	@Override
	public void loadDeliveries(Controler controler, MainWindow mainWindow) {
	}

	@Override
	public void compute(Controler controler, MainWindow mainWindow) {

	}

	@Override
	public void mouseClick(Controler controler, MainWindow mainWindow, int x , int y) {		
	}
}
