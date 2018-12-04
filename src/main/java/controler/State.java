package controler;

import view.MainWindow;

public interface State {
	
	
	public void loadPlan(Controler controler, MainWindow mainWindow);
	public void loadDeliveries(Controler controler, MainWindow mainWindow);
	public void compute(Controler controler, MainWindow mainWindow);
	
}
