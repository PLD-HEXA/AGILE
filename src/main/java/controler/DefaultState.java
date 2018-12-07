package controler;

import java.io.File;
import javax.swing.JFileChooser;
import entities.Map;
import entities.Reseau;
import view.MainWindow;


/**
 * The default state of the window.
 * @author PLD-HEXA-301
 *
 */
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
			// Ici rajouter l'appel � la m�thode qui traite l'xml
			Reseau reseau = controler.getParser().parseCityPlan(selectedFile.toString());
			Map map = new Map();
			map.fillMapIdAndCoordinate(reseau);
			map.fillGraph(reseau);
			mainWindow.getTextualView().setItineraries(null);
			mainWindow.getTextualView().repaint();
			mainWindow.getGraphicalView().setItineraries(null);
			mainWindow.getGraphicalView().setMap(map);
			mainWindow.getGraphicalView().setNearestDeliveryPoint(null);
			mainWindow.getGraphicalView().repaint();
			controler.setCurState(controler.planState);
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
