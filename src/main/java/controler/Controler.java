package controler;

import entities.Parser;
import entities.algorithms.PathFinder;
import view.MainWindow;

public class Controler {
	
	PathFinder pathFinder;
	private Parser parser;
	private CmdList cmdList;
	private State curState;
	private MainWindow mainWindow;
	protected final InitState initState = new InitState();
	protected final PlanState planState = new PlanState();
	protected final DeliveriesState deliveriesState = new DeliveriesState();
	protected final ComputeState computeState = new ComputeState();
	
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


	public void loadPlan() {
		curState.loadPlan(this, mainWindow);
	}
	
	public void loadDeliveries() {
		curState.loadDeliveries(this, mainWindow);
	}
	
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
	 * Returns the xml file parser.
	 */
	public Parser getParser() {
		return parser;
	}
	
	public PathFinder getPathFinder() {
		return pathFinder;
	}
	
	

}
