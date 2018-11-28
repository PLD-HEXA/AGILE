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

public class DefaultState implements State{
	
	public void loadPlan(Controler controler, MainWindow mainWindow) {
		mainWindow.displayMessage("Load plan");
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File("/"));
		chooser.changeToParentDirectory();
		mainWindow.add(chooser);
		int returnValue = chooser.showOpenDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = chooser.getSelectedFile();
			//Ici rajouter l'appel à la méthode qui traite l'xml
			try {
				Reseau reseau= controler.getParser().parseCityPlan(selectedFile.toString());
				Map map= new Map();
				map.fillMapIdAndCoordinate(reseau);
				map.fillGraph(reseau);
				mainWindow.getGraphicalView().setMap(map);
				System.out.println(map);
	            mainWindow.getGraphicalView().repaint();
				controler.setCurState(controler.planState);
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}         
		}
	}
	public void loadDeliveries(Controler controler, MainWindow mainWindow) {
	}

}
