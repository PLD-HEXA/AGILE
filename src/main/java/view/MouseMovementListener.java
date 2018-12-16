package view;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import controler.Controller;

/**
 * This class represents the mouse movement listener
 *
 * @author PLD-HEXA-301
 */
public class MouseMovementListener implements MouseListener {

    /**
     * The controller
     */
    private Controller controller;
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
     * @param controller
     * @param graphicalView
     * @param mainWindow
     */
    public MouseMovementListener(Controller controller, GraphicalView graphicalView, MainWindow mainWindow) {
        this.controller = controller;
        this.graphicalView = graphicalView;
        this.mainWindow = mainWindow;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        controller.mouseClick(e.getX(), e.getY());
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
