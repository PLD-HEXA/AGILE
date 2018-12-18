package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Toolkit;
import javax.swing.JFrame;
import controler.Controller;
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
     * The JPanel that contains the input and textual view
     */
    private JPanel container;

    /**
     * The button listener
     */
    private ButtonListener buttonListener;

    /**
     * The mouse listener
     */
    private MouseMovementListener mouseMovementListener;

    /**
     * The keyboard listener
     */
    private KeyBoardListener keyBoardListener;

    /**
     * The JScrollPane that contains the graphical view
     */
    private JScrollPane jScrollPane;

    /**
     * The constructor.It creates the window with all components ( graphical ,
     * textual and input)
     *
     * @param controller
     */
    public MainWindow(Controller controller) {
        super();
        graphicalView = new GraphicalView(this);
        textualView = new TextualView(this);
        inputView = new InputView();
        jScrollPane = new JScrollPane(graphicalView);
        graphicalView.setScrollPane(jScrollPane);
        jScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        createButtons(controller);
        mouseMovementListener = new MouseMovementListener(controller);
        this.getGraphicalView().addMouseListener(mouseMovementListener);
        keyBoardListener = new KeyBoardListener(controller, graphicalView, this);
        addKeyListener(keyBoardListener);
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
        graphicalView.setPreferredSize(new Dimension(size.width, size.height));
        graphicalView.setMapSize(size.height - 30);
        graphicalView.setLocation(0, 0);
        container = new JPanel();
        container.setLayout(new BorderLayout());
        container.setLocation(0, size.height);
        container.setPreferredSize(new Dimension(size.width - size.height, size.height));
        container.add(inputView, BorderLayout.NORTH);
        container.add(textualView, BorderLayout.CENTER);
        this.getContentPane().add(container, BorderLayout.EAST);
        this.getContentPane().add(jScrollPane, BorderLayout.CENTER);
    }

    /**
     * It allows to display a message
     *
     * @param s The string to show
     */
    public void displayMessage(String s) {
        System.out.println(s);
    }

    /**
     * It allows to create all the buttons
     *
     * @param controller The controller
     */
    private void createButtons(Controller controller) {
        buttonListener = new ButtonListener(controller);
        inputView.createButtons(controller, buttonListener);

    }

    /**
     * It allows to set the size of the window
     */
    private void setWindowSize() {
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
     * process. It can be the input xml file which is invalid, its content or an
     * error when calculating routes. is invalid.
     *
     * @param text The text to show in the pop up
     */
    public void showError(String text) {
        JOptionPane.showConfirmDialog(null, text, "Error",
                JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
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

    /**
     * Shows a dialog to inform the user that the point will be deleted
     * @param text
     * @return an int : 0 if Ok, 1 if Cancel
     */
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



    /**
     * Shows a dialog to inform the user that he can put the duration
     * @param text
     * @return an int: the duration
     */
    public Integer showInformationAddState(String text) {
        String duration = JOptionPane.showInputDialog(null, text, "Duration", JOptionPane.PLAIN_MESSAGE);
        System.out.println("duration : " + duration);
        Integer durationInt = null;
        if (duration != null) {
            try {
                durationInt = -1;
                durationInt = Integer.valueOf(duration);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return durationInt;
    }

    /**
     * Shows a dialog to inform the user that the command has been taken into account
     * @param text
     */
    public void showInformationConfirmationCommand(String text) {
        JOptionPane.showConfirmDialog(null, text, "Confirmation of the action", JOptionPane.DEFAULT_OPTION);
    }
}
