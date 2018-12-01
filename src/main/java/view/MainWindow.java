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
 * 
 * @author User
 */
public class MainWindow extends JFrame {

    //Components
    private GraphicalView graphicalView;
    private TextualView textualView;
    private InputView inputView;
    //Listeners
    private ButtonListener buttonListener;

    //Display charasteristics

    /**
     *
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
     *
     * @param s
     */
    public void displayMessage(String s) {
        System.out.println(s);
    }

    private void createButtons(Controler controler) {
        buttonListener = new ButtonListener(controler);
        inputView.createButtons(controler, buttonListener);

    }

    /**
     *
     */
    public void setWindowSize() {
        setExtendedState(JFrame.MAXIMIZED_BOTH);

    }

    /**
     *
     * @return
     */
    public GraphicalView getGraphicalView() {
        return graphicalView;
    }

    /**
     *
     * @return
     */
    public TextualView getTextualView() {
        return textualView;
    }

    /**
     *
     * @return
     */
    public InputView getInputView() {
        return inputView;
    }

}
