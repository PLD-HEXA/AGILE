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
     * The constructor
     *
     * @param controller
     */
    public MouseMovementListener(Controller controller) {
        this.controller = controller;

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
