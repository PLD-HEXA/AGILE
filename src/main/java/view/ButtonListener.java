package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import controler.Controler;

/**
 * This class allows to call the controller whenever the user presses a button
 * @author User
 */
public class ButtonListener implements ActionListener {

    /**
         * The controller
    */
    private Controler controler;

    /**
     * The constructor
     * @param controler
     *          the controller
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
        }
    }

}
