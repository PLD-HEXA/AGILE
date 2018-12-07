package view;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import controler.Controler;

public class MouseMovementListener implements MouseListener{
	
	private Controler controler;
	private GraphicalView graphicalView;
	private MainWindow mainWindow;

	public MouseMovementListener(Controler controler, GraphicalView graphicalView, MainWindow mainWindow){
		this.controler = controler;
		this.graphicalView = graphicalView;
		this.mainWindow = mainWindow;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		controler.mouseClick(e.getX(),e.getY());
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {		
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {		
	}

	@Override
	public void mouseReleased(MouseEvent e) {		
	}

}
