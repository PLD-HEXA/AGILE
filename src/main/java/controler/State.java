package controler;

import view.MainWindow;

/**
 * Represents a window state.
 *
 * @author PLD-HEXA-301
 */
public interface State {

    /**
     * Allows the user to choose a plan file and loads it.
     *
     * @param controller
     * @param mainWindow
     */
    public void loadPlan(Controller controller, MainWindow mainWindow);

    /**
     * Allows the user to choose a deliveries file and loads it.
     *
     * @param controller
     * @param mainWindow
     */
    public void loadDeliveries(Controller controller, MainWindow mainWindow);

    /**
     * Computes the rounds and displays them graphically and textually.
     *
     * @param controller
     * @param mainWindow
     */
    public void compute(Controller controller, MainWindow mainWindow);

    /**
     * Undoes a user command.
     *
     * @param cmdList
     */
    public void undo(CmdList cmdList);

    /**
     * Redoes a user command.
     *
     * @param cmdList
     */
    public void redo(CmdList cmdList);

    /**
     * Highlights one specific round according to the delivery point chosen by the
     * user..
     *
     * @param controller
     * @param mainWindow
     */
    public void mouseClick(Controller controller, MainWindow mainWindow, int x, int y);

    /**
     * Deletes one specific round according to the delivery point chosen by the
     * user.
     *
     * @param controller
     * @param mainWindow
     */
    public void mouseClick(Controller controller, MainWindow mainWindow, CmdList cmdList, int x, int y);

    /**
     * The user has clicked on the button delete delivery point, therefore we enter
     * in the state deleteState
     *
     * @param controller
     */
    public void clickAddButton(Controller controller);

    /**
     * The user has clicked on the button add delivery point, therefore we enter in
     * the state addState
     *
     * @param controller
     */
    public void clickDeleteButton(Controller controller);

    /**
     * Allows the user to move dynamically through a specific round.
     *
     * @param controller
     * @param mainWindow
     * @param keyCode
     */
    public void keyPressed(Controller controller, MainWindow mainWindow, int keyCode);

    /**
     * Resets the window and all of its components to the initial state.
     *
     * @param controller
     * @param mainWindow
     */
    public void reset(Controller controller, MainWindow mainWindow);
}
