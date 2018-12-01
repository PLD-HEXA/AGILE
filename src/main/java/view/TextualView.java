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
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.ScrollPaneConstants;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import entities.Coordinate;
import entities.DeliveryPoint;
import entities.Itinerary;

/**
 * This class represents the textual view of our project
 * It allows to display the rounds
 * @author User
 */
public class TextualView extends JPanel {
     /**
         * The itineraries
    */
    private List<Itinerary> itineraries;
     /**
         * The list of rounds
    */
    private JTree listOfRounds;
     /**
         * The renderer
    */
    private DefaultTreeCellRenderer renderer;
     /**
         * The main Window
    */
    private MainWindow mainWindow;
     /**
         * It allows to draw graphics  
    */
    Graphics g;
     /**
         * It will contains the list of rounds 
    */
    JPanel panel;

    /**
     * The constructor
     * @param mainWindow
     *                  It represents our window
     */
    public TextualView(MainWindow mainWindow) {
        super();
        this.mainWindow = mainWindow;
        setLayout(new BorderLayout());
        setBackground(Color.pink);
        JLabel label = new JLabel("         Delivery rounds :       ");
        Font f = new Font("TimesRoman", Font.BOLD, 25);
        label.setFont(f);
        this.add(label, BorderLayout.NORTH);
        ImageIcon timer = new ImageIcon("images/timer.png");
        ImageIcon blackBicycle = new ImageIcon("images/bicycle.jpg");
        System.out.println("Working Directory = "
                + System.getProperty("user.dir"));
        ImageIcon redBicycle = new ImageIcon("images/redbicycle.png");
        renderer = new DefaultTreeCellRenderer();
        renderer.setLeafIcon(timer);
        renderer.setClosedIcon(blackBicycle);
        renderer.setOpenIcon(redBicycle);
        panel = new JPanel();
        this.add(panel, BorderLayout.CENTER);
        panel.setLayout(new BorderLayout());
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (itineraries != null) {
            displayListOfRounds();
        }

        this.g = g;
    }

    /**
     * This method allows to show the list of rounds     */
    public void displayListOfRounds() {
        int numberOfRounds = itineraries.size();
        DefaultMutableTreeNode rounds = new DefaultMutableTreeNode("Rounds");
        DefaultMutableTreeNode curRound;
        DefaultMutableTreeNode curStop;
        DefaultMutableTreeNode arrival;
        DefaultMutableTreeNode departure;
        DefaultMutableTreeNode duration;
        Date departureDate;
        Date arrivalDate;
        Date diff;
        for (int i = 0; i < numberOfRounds; i++) {
            int numberOfStops = itineraries.get(i).getGeneralPath().size();
            curRound = new DefaultMutableTreeNode("Round n°" + Integer.toString(i + 1));
            for (int j = 1; j < (numberOfStops - 1); j++) {

                departureDate = itineraries.get(i).getGeneralPath().get(j).getDepartureTime();
                arrivalDate = itineraries.get(i).getGeneralPath().get(j).getArrivalTime();
                curStop = new DefaultMutableTreeNode("Delivery n°" + Integer.toString(j));
                arrival = new DefaultMutableTreeNode("Departure  " + departureDate.toString().substring(11, 19));
                departure = new DefaultMutableTreeNode("Arrival        " + arrivalDate.toString().substring(11, 19));
                diff = new Date(departureDate.getTime() - arrivalDate.getTime());
                duration = new DefaultMutableTreeNode("Duration     " + diff.toString().substring(14, 19).replace(':', 'm') + "s");
                curStop.add(departure);
                curStop.add(arrival);
                curStop.add(duration);
                curRound.add(curStop);
                rounds.add(curRound);

            }
        }
        listOfRounds = new JTree(rounds);
        final Font currentFont = listOfRounds.getFont();
        final Font bigFont = new Font(currentFont.getName(), currentFont.getStyle(), currentFont.getSize() + 10);
        listOfRounds.setFont(bigFont);
        listOfRounds.setCellRenderer(renderer);
        JScrollPane conteneur = new JScrollPane(listOfRounds, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        panel.removeAll();
        panel.add(conteneur, BorderLayout.CENTER);
        panel.revalidate();
        panel.repaint();

    }

    /**
     * This method allows to edit the itineraries
     * @param itineraries   
     *                   The new itineraries
     */
    public void setItineraries(List<Itinerary> itineraries) {
        this.itineraries = itineraries;
    }

}
