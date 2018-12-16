package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import entities.Itinerary;
import javafx.util.Pair;

/**
 * This class represents the textual view of our project It allows to display
 * the rounds
 *
 * @author PLD-HEXA-301
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
     * TODO never used
     * It allows to draw graphics
     */
    private Graphics g;

    /**
     * It will contains the list of rounds
     */
    private JPanel panel;

    /**
     * The Index of the current highlighted itinerary
     */
    private Integer itineraryIndex;

    /**
     * The Index of the current highlihghted delivery point in the itinerary
     */
    private Integer deliveryPointIndex;

    /**
     * The nodes Hierarchy
     */
    private List<List<DefaultMutableTreeNode>> nodesHierarchy;

    /**
     * Contains the index of the delivery to delete in the itinerary
     */
    private List<Pair<Integer, Integer>> indexItineraryToDelete;

    /**
     * The constructor
     *
     * @param mainWindow It represents our window
     */
    public TextualView(MainWindow mainWindow) {
        super();
        this.mainWindow = mainWindow;
        this.indexItineraryToDelete = new ArrayList<>();
        setLayout(new BorderLayout());
        setBackground(Color.pink);
        nodesHierarchy = new ArrayList<>();
        JLabel label = new JLabel("Delivery rounds");
        Font f = new Font("TimesRoman", Font.BOLD, 25);
        label.setFont(f);
        this.add(label, BorderLayout.NORTH);
        ImageIcon timer = new ImageIcon("images/timer.png");
        ImageIcon blackBicycle = new ImageIcon("images/bicycle.jpg");
        ImageIcon redBicycle = new ImageIcon("images/redbicycle.png");
        renderer = new DefaultTreeCellRenderer();
        renderer.setLeafIcon(timer);
        renderer.setClosedIcon(blackBicycle);
        renderer.setOpenIcon(redBicycle);
        panel = new JPanel();
        this.add(panel, BorderLayout.CENTER);
        panel.setLayout(new BorderLayout());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (itineraries != null) {
            displayListOfRounds();
        }
        if (itineraryIndex != null && deliveryPointIndex != null) {
            displaySpecificPath();
        }
        this.g = g;
    }

    /**
     * This method allows to show the list of rounds
     */
    public void displayListOfRounds() {
        DefaultMutableTreeNode rounds = new DefaultMutableTreeNode("Rounds");
        if (itineraries != null) {

            int numberOfRounds = itineraries.size();
            System.out.println("size iti : " + numberOfRounds);
            DefaultMutableTreeNode curRound;
            DefaultMutableTreeNode curStop;
            DefaultMutableTreeNode arrival;
            DefaultMutableTreeNode departure;
            DefaultMutableTreeNode duration;
            Date departureDate;
            Date arrivalDate;
            Date diff;
            for (int i = 0; i < numberOfRounds; i++) {
                boolean itineraryWithDeletedPoint = false;
                List<Integer> indexDelPointToDelete = new ArrayList<>();
                for (Pair<Integer, Integer> p : indexItineraryToDelete) {
                    if (p.getKey() == i) {
                        itineraryWithDeletedPoint = true;
                        indexDelPointToDelete.add(p.getValue());
                    }
                }
                int numberOfStops = itineraries.get(i).getGeneralPath().size();
                curRound = new DefaultMutableTreeNode("Round n " + (i + 1));
                nodesHierarchy.add(i, new ArrayList<>());
                if (!itineraryWithDeletedPoint) {
                    for (int j = 0; j < numberOfStops; j++) {
                        departureDate = itineraries.get(i).getGeneralPath().get(j).getDepartureTime();
                        arrivalDate = itineraries.get(i).getGeneralPath().get(j).getArrivalTime();
                        if (j == 0 || j == (numberOfStops - 1)) {
                            curStop = new DefaultMutableTreeNode("Warehouse - Delivery n " + j);
                        } else {
                            curStop = new DefaultMutableTreeNode("Delivery n " + j);
                        }

                        arrival = new DefaultMutableTreeNode("Departure  " + departureDate.toString().substring(11, 19));
                        departure = new DefaultMutableTreeNode(
                                "Arrival        " + arrivalDate.toString().substring(11, 19));
                        diff = new Date(departureDate.getTime() - arrivalDate.getTime());
                        duration = new DefaultMutableTreeNode(
                                "Duration     " + diff.toString().substring(14, 19).replace(':', 'm') + "s");
                        curStop.add(departure);
                        curStop.add(arrival);
                        curStop.add(duration);
                        curRound.add(curStop);
                        rounds.add(curRound);
                        nodesHierarchy.get(i).add(curStop);
                    }
                } else {
                    for (int j = 0; j < numberOfStops; j++) {
                        departureDate = itineraries.get(i).getGeneralPath().get(j).getDepartureTime();
                        arrivalDate = itineraries.get(i).getGeneralPath().get(j).getArrivalTime();
                        int ind = indexDelPointToDelete.indexOf(j);
                        if (ind != -1) {
                            curStop = new DefaultMutableTreeNode("(DELETED) Delivery n " + j);
                        } else {
                            curStop = new DefaultMutableTreeNode("Delivery n " + j);
                        }
                        arrival = new DefaultMutableTreeNode("Departure  " + departureDate.toString().substring(11, 19));
                        departure = new DefaultMutableTreeNode(
                                "Arrival        " + arrivalDate.toString().substring(11, 19));
                        diff = new Date(departureDate.getTime() - arrivalDate.getTime());
                        duration = new DefaultMutableTreeNode(
                                "Duration     " + diff.toString().substring(14, 19).replace(':', 'm') + "s");
                        curStop.add(departure);
                        curStop.add(arrival);
                        curStop.add(duration);
                        curRound.add(curStop);
                        rounds.add(curRound);
                        nodesHierarchy.get(i).add(curStop);
                    }
                }
            }
            listOfRounds = new JTree(rounds);
            listOfRounds.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
            listOfRounds.addTreeSelectionListener(new TreeSelectionListener() {
                public void valueChanged(TreeSelectionEvent e) {
                    System.out.println("value changed : ");
                    DefaultMutableTreeNode node = (DefaultMutableTreeNode) listOfRounds.getLastSelectedPathComponent();
                    if (node != null) {
                        TreeNode parentNode = node.getParent();
                        if (parentNode != null) {
                            String parent = parentNode.toString();
                            String child = node.toString();
                            int deliveryPoint = 1;
                            int itinerary = 0;
                            if (parent.equals("Rounds")) {
                                itinerary = Integer.parseInt(child.substring(8)) - 1;
                                mainWindow.getGraphicalView().setItineraryIndex(itinerary);
                                mainWindow.getGraphicalView().setDeliveryPointIndex(deliveryPoint);
                                mainWindow.getGraphicalView().repaint();
                            } else if (parent.contains("Round n")) {
                                if (child.contains("DELETED")) {
                                    itinerary = Integer.parseInt(parent.substring(8)) - 1;
                                    deliveryPoint = Integer.parseInt(child.substring(21));

                                } else if (child.contains("Warehouse")) {
                                    itinerary = Integer.parseInt(parent.substring(8)) - 1;
                                    deliveryPoint = Integer.parseInt(child.substring(23));
                                } else {
                                    itinerary = Integer.parseInt(parent.substring(8)) - 1;
                                    deliveryPoint = Integer.parseInt(child.substring(11));
                                }
                                mainWindow.getGraphicalView().setItineraryIndex(itinerary);
                                mainWindow.getGraphicalView().setDeliveryPointIndex(deliveryPoint);
                                mainWindow.getGraphicalView().repaint();
                            }
                        }
                    }
                }
            });
            if (itineraryIndex != null && deliveryPointIndex != null) {
                TreePath path = new TreePath(nodesHierarchy.get(itineraryIndex).get(deliveryPointIndex).getPath());
                listOfRounds.expandPath(path);
                listOfRounds.setSelectionPath(path);
            }
            final Font currentFont = listOfRounds.getFont();
            final Font bigFont = new Font(currentFont.getName(), currentFont.getStyle(), currentFont.getSize() + 10);
            listOfRounds.setFont(bigFont);
            listOfRounds.setCellRenderer(renderer);
            JScrollPane container = new JScrollPane(listOfRounds, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                    ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            panel.removeAll();
            panel.add(container, BorderLayout.CENTER);
            panel.revalidate();
            panel.repaint();
        } else {
            listOfRounds = new JTree(rounds);
            final Font currentFont = listOfRounds.getFont();
            final Font bigFont = new Font(currentFont.getName(), currentFont.getStyle(), currentFont.getSize() + 10);
            listOfRounds.setFont(bigFont);
            listOfRounds.setCellRenderer(renderer);
            JScrollPane container = new JScrollPane(listOfRounds, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                    ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            panel.removeAll();
            panel.add(container, BorderLayout.CENTER);
            panel.revalidate();
            panel.repaint();
        }
    }

    /**
     * This method allows to display a specific path
     */
    private void displaySpecificPath() {
    }

    /**
     * This method allows to set the itineraries
     *
     * @param itineraries
     */
    public void setItineraries(List<Itinerary> itineraries) {
        this.itineraries = itineraries;
    }

    /**
     * This method allows to set the itinerary index
     *
     * @param itineraryIndex
     */
    public void setItineraryIndex(Integer itineraryIndex) {
        this.itineraryIndex = itineraryIndex;
    }

    /**
     * This method allows to set the delivery point index
     *
     * @param deliveryPointIndex
     */
    public void setDeliveryPointIndex(Integer deliveryPointIndex) {
        this.deliveryPointIndex = deliveryPointIndex;
    }

    /**
     * This method allows to get the itinerary index
     *
     * @return an Integer
     */
    public Integer getItineraryIndex() {
        return itineraryIndex;
    }

    /**
     * TODO never used
     * This method allows to get the delivery point index
     *
     * @return an integer
     */
    public Integer getDeliveryPointIndex() {
        return deliveryPointIndex;
    }

    /**
     * This method allows to get the index itenerary to delete
     *
     * @return List<Pair   <   Integer   ,       Integer>>
     */
    public List<Pair<Integer, Integer>> getIndexItineraryToDelete() {
        return indexItineraryToDelete;
    }

    /**
     * TODO never used
     * This method allows to set the index itenerary to delete
     *
     * @param indexItineraryToDelete
     */
    public void setIndexItineraryToDelete(List<Pair<Integer, Integer>> indexItineraryToDelete) {
        this.indexItineraryToDelete = indexItineraryToDelete;
    }
}
