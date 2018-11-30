package controler;

import java.io.File;
import java.util.List;

import javax.swing.JFileChooser;

import entities.DemandeDeLivraisons;
import entities.Itinerary;
import entities.Map;
import view.MainWindow;

public class DeliveriesState extends DefaultState{
	
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
	
	

}
