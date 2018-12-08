package controler;

import entities.Parser;
import entities.algorithms.PathFinder;
import view.MainWindow;

/**
 * @author PLD-HEXA-301
 *
 */
public class Controler {
	
	PathFinder pathFinder;
	private Parser parser;
	private CmdList cmdList;
	private MainWindow mainWindow;
	private State curState;
	protected final InitState initState = new InitState();
	protected final PlanState planState = new PlanState();
	protected final DeliveriesState deliveriesState = new DeliveriesState();
	protected final ComputeState computeState = new ComputeState();
	protected final DetailState detailState = new DetailState();
	
	/**
	 * Constructor
	 */
	public Controler() {
		pathFinder= new PathFinder();
		parser = new Parser();
		cmdList = new CmdList();
		curState = initState;
		mainWindow = new MainWindow( this);
	}

	
	
	/**
	 * Sets the current state to the given state.
	 * @param state
	 */
	protected void setCurState(State state){
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
		curState.compute(this,mainWindow);
	}
	
	/**
	 * Highlights one specific round according to the delivery point chosen by the user.
	 */
	public void mouseClick(int x,int y) {
		curState.mouseClick(this,mainWindow,x,y);
	}
	/**
	 * Allows the user to move dynamically through a specific round.
	 */
	public void keyPressed(int keyCode) {
		curState.keyPressed(this,mainWindow,keyCode);
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
	
	

}
