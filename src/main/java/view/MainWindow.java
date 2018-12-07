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
   * Shows a dialog to indicate the user that the input xml file is invalid
   * @param text 
   */
  public void showErrorXmlCityPlan(String text) {
      JOptionPane.showConfirmDialog(null, text,"Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
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
