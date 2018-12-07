package controler;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import entities.DemandeDeLivraisons;
import entities.Itinerary;
import view.MainWindow;

/**
 * The state after the deliveries have been loaded.
 * @author PLD-HEXA-301
 *
 */
public class DeliveriesState extends DefaultState{
	
	@Override
	public void compute(Controler controler, MainWindow mainWindow) {
		int numberOfDeliveryMen =(int)mainWindow.getInputView().getNumOfRounds().getValue();
		int numberOfDeliveries = mainWindow.getGraphicalView().getMap().getTabDeliveryPoints().size();
		if(numberOfDeliveryMen<=numberOfDeliveries) {
			List<Itinerary> itineraries = controler.getPathFinder().findPath (mainWindow.getGraphicalView().getMap(),
					numberOfDeliveryMen);
			if(itineraries != null) {
				mainWindow.getGraphicalView().setItineraries(itineraries);
				mainWindow.getGraphicalView().repaint();
				mainWindow.getTextualView().setItineraries(itineraries);
				mainWindow.getTextualView().displayListOfRounds();
				mainWindow.getTextualView().revalidate();
				mainWindow.getTextualView().repaint();
				controler.setCurState(controler.computeState);
			}
			else {
				//TODO : pop up erreur de calcul
			}
		}
		else {
			//TODO : pop up où on dit
			//On peut te le calculer mais il y aura (numberOfDeliveryMen-numberOfDeliveries) livreurs qui n'auront rien à faire
			// si l'utilisateur valide on lui fait le calcul comme ci-dessus, sinon on ne fait rien
		}
	}
	
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
			DemandeDeLivraisons ddl;
			ddl = controler.getParser().parseDelivery(selectedFile.toString());
			mainWindow.getGraphicalView().getMap().setTabDeliveryPoints(new ArrayList<>());
			mainWindow.getGraphicalView().getMap().fillTabDeliveryPoint(ddl);
			mainWindow.getGraphicalView().setItineraries(null);
			mainWindow.getGraphicalView().repaint();
			mainWindow.getTextualView().setItineraries(null);
			mainWindow.getTextualView().repaint();
			// On reste dans le même état
                        // controler.setCurState(controler.deliveriesState);
		}
	}
}
