package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;

import controler.Controler;

public class MainWindow extends JFrame {
	
	
	//Components
	private GraphicalView graphicalView;
	private TextualView textualView;
	private InputView inputView;
	//Listeners
	private ButtonListener buttonListener;
	
	
	//Display charasteristics

	public MainWindow(Controler controler) {
		//Components initialisation
		graphicalView = new GraphicalView(this);
		textualView = new TextualView();
		inputView = new InputView();
		//Buttons
		createButtons(controler);
		//Display characteristics
		setLayout(null);
		setExtendedState(JFrame.MAXIMIZED_BOTH );
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.getContentPane().add(inputView, BorderLayout.NORTH);
//		this.getContentPane().add(graphicalView, BorderLayout.CENTER);
		this.getContentPane().add(new JButton("SOUTH"), BorderLayout.SOUTH);
		setTitle("Best Delivery Rounds");
		setVisible(true);
	}
	
	public void displayMessage(String s) {
		System.out.println(s);
	}
	
	private void createButtons(Controler controler){
		buttonListener = new ButtonListener(controler);
		inputView.createButtons(controler,buttonListener);
		
	}

	public GraphicalView getGraphicalView() {
		return graphicalView;
	}

	
	
	
	
	

	
}
