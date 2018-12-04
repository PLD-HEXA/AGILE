package controler;

import java.io.File;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import entities.DemandeDeLivraisons;
import view.MainWindow;

/**
 * The state after a plan has been loaded.
 * @author PLD-HEXA-301
 *
 */
public class PlanState extends DefaultState {

	@Override
	public void loadDeliveries(Controler controler, MainWindow mainWindow) {
		mainWindow.displayMessage("Load deliveries");
		mainWindow.displayMessage("Load plan");
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File("/"));
		chooser.changeToParentDirectory();
		mainWindow.add(chooser);
		int returnValue = chooser.showOpenDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = chooser.getSelectedFile();
			controler.setCurState(controler.planState);
			DemandeDeLivraisons ddl;
			ddl = controler.getParser().parseDelivery(selectedFile.toString());
			mainWindow.getGraphicalView().getMap().setTabDeliveryPoints(new ArrayList<>());
			mainWindow.getGraphicalView().getMap().fillTabDeliveryPoint(ddl);
			mainWindow.getGraphicalView().setItineraries(null);
			mainWindow.getGraphicalView().repaint();
			mainWindow.getTextualView().setItineraries(null);
			mainWindow.getTextualView().repaint();
			controler.setCurState(controler.deliveriesState);
		}
	}

}
