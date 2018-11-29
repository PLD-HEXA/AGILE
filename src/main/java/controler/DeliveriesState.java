package controler;

import java.util.List;

import entities.Itinerary;
import entities.Map;
import view.MainWindow;

public class DeliveriesState extends DefaultState{
	
	public void compute(Controler controler, MainWindow mainWindow) {
		List<Itinerary> itineraries = controler.getPathFinder().findPath (mainWindow.getGraphicalView().getMap(),5);
		mainWindow.getTextualView().displayListOfRounds(itineraries);
		mainWindow.getTextualView().repaint();
		mainWindow.getGraphicalView().setItineraries(itineraries);
		mainWindow.getGraphicalView().repaint();
		controler.setCurState(controler.deliveriesState);
		
	}
	

}
