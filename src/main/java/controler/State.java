package controler;

import view.MainWindow;

public interface State {
	
	
	public void loadPlan(Controler controler, MainWindow mainWindow);
	public void loadDeliveries(Controler controler, MainWindow mainWindow);
	public void compute(Controler controler, MainWindow mainWindow);
	
	/**
	 * Highlights one specific round according to the delivery point chosen by the user..
	 * @param controler
	 * @param mainWindow
	 */
	public void mouseClick(Controler controler, MainWindow mainWindow,int x,int y);
	
}
