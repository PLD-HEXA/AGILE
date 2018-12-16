package controler;

import entities.Parser;
import entities.algorithms.PathFinder;
import view.MainWindow;

/**
 * TODO Reprents? Represents may be?
 * Reprents the controller that orchestrates the different states and changes of the window according
 * to those of the entities.
 *
 * @author PLD-HEXA-301
 */
public class Controller {

    /**
     * Is used to find the shortest path for the different deliveries.
     */
    private PathFinder pathFinder;
    /**
     * Is used to parse the xml file.
     */
    private Parser parser;
    /**
     * The user command list.
     */
    private CmdList cmdList;
    /**
     * The main window.
     */
    private MainWindow mainWindow;
    /**
     * The current state of the window.
     */
    private State curState;
    /**
     * The initial state.
     */
    protected final InitState initState = new InitState();
    /**
     * The state after a plan has been loaded.
     */
    protected final PlanState planState = new PlanState();
    /**
     * The state after the deliveries have been loaded.
     */
    protected final DeliveriesState deliveriesState = new DeliveriesState();
    /**
     * The state after the rounds have been computed.
     */
    protected final ComputeState computeState = new ComputeState();
    /**
     * The state after the user has clicked on the delete button.
     */
    protected final DeleteState deleteState = new DeleteState();
    /**
     * The state after the user has clicked of the add button.
     */
    protected final AddState addState = new AddState();
    /**
     * The state after the user has clicked on a specific delivery point of a
     * specific round.
     */
    protected final DetailState detailState = new DetailState();

    /**
     * Constructor
     */
    public Controller() {
        pathFinder = new PathFinder();
        parser = new Parser();
        cmdList = new CmdList();
        curState = initState;
        mainWindow = new MainWindow(this);
    }

    /**
     * Sets the current state to the given state.
     *
     * @param state
     */
    protected void setCurState(State state) {
        curState = state;
    }

    /**
     * Loads a plan and displays it in the graphical view.
     */
    public void loadPlan() {
        curState.loadPlan(this, mainWindow);
    }

    /**
     * Loads deliveries and displays them in the graphical view.
     */
    public void loadDeliveries() {
        curState.loadDeliveries(this, mainWindow);
    }

    /**
     * Computes the rounds and displays them graphically and textually.
     */
    public void compute() {
        curState.compute(this, mainWindow);
    }

    /**
     * Highlights one specific round according to the delivery point chosen by the
     * user.
     */
    public void mouseClick(int x, int y) {
        if (curState.equals(computeState) || curState.equals(detailState)) {
            curState.mouseClick(this, mainWindow, x, y);
        } else {
            curState.mouseClick(this, mainWindow, cmdList, x, y);
        }
    }

    /**
     * Allows the user to add a delivery point after having clicked on the add button.
     */
    public void buttonAddClick() {
        if (curState.equals(computeState) || curState.equals(detailState)) {
            Integer duration = mainWindow.showInformationAddState("You can click on the locate of the delivery point"
                    + " you want to add. \nPlease, Enter the duration of the new delivery point.");
            if (duration != null) {
                if (duration >= 0) {
                    addState.setDuration(duration);
                    curState.clickAddButton(this);
                } else {
                    mainWindow.showError("The duration entered is not valid.");
                }
            }

        }
    }

    /**
     * Allows the user to delete a delivery point after having clicked on the delete button.
     */
    public void buttonDeleteClick() {
        if (curState.equals(computeState) || curState.equals(detailState)) {
            int wantToDelete = mainWindow
                    .showInformationDeleteState("You can click on one delivery point" + " in order to delete it.");
            if (wantToDelete == 0) {
                curState.clickDeleteButton(this);
            }
        }
    }

    /**
     * Allows the user to move dynamically through a specific round.
     */
    public void keyPressed(int keyCode) {
        curState.keyPressed(this, mainWindow, keyCode);
    }

    /**
     * Returns the xml file parser.
     */
    public Parser getParser() {
        return parser;
    }

    /**
     * Returns the pathfinder.
     */
    public PathFinder getPathFinder() {
        return pathFinder;
    }

    /**
     * Calls by the main window when the user clicks on button "Undo"
     */
    public void undo() {
        curState.undo(cmdList);
    }

    /**
     * Calls by the main window when the user clicks on button "Redo"
     */
    public void redo() {
        curState.redo(cmdList);
    }

    /**
     * Returns the mainWindow.
     *
     * @return mainWindow
     */
    public MainWindow getMainWindow() {
        return mainWindow;
    }

    /**
     * Returns the deleteState.
     *
     * @return deleteState
     */
    public DeleteState getDeleteState() {
        return deleteState;
    }

    /**
     * Returns the add state.
     *
     * @return addState
     */
    public AddState getAddState() {
        return addState;
    }

    /**
     * Resets the application.
     */
    public void reset() {
        curState.reset(this, mainWindow);
    }

    /**
     * Returns the command list.
     *
     * @return cmdList
     */
    public CmdList getCmdList() {
        return cmdList;
    }

}
