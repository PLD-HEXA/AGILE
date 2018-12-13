package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import controler.Controler;

/**
 * This class allows to call the controller whenever the user presses a button
 *
 * @author PLD-HEXA-301
 */
public class ButtonListener implements ActionListener {

    /**
     * The controller
     */
    private Controler controler;

    /**
     * The constructor
     *
     * @param controler the controller
     */
    public ButtonListener(Controler controler) {
        this.controler = controler;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case InputView.LOAD_PLAN:
                controler.loadPlan();
                break;
            case InputView.LOAD_DELIVERIES:
                controler.loadDeliveries();
                break;
            case InputView.COMPUTE:
                controler.compute();
                break;
            case InputView.DELETE:
                controler.buttonDeleteClick();
                break;
            case InputView.ADD:
                controler.buttonAddClick();
                break;
            case InputView.UNDO:
                System.out.println("Clique sur undo");
                controler.undo();
                break;
            case InputView.REDO:
                System.out.println("Clique sur redo");
                controler.redo();
                break;
            case InputView.RESET:
                System.out.println("Clique sur reset");
                controler.reset();
                break;
            
        }
    }

}
