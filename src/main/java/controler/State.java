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
        
        public void undo(CmdList cmdList);
        
        public void redo(CmdList cmdList);
	
	/**
	 * Highlights one specific round according to the delivery point chosen by the user..
	 * @param controler
	 * @param mainWindow
	 */
	public void mouseClick(Controler controler, MainWindow mainWindow,int x,int y);
        
        /**
	 * Deletes one specific round according to the delivery point chosen by the user.
	 * @param controler
	 * @param mainWindow
	 */
	public void mouseClick(Controler controler, MainWindow mainWindow,CmdList cmdList, int x,int y);
        
        /**
	 * The user has clicked on the button delete delivery point, therefore 
         * we enter in the state deleteState
	 * @param controler
	 * @param mainWindow
	 */
	public void clickAddButton(Controler controler);
        
        /**
	 * The user has clicked on the button add delivery point, therefore 
         * we enter in the state addState
	 * @param controler
	 * @param mainWindow
	 */
	public void clickDeleteButton(Controler controler);
        
        /**
	 * Deleted one specific delivery point according to the delivery point chosen by the user.
	 * @param controler
	 * @param mainWindow
	 */
	public void iconeClick(Controler controler, MainWindow mainWindow,int x,int y);
	
	/**
	 * Allows the user to move dynamically through a specific round.
	 * @param controler
	 * @param mainWindow
	 * @param keyCode
	 */
	public void keyPressed(Controler controler, MainWindow mainWindow,int keyCode);
	
}
