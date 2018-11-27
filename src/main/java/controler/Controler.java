package controler;

import view.MainWindow;

public class Controler {
	
	private CmdList cmdList;
	private State curState;
	private MainWindow mainWindow;
	protected final InitState initState = new InitState();
	protected final PlanState planState = new PlanState();
	protected final DeliveriesState deliveriesState = new DeliveriesState();
	
	public Controler() {
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

	

}
