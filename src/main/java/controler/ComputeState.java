package controler;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;

import entities.DemandeDeLivraisons;
import entities.Itinerary;
import view.MainWindow;

public class ComputeState extends DefaultState {
	
	@Override
	public void compute(Controler controler, MainWindow mainWindow) {
		List<Itinerary> itineraries = controler.getPathFinder().findPath (mainWindow.getGraphicalView().getMap(),
				(int)mainWindow.getInputView().getNumOfRounds().getValue());
		System.out.println((int)mainWindow.getInputView().getNumOfRounds().getValue());
		mainWindow.getGraphicalView().setItineraries(itineraries);
		mainWindow.getGraphicalView().repaint();
		mainWindow.getTextualView().setItineraries(itineraries);
		mainWindow.getTextualView().displayListOfRounds();
		mainWindow.getTextualView().revalidate();
		mainWindow.getTextualView().repaint();
		controler.setCurState(controler.computeState);
		
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
