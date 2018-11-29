package controler;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import entities.DemandeDeLivraisons;
import entities.Reseau;
import view.MainWindow;

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
			mainWindow.getGraphicalView().getMap().fillTabDeliveryPoint(ddl);
			mainWindow.getGraphicalView().repaint();
			controler.setCurState(controler.deliveriesState);

		}
	}

}
