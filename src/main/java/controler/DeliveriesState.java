package controler;

import java.util.List;

import entities.Itinerary;
import entities.Map;
import view.MainWindow;

public class DeliveriesState extends DefaultState{
	
	public void compute(Controler controler, MainWindow mainWindow) {
		List<Itinerary> itineraries = controler.getPathFinder().findPath (mainWindow.getGraphicalView().getMap(),
				(int)mainWindow.getInputView().getNumOfRounds().getValue());
		System.out.println((int)mainWindow.getInputView().getNumOfRounds().getValue());
		
//		
		mainWindow.getGraphicalView().setItineraries(itineraries);
		mainWindow.getGraphicalView().repaint();
		mainWindow.getTextualView().setItineraries(itineraries);
		mainWindow.getTextualView().displayListOfRounds();
		mainWindow.getTextualView().revalidate();
		mainWindow.getTextualView().repaint();
		controler.setCurState(controler.deliveriesState);
		
	}
	

}
