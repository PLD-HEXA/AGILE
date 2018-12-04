package controler;

import view.MainWindow;

/**
 * Represents a window state.
 * @author PLD-HEXA-301
 *
 */
public interface State {
	
	/**
	 * Allows the user to choose a plan file and loads it.
	 * @param controler 
	 * @param mainWindow
	 */
	public void loadPlan(Controler controler, MainWindow mainWindow);
	/**
	 * Allows the user to choose a deliveries file and loads it.
	 * @param controler
	 * @param mainWindow
	 */
	public void loadDeliveries(Controler controler, MainWindow mainWindow);
	/**
	 * Computes the rounds and displays them graphically and textually.
	 * @param controler
	 * @param mainWindow
	 */
	public void compute(Controler controler, MainWindow mainWindow);
	
}
