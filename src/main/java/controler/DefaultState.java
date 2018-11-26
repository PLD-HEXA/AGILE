package controler;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.swing.JFileChooser;

import entities.Coordinate;
import entities.Map;
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
			
			
			HashMap mapId=new HashMap<Long,Integer>();
			mapId.put(25175791, 0);
			mapId.put(2129259178, 1);
			Coordinate [] coordinates= {new Coordinate(10.0,300.0),new Coordinate(300.0,10.0)};
			List<Segment> segments = new ArrayList<Segment>();
			segments.add(new Segment(2129259178, "Rue Claudius Penet", 104));
			List<List<Segment>> graph = new ArrayList<List<Segment>>();
			graph.add(segments);
			System.out.println(graph);
			Map map = new Map(new Coordinate(10.0,10.0),new Coordinate(300.0,300.0));
			map.setCoordinates(coordinates);
			map.setMapId(mapId);
			map.setGraph(graph);
			System.out.println(map);
			mainWindow.getGraphicalView().drawPlan(map);
			controler.setCurState(controler.planState);
			
		}
	}
	public void loadDeliveries(Controler controler, MainWindow mainWindow) {
	}

}
