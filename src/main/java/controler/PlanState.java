package controler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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
                    DemandeDeLivraisons ddl = controler.getParser().parseDelivery(selectedFile.toString());
                    if (ddl != null) {
                        mainWindow.getGraphicalView().getMap().fillTabDeliveryPoint(ddl);
                        mainWindow.getGraphicalView().setItineraries(null);
                        mainWindow.getGraphicalView().repaint();
                        mainWindow.getTextualView().setItineraries(null);
                        mainWindow.getTextualView().repaint();
                        controler.setCurState(controler.deliveriesState);
                    }
                    else {
                        mainWindow.showError("The input xml file is invalid.");
                        // TODO : Afficher mesg d'erreur à l'écran (cas ou le fichier
                        // est invalide : extension, balise et/ou attribut en trop ...)
                    }
		}
	}

}
