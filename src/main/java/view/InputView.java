package view;

import java.io.File;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

import controler.Controler;
import entities.Map;

public class InputView extends JPanel {
	
	Map map;

	// States
	protected final static String LOAD_PLAN = "Load a plan";
	protected final static String LOAD_DELIVERIES = "Load deliveries";
	private ArrayList<JButton> buttons;
	private final String[] buttonNames = new String[]{LOAD_PLAN,LOAD_DELIVERIES};


	public InputView() {
		buttons=new ArrayList<JButton>();
	}
	
	public void createButtons(Controler controler,ButtonListener buttonListener) {
		for (String buttonName : buttonNames){
			JButton bouton = new JButton(buttonName);
			buttons.add(bouton);
			bouton.setSize(80,80);
			bouton.setLocation(0,(buttons.size()-1)*80);
			bouton.setFocusable(false);
			bouton.setFocusPainted(false);
			bouton.addActionListener(buttonListener);
			add(bouton);	
		}
		
	}

}
