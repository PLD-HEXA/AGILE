package controler;

import entities.Parser;
import view.MainWindow;

public class Controler {
	
	private Parser parser;
	private CmdList cmdList;
	private State curState;
	private MainWindow mainWindow;
	protected final InitState initState = new InitState();
	protected final PlanState planState = new PlanState();
	protected final DeliveriesState deliveriesState = new DeliveriesState();
	
	public Controler() {
		parser = new Parser();
		cmdList = new CmdList();
		curState = initState;
		mainWindow = new MainWindow( this);
	}


	protected void setCurState(State state){
		curState = state;
	}


	public void loadPlan() {
		curState.loadPlan(this, mainWindow);
	}
	
	public void loadDeliveries() {
		curState.loadDeliveries(this, mainWindow);
	}
	
	public Parser getParser() {
		return parser;
	}
	

}
