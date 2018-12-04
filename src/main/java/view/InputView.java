package view;

import java.io.File;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import controler.Controler;
import entities.Map;

public class InputView extends JPanel {

    Map map;

    // States
    protected final static String LOAD_PLAN = "Load an xml plan";
    protected final static String LOAD_DELIVERIES = "Load deliveries";
    protected final static String COMPUTE = "Compute";
    private JSpinner numOfRounds;
    private ArrayList<JButton> buttons;
    private final String[] buttonNames = new String[]{LOAD_PLAN, LOAD_DELIVERIES, COMPUTE};

    public InputView(MainWindow mainWindow) {
        super();
        buttons = new ArrayList<JButton>();
    }

    public void createButtons(Controler controler, ButtonListener buttonListener) {
        for (String buttonName : buttonNames) {
            if (buttonName.equals("Compute")) {
                JLabel label = new JLabel("Number of delivery men :");
                add(label);
                SpinnerModel spinner = new SpinnerNumberModel(1, 1, 100, 1);
                numOfRounds = new JSpinner(spinner);
                add(numOfRounds);
            }

            JButton bouton = new JButton(buttonName);
            buttons.add(bouton);
            bouton.setSize(120, 80);
            bouton.setLocation(0, (buttons.size() - 1) * 80);
            bouton.setFocusable(false);
            bouton.setFocusPainted(false);
            bouton.addActionListener(buttonListener);
            add(bouton);
        }
    }

    public JSpinner getNumOfRounds() {
        return numOfRounds;
    }

}
