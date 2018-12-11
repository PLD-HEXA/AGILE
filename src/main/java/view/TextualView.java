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
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import entities.Coordinate;
import entities.DeliveryPoint;
import entities.Itinerary;

public class TextualView extends JPanel {

	private List<Itinerary> itineraries;
	private JTree listOfRounds;
	private DefaultTreeCellRenderer renderer;
	private MainWindow mainWindow;
	Graphics g;
	JPanel panel;
	private Integer itineraryIndex; // Index of the current highlighted itinerary
	private Integer deliveryPointIndex; // Index of the index of the current highlihghted delivery point in the
										// itinerary
	private List<List<DefaultMutableTreeNode>> nodesHierarchy;

	public TextualView(MainWindow mainWindow) {
		super();
		this.mainWindow = mainWindow;
		setLayout(new BorderLayout());
		setBackground(Color.pink);
		nodesHierarchy = new ArrayList<List<DefaultMutableTreeNode>>();
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

	public void displayListOfRounds() {
		DefaultMutableTreeNode rounds = new DefaultMutableTreeNode("Rounds");
		if (itineraries != null) {
			int numberOfRounds = itineraries.size();
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
				curRound = new DefaultMutableTreeNode("Round n " + Integer.toString(i + 1));
				nodesHierarchy.add(i, new ArrayList<DefaultMutableTreeNode>());
				for (int j = 0; j < numberOfStops; j++) {
					departureDate = itineraries.get(i).getGeneralPath().get(j).getDepartureTime();
					arrivalDate = itineraries.get(i).getGeneralPath().get(j).getArrivalTime();
					curStop = new DefaultMutableTreeNode("Delivery n " + Integer.toString(j));
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
			listOfRounds = new JTree(rounds);
			listOfRounds.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
			listOfRounds.addTreeSelectionListener(new TreeSelectionListener() {
				public void valueChanged(TreeSelectionEvent e) {
					DefaultMutableTreeNode node = (DefaultMutableTreeNode) listOfRounds.getLastSelectedPathComponent();
					if (node != null) {
						System.out.println(node);
						TreeNode parentNode = node.getParent();
						if (parentNode != null) {
							System.out.println(parentNode);
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
								itinerary = Integer.parseInt(parent.substring(8)) - 1;
								System.out.print("parsing child " + child.substring(11));
								deliveryPoint = Integer.parseInt(child.substring(11));
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
			JScrollPane conteneur = new JScrollPane(listOfRounds, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
					ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			panel.removeAll();
			panel.add(conteneur, BorderLayout.CENTER);
			panel.revalidate();
			panel.repaint();
		} else {
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
	}

	public void displaySpecificPath() {

	}

	public void setItineraries(List<Itinerary> itineraries) {
		this.itineraries = itineraries;
	}

	public void setItineraryIndex(Integer itineraryIndex) {
		this.itineraryIndex = itineraryIndex;
	}

	public void setDeliveryPointIndex(Integer deliveryPointIndex) {
		this.deliveryPointIndex = deliveryPointIndex;
	}

	public Integer getItineraryIndex() {
		return itineraryIndex;
	}

	public Integer getDeliveryPointIndex() {
		return deliveryPointIndex;
	}

}
