package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
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
	
	private JTree listOfRounds;
	private DefaultTreeCellRenderer renderer;
	
	public TextualView(MainWindow mainWindow) {
		super();
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
		List<Itinerary> itineraries = new ArrayList<Itinerary>();
		Itinerary itinerary=new Itinerary();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date arrival;
		try {
			arrival = format.parse ( "2009-12-31" );
			Date departure = format.parse ( "2009-12-30" );
			DeliveryPoint dp=new DeliveryPoint(new Coordinate(10.0,20.20),arrival,departure);
			List<DeliveryPoint> generalPath= new ArrayList<DeliveryPoint>();
			generalPath.add(dp);
			itinerary.setGeneralPath(generalPath);
			itineraries.add(itinerary);
			displayListOfRounds(itineraries);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		
//        DefaultMutableTreeNode rounds = new DefaultMutableTreeNode("Rounds");
//        DefaultMutableTreeNode round1 = new DefaultMutableTreeNode("1st delivery round");
//        DefaultMutableTreeNode stop11 = new DefaultMutableTreeNode("1st stop");
//        DefaultMutableTreeNode departure1 = new DefaultMutableTreeNode("10h15");
//        DefaultMutableTreeNode arrival1 = new DefaultMutableTreeNode("10h30");
//        DefaultMutableTreeNode duration1 = new DefaultMutableTreeNode("15min");
//        rounds.add(round1);
//        round1.add(stop11);
//        stop11.add(departure1);
//        stop11.add(arrival1);
//        stop11.add(duration1);
//        DefaultMutableTreeNode round2 = new DefaultMutableTreeNode("2nd delivery round");
//        DefaultMutableTreeNode stop21 = new DefaultMutableTreeNode("1st stop");
//        DefaultMutableTreeNode departure2 = new DefaultMutableTreeNode("10h25");
//        DefaultMutableTreeNode arrival2 = new DefaultMutableTreeNode("10h30");
//        DefaultMutableTreeNode duration2 = new DefaultMutableTreeNode("5min");
//        rounds.add(round2);
//        round2.add(stop21);
//        stop21.add(departure2);
//        stop21.add(arrival2);
//        stop21.add(duration2);
//		
		
	}
	
	public void displayListOfRounds(List<Itinerary> itinerary) {
		int numberOfRounds=itinerary.size();
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
			int numberOfStops=itinerary.get(i).getGeneralPath().size();
			curRound=new DefaultMutableTreeNode("Round n°"+ Integer.toString(i+1)); 
			for(int j=0;j<numberOfStops;j++) {
				departureDate = itinerary.get(i).getGeneralPath().get(j).getDepartureTime();
				arrivalDate = itinerary.get(i).getGeneralPath().get(j).getArrivalTime();
				curStop=new DefaultMutableTreeNode("Delivery n°"+ Integer.toString(i+1));
				arrival=new DefaultMutableTreeNode("Departure  "+departureDate.toString().substring(11, 19));
				departure=new DefaultMutableTreeNode("Arrival        "+arrivalDate.toString().substring(11, 19));
				diff=new Date(departureDate.getTime() - arrivalDate.getTime());
				duration=new DefaultMutableTreeNode("Duration     "+diff.toString().substring(11, 19));
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
	}

}
