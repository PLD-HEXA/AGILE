package view;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import controler.Controler;

/**
 * This class represents the mouse movement listener
 *
 * @author PLD-HEXA-301
 */
public class MouseMovementListener implements MouseListener {

    /**
     * The controler
     */
    private Controler controler;
    /**
     * The graphical view
     */
    private GraphicalView graphicalView;
    /**
     * The main window
     */
    private MainWindow mainWindow;

    /**
     * The constructor
     * @param controler
     * @param graphicalView
     * @param mainWindow
     */
    public MouseMovementListener(Controler controler, GraphicalView graphicalView, MainWindow mainWindow) {
        this.controler = controler;
        this.graphicalView = graphicalView;
        this.mainWindow = mainWindow;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        controler.mouseClick(e.getX(), e.getY());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mouseEntered(MouseEvent e) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mouseExited(MouseEvent e) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mousePressed(MouseEvent e) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mouseReleased(MouseEvent e) {
    }

}
