package view;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import controler.Controler;
import javax.swing.JOptionPane;

/**
 * This class represents our window It contains the graphical,textual and input
 * view
 *e
 * @author PLD-HEXA-301
 */
public class MainWindow extends JFrame {	
	//Components
	private GraphicalView graphicalView;
	private TextualView textualView;
	private InputView inputView;
	//Listeners
	private ButtonListener buttonListener;
	private MouseMovementListener mouseMouvementListener;
	
	//Display charasteristics

	public MainWindow(Controler controler) {
		//Components initialisation
		graphicalView = new GraphicalView(this);
		textualView = new TextualView(this);
		inputView = new InputView(this);
		//Buttons
		createButtons(controler);
		//Listeners
		mouseMouvementListener = new MouseMovementListener(controler,graphicalView,this);
		this.getGraphicalView().addMouseListener(mouseMouvementListener);
		//Display characteristics
	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setWindowSize();
		this.setLayout(new BorderLayout());
	    this.getContentPane().add(inputView, BorderLayout.WEST);
	    this.getContentPane().add(textualView, BorderLayout.EAST);
	    this.getContentPane().add(graphicalView, BorderLayout.CENTER);
		setTitle("Best Delivery Rounds");
		setVisible(true);
	}
	
	public void displayMessage(String s) {
		System.out.println(s);
	}
	
	private void createButtons(Controler controler){
		buttonListener = new ButtonListener(controler);
		inputView.createButtons(controler,buttonListener);
		
	}
	
	public void setWindowSize() {
		setExtendedState(JFrame.MAXIMIZED_BOTH );
  }
  
  /**
   * Shows a dialog to indicate the user that there is an error during a proces.
   * It can be the input xml file which is invalid, its content or an error
   * when calculating routes.
   * is invalid.
   * 
   * @param text The text to show in the pop up
   */
  public void showError(String text) {
      JOptionPane.showConfirmDialog(null, text,"Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
  }

  /**
   * Shows a dialog to inform the user that there are delivery men who will have
   * nothing to do. He is asked to answer if he wants to pursue.
   * @param text 
   * 
   * @return an int : 0 if Yes, 1 if No
   */
  public int showInformationDelivery(String text) {
       return JOptionPane.showConfirmDialog(null, text, "Warning",JOptionPane.YES_NO_OPTION);
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
}
