package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.JFrame;
import controler.Controler;
import java.awt.GridLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

/**
 * This class represents our window It contains the graphical,textual and input
 * view e
 *
 * @author PLD-HEXA-301
 */
public class MainWindow extends JFrame {
    //Components

    private GraphicalView graphicalView;
    private TextualView textualView;
    private InputView inputView;
    private JPanel container;
    //Listeners
    private ButtonListener buttonListener;
    private MouseMovementListener mouseMouvementListener;
    private KeyBoardListener keyBoardListener;
    private JScrollPane jScroolPane;
    //Display charasteristics

    public MainWindow(Controler controler) {
        super();
        //Components initialisation
        graphicalView = new GraphicalView(this);
        textualView = new TextualView(this);
        inputView = new InputView(this);
        jScroolPane = new JScrollPane(graphicalView);
        graphicalView.setScrollPane(jScroolPane);

        jScroolPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScroolPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        //Buttons
        createButtons(controler);
        //Listeners
        mouseMouvementListener = new MouseMovementListener(controler, graphicalView, this);
        this.getGraphicalView().addMouseListener(mouseMouvementListener);
        keyBoardListener = new KeyBoardListener(controler, graphicalView, this);
        addKeyListener(keyBoardListener);
        //Display characteristics
        setFocusable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setWindowSize();
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);
        setVisible(true);
        Dimension size = this.getSize();
        setTitle("Best Delivery Rounds");
        Insets insets = this.getInsets();
        System.out.println(insets);
        if (insets != null) {
            size.height -= insets.top + insets.bottom;
            size.width -= insets.left + insets.right;
        }
        graphicalView.setPreferredSize(new Dimension(size.height, size.height));
        graphicalView.setMapSize(size.height - 30);
        graphicalView.setLocation(0, 0);
        container = new JPanel();
        container.setLayout(new BorderLayout());
        container.setLocation(0, size.height);
        container.setPreferredSize(new Dimension(size.width - size.height, size.height));
        container.add(inputView, BorderLayout.NORTH);
        container.add(textualView, BorderLayout.CENTER);
        this.getContentPane().add(container, BorderLayout.EAST);
        this.getContentPane().add(jScroolPane, BorderLayout.CENTER);

    }

    public void displayMessage(String s) {
        System.out.println(s);
    }

    private void createButtons(Controler controler) {
        buttonListener = new ButtonListener(controler);
        inputView.createButtons(controler, buttonListener);

    }

    public void setWindowSize() {
//		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//		setSize((int)screenSize.getHeight()-50+inputView.getWidth(),(int)screenSize.getHeight()-50);
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension d = tk.getScreenSize();
        Insets insets = tk.getScreenInsets(getGraphicsConfiguration());
        int width = (int) (d.getWidth() - insets.left - insets.right);
        int height = (int) (d.getHeight() - insets.top - insets.bottom);
        Dimension dimension = new Dimension(width, height);
        setSize(dimension);
    }

    /**
     * Shows a dialog to indicate the user that there is an error during a
     * proces. It can be the input xml file which is invalid, its content or an
     * error when calculating routes. is invalid.
     *
     * @param text The text to show in the pop up
     */
    public void showError(String text) {
        JOptionPane.showConfirmDialog(null, text, "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Shows a dialog to inform the user that there are delivery men who will
     * have nothing to do. He is asked to answer if he wants to pursue.
     *
     * @param text
     *
     * @return an int : 0 if Yes, 1 if No
     */
    public int showInformationDelivery(String text) {
        return JOptionPane.showConfirmDialog(null, text, "Warning", JOptionPane.YES_NO_OPTION);
    }

    public int showInformationDeleteState(String text) {
        return JOptionPane.showConfirmDialog(null, text, "Delete delivery point information", JOptionPane.OK_CANCEL_OPTION);
    }

    /**
     * It allows to get the graphical view
     *
     * @return The graphical view
     */
    public GraphicalView getGraphicalView() {
        return graphicalView;
    }

    /**
     * It allows to get the textual view
     *
     * @return The textual view
     */
    public TextualView getTextualView() {
        return textualView;
    }

    /**
     * It allows to get the input view
     *
     * @return The input view
     */
    public InputView getInputView() {
        return inputView;
    }

    public Integer showInformationAddState(String text) {
        String duration = JOptionPane.showInputDialog(null, text, "Duration", JOptionPane.DEFAULT_OPTION);
        System.out.println("duration : "+duration);
        Integer durationInt = null;
        if(duration != null ) {
        	try {
        		durationInt = -1;
            	durationInt = Integer.valueOf(duration);
            }
            catch(Exception e) {
            	System.out.println(e);
            }
        }
        return durationInt;
         
    }

    
    public void showInformationConfirmationCommand(String text) {
        JOptionPane.showConfirmDialog(null, text, "Confirmation of the action", JOptionPane.DEFAULT_OPTION);
    }
}
