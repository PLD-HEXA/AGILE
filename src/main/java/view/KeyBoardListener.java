package view;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

import controler.Controller;

/**
 * This class represents the keyboard listener
 *
 * @author PLD-HEXA-301
 */
public class KeyBoardListener implements KeyListener {

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
     *
     * @param controller
     * @param graphicalView
     * @param mainWindow
     */
    public KeyBoardListener(Controller controller, GraphicalView graphicalView, MainWindow mainWindow) {
        this.controller = controller;
        this.graphicalView = graphicalView;
        this.mainWindow = mainWindow;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void keyPressed(KeyEvent e) {
        controller.keyPressed(e.getKeyCode());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void keyReleased(KeyEvent arg0) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void keyTyped(KeyEvent arg0) {
    }

}
