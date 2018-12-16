package view;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

import controler.Controler;

/**
 * This class represents the keyboard listener
 *
 * @author PLD-HEXA-301
 */
public class KeyBoardListener implements KeyListener {

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
     *
     * @param controler
     * @param graphicalView
     * @param mainWindow
     */
    public KeyBoardListener(Controler controler, GraphicalView graphicalView, MainWindow mainWindow) {
        this.controler = controler;
        this.graphicalView = graphicalView;
        this.mainWindow = mainWindow;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void keyPressed(KeyEvent e) {
        controler.keyPressed(e.getKeyCode());
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
