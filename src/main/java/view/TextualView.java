package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import entities.Coordinate;
import entities.DeliveryPoint;
import entities.Itinerary;

public class TextualView extends JPanel {
	
	private List<Itinerary> itineraries;
	private JTree listOfRounds;
	private DefaultTreeCellRenderer renderer;
	private MainWindow mainWindow;
	
	
	public TextualView(MainWindow mainWindow) {
		super();
		this.mainWindow=mainWindow;
        setLayout(new BorderLayout());
        setBackground(Color.pink);
        JLabel label=new JLabel("         Delivery rounds :       ");
        Font f = new Font("TimesRoman",Font.BOLD,25);
        label.setFont(f);
        this.add(label, BorderLayout.NORTH);
        ImageIcon timer = new ImageIcon("C:/Users/asus/Downloads/timer.png");
        ImageIcon blackBicycle = new ImageIcon("C:/Users/asus/Downloads/bicycle.jpg");
        ImageIcon redBicycle = new ImageIcon("C:/Users/asus/Downloads/redbicycle.png");
		renderer = new DefaultTreeCellRenderer();
		renderer.setLeafIcon(timer);
		renderer.setClosedIcon(blackBicycle);
		renderer.setOpenIcon(redBicycle);		
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (itineraries != null) {
			displayListOfRounds(itineraries);
		}
	}
	
	public void displayListOfRounds(List<Itinerary> itineraries) {
		this.itineraries=itineraries;
		int numberOfRounds=itineraries.size();
		DefaultMutableTreeNode rounds = new DefaultMutableTreeNode("Rounds");
		DefaultMutableTreeNode curRound;
		DefaultMutableTreeNode curStop;
		DefaultMutableTreeNode arrival;
		DefaultMutableTreeNode departure;
		DefaultMutableTreeNode duration;
		Date departureDate;
		Date arrivalDate;
		Date diff;
		for(int i=0;i<numberOfRounds;i++) {
			int numberOfStops=itineraries.get(i).getGeneralPath().size();
			curRound=new DefaultMutableTreeNode("Round n°"+ Integer.toString(i+1)); 
			for(int j=0;j<numberOfStops;j++) {
				departureDate = itineraries.get(i).getGeneralPath().get(j).getDepartureTime();
				arrivalDate = itineraries.get(i).getGeneralPath().get(j).getArrivalTime();
				curStop=new DefaultMutableTreeNode("Delivery n°"+ Integer.toString(j+1));
				arrival=new DefaultMutableTreeNode("Departure  "+departureDate.toString().substring(11, 19));
				departure=new DefaultMutableTreeNode("Arrival        "+arrivalDate.toString().substring(11, 19));
				diff=new Date(departureDate.getTime() - arrivalDate.getTime());
				duration=new DefaultMutableTreeNode("Duration     "+diff.toString().substring(14, 19).replace(':','m')+"s");
				curStop.add(departure);
				curStop.add(arrival);
				curStop.add(duration);
				curRound.add(curStop);
				rounds.add(curRound);
				
			}
		}
		listOfRounds=new JTree(rounds);
		final Font currentFont = listOfRounds.getFont();
		final Font bigFont = new Font(currentFont.getName(), currentFont.getStyle(), currentFont.getSize() + 10);
		listOfRounds.setFont(bigFont);
		listOfRounds.setCellRenderer(renderer);
		this.add(listOfRounds, BorderLayout.CENTER);
		System.out.println(itineraries);
	}

}
