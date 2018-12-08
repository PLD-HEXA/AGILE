package view;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

import controler.Controler;

public class KeyBoardListener implements KeyListener {
	private Controler controler;
	private GraphicalView graphicalView;
	private MainWindow mainWindow;

	public KeyBoardListener(Controler controler, GraphicalView graphicalView, MainWindow mainWindow){
		this.controler = controler;
		this.graphicalView = graphicalView;
		this.mainWindow = mainWindow;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		controler.keyPressed(e.getKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {		
	}

}
