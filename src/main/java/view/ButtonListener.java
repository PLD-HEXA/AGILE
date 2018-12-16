package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import controler.Controller;

/**
 * This class allows to call the controller whenever the user presses a button
 *
 * @author PLD-HEXA-301
 */
public class ButtonListener implements ActionListener {

    /**
     * The controller
     */
    private Controller controller;

    /**
     * The constructor
     *
     * @param controller the controller
     */
    public ButtonListener(Controller controller) {
        this.controller = controller;
    }
     /**
     * {@inheritDoc}
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case InputView.LOAD_PLAN:
                controller.loadPlan();
                break;
            case InputView.LOAD_DELIVERIES:
                controller.loadDeliveries();
                break;
            case InputView.COMPUTE:
                controller.compute();
                break;
            case InputView.DELETE:
                controller.buttonDeleteClick();
                break;
            case InputView.ADD:
                controller.buttonAddClick();
                break;
            case InputView.UNDO:
                System.out.println("Clique sur undo");
                controller.undo();
                break;
            case InputView.REDO:
                System.out.println("Clique sur redo");
                controller.redo();
                break;
            case InputView.RESET:
                System.out.println("Clique sur reset");
                controller.reset();
                break;
            
        }
    }

}
