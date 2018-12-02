package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import controler.Controler;

/**
 * This class represents our window
 * It contains the graphical,textual and input view
 * @author User
 */
public class MainWindow extends JFrame {

    //Components
    /**
         * The graphical view
    */
    private GraphicalView graphicalView;
    /**
         * The textual view
    */
    private TextualView textualView;
    /**
         * The input view
    */
    private InputView inputView;
    /**
         * The button listener
    */
    //Listeners
    private ButtonListener buttonListener;

    //Display charasteristics

    /**
     * The constructor
     * It creates the window with all components ( graphical , textual and input)
     * @param controler
     */
    public MainWindow(Controler controler) {
        //Components initialisation
        graphicalView = new GraphicalView(this);
        textualView = new TextualView(this);
        inputView = new InputView(this);
        //Buttons
        createButtons(controler);
        //Display characteristics

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setWindowSize();
        this.setLayout(new BorderLayout());
        this.getContentPane().add(inputView, BorderLayout.WEST);
        this.getContentPane().add(textualView, BorderLayout.EAST);
        this.getContentPane().add(graphicalView, BorderLayout.CENTER);
//		this.add(inputView);
//		this.add(textualView);
//		this.add(graphicalView);
        setTitle("Best Delivery Rounds");
        setVisible(true);
    }

    /**
     * It allows to display a message
     * @param s
     *          The string to show
     */
    public void displayMessage(String s) {
        System.out.println(s);
    }
     /**
     * It allows to create all the buttons
     * @param s
     *          The string to show
     */
    private void createButtons(Controler controler) {
        buttonListener = new ButtonListener(controler);
        inputView.createButtons(controler, buttonListener);

    }

    /**
     * It allows to set the size of the window
     */
    public void setWindowSize() {
        setExtendedState(JFrame.MAXIMIZED_BOTH);

    }

    /**
     * It allows to get the graphical view
     * @return 
     *         The graphical view
     */
    public GraphicalView getGraphicalView() {
        return graphicalView;
    }

    /**
     * It allows to get the textual view
     * @return
     *          The textual view
     */
    public TextualView getTextualView() {
        return textualView;
    }

    /**
     * It allows to get the input view
     * @return
     *          The input view
     */
    public InputView getInputView() {
        return inputView;
    }

}
