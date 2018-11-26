package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import controler.Controler;

public class ButtonListener implements ActionListener {

	private Controler controler;

	public ButtonListener(Controler controler){
		this.controler = controler;
	}

	public void actionPerformed(ActionEvent e) { 
		switch (e.getActionCommand()){
		case InputView.LOAD_PLAN: controler.loadPlan(); break;
		case InputView.LOAD_DELIVERIES: controler.loadDeliveries(); break;
		}
	}
	
}
