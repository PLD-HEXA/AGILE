package view;

import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import controler.Controller;
import entities.Map;
import java.awt.GridLayout;
import javax.swing.JSpinner.DefaultEditor;

/**
 * this class represents the input view of our project it allows to create all
 * the buttons
 *
 * @author PLD-HEXA-301
 */
public class InputView extends JPanel {

    /**
     * The map
     */
    Map map;

    /**
     * It represents the name of the button that allows to load a plan
     */
    protected final static String LOAD_PLAN = "Load an xml plan";

    /**
     * It represents the name of the button that allows to load deliveries
     */
    protected final static String LOAD_DELIVERIES = "Load deliveries";

    /**
     * It represents the name of the button that allows to compute
     */
    protected final static String COMPUTE = "Compute";

    /**
     * It represents the name of the button that allows to delete a delivery point
     */
    protected final static String DELETE = "Delete";

    /**
     * It represents the name of the button that allows to add a delivery point
     */
    protected final static String ADD = "Add";

    /**
     * It represents the name of the button that allows to undoes the last command
     */
    protected final static String UNDO = "Undo";

    /**
     * It represents the name of the button that allows to redoes the last command
     */
    protected final static String REDO = "Redo";
    
    /**
     * It represents the name of the button that allows to reset the application
     */
    protected final static String RESET = "Reset";

    /**
     * It represents the number of rounds
     */
    private JSpinner numOfRounds;
    /**
     * It represents the list of all buttons
     */
    private ArrayList<JButton> buttons;
    /**
     * It represents a board that contains the name of all buttons
     */
  
    private final String[] buttonNames = new String[]{LOAD_PLAN, LOAD_DELIVERIES, COMPUTE, DELETE, ADD, UNDO, REDO,RESET};

    /**
     * The constructor
     *
     * @param mainWindow It represents our window
     */
    public InputView(MainWindow mainWindow) {
        super();
        buttons = new ArrayList<JButton>();
    }

    /**
     * This method allows to create the buttons
     *
     * @param controller The controller
     * @param buttonListener The buttonListener
     */
    public void createButtons(Controller controller, ButtonListener buttonListener) {
        this.setLayout(new GridLayout(2,4));
        for (String buttonName : buttonNames) {
            if (buttonName.equals("Compute")) {
                JLabel label = new JLabel("Nb of delivery men :");
                add(label);
                SpinnerModel spinner = new SpinnerNumberModel(1, 1, 100, 1);
                numOfRounds = new JSpinner(spinner);
                ((DefaultEditor) numOfRounds.getEditor()).getTextField().setEditable(false);
                numOfRounds.setName("Number of delivery men");
                add(numOfRounds);
            }
            JButton bouton = new JButton(buttonName);
            bouton.setName(buttonName);
            buttons.add(bouton);
            bouton.setSize(120, 80);
            bouton.setLocation(0, (buttons.size() - 1) * 80);
            bouton.setFocusable(false);
            bouton.setFocusPainted(false);
            bouton.addActionListener(buttonListener);
            this.add(bouton);
        }
    }

    /**
     * This method allows to get the number of rounds
     *
     * @return the number of rounds
     */
    public JSpinner getNumOfRounds() {
        return numOfRounds;
    }

}
