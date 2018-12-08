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
	
	/**
	 * Highlights one specific round according to the delivery point chosen by the user..
	 * @param controler
	 * @param mainWindow
	 */
	public void mouseClick(Controler controler, MainWindow mainWindow,int x,int y);
	
	/**
	 * Allows the user to move dynamically through a specific round.
	 * @param controler
	 * @param mainWindow
	 * @param keyCode
	 */
	public void keyPressed(Controler controler, MainWindow mainWindow,int keyCode);
	
}
