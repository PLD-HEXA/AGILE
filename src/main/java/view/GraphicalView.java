
package view;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import entities.Coordinate;
import entities.DeliveryPoint;
import entities.Itinerary;
import entities.Map;
import entities.Segment;

public class GraphicalView extends JPanel {

	private Map map;
	private double heightScale;
	private double widthScale;
	private static final int pointRadius = 5;
	private Graphics g;
	double longMax;
	double latMax;
	List<Itinerary> itineraries;

	public GraphicalView(MainWindow mainWindow) {
		super();
		// plan.addObserver(this);
//        height = 800;
//        width = 800;
		setLayout(null);

		setBackground(Color.gray);
//        mainWindow.getContentPane().add(this);
//		itineraries = new ArrayList<Itinerary>();
//		Itinerary itinerary = new Itinerary();
//		List<Coordinate> detailedPath = new ArrayList<Coordinate>();
//		detailedPath.add(new Coordinate(4.8744674,45.750404));
//		detailedPath.add(new Coordinate(4.8718166,45.75171));
//		detailedPath.add(new Coordinate(4.886816,45.754265));
//		itinerary.setDetailedPath(detailedPath);
//		itineraries.add(itinerary);
	
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.white);
		if (map != null) {
			drawPlan(g);
			if (map.getWareHouse() != null) {
				drawDeliveries(g);
			}
		}
		if (itineraries != null) {
			drawRounds(g);
		}
		
		
		this.g = g;
	}

	public void drawPlan(Graphics g) {
		longMax = map.getCoordinateMax().getLongitude();
		latMax = map.getCoordinateMax().getLatitude();
		heightScale = this.getHeight() / (longMax - map.getCoordinateMin().getLongitude());
		widthScale = this.getWidth() / (latMax - map.getCoordinateMin().getLatitude());
		for (int i = 0; i < map.getGraph().size(); i++) {
			int numberOfSuccessors = map.getGraph().get(i).size();// taille de la i�me liste de segments dans graph
			for (int j = 0; j < numberOfSuccessors; j++) {// pour chaque successeur
				Coordinate curSuccessor = map.getCoordinates()[map.getGraph().get(i).get(j).getDestIndex()];
				g.drawLine((int) (this.getWidth() - (longMax - map.getCoordinates()[i].getLongitude()) * heightScale),
						(int) ((latMax - map.getCoordinates()[i].getLatitude()) * widthScale),
						(int) (this.getWidth() - (longMax - curSuccessor.getLongitude()) * heightScale),
						(int) ((latMax - curSuccessor.getLatitude()) * widthScale));
			}
		}
	}

	public void drawDeliveries(Graphics g) {
		double latitude;
		double longitude;
		int numberOfDeliveryPoints = map.getTabDeliveryPoints().size();
		for (int i = 0; i < numberOfDeliveryPoints; i++) {
			System.out.print(map.getCoordinates()[i]);
			latitude = (latMax - map.getCoordinates()[map.getTabDeliveryPoints().get(i).getKey()].getLatitude()) * widthScale;
			longitude = (longMax - map.getCoordinates()[map.getTabDeliveryPoints().get(i).getKey()].getLongitude()) * heightScale;
			g.setColor(Color.pink);
			g.drawOval((int) (this.getWidth() - longitude)-pointRadius, (int) latitude-pointRadius, pointRadius*2, pointRadius*2);
			g.fillOval((int) (this.getWidth() - longitude)-pointRadius, (int) latitude-pointRadius, pointRadius*2, pointRadius*2);
		}
		latitude = (latMax - map.getCoordinates()[map.getWareHouse().getKey()].getLatitude()) * widthScale;
		longitude = (longMax - map.getCoordinates()[map.getWareHouse().getKey()].getLongitude()) * heightScale;
		g.setColor(Color.blue);
		
		g.drawOval((int) (this.getWidth() - longitude)-pointRadius, (int) latitude-pointRadius, pointRadius*2, pointRadius*2);
		g.fillOval((int) (this.getWidth() - longitude)-pointRadius, (int) latitude-pointRadius, pointRadius*2, pointRadius*2);
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public Map getMap() {
		return map;
	}

	private void drawRounds(Graphics g) {
		double latitude1;
		double longitude1;
		double latitude2;
		double longitude2;
		int numberOfRounds = itineraries.size();
		for (int i = 0; i < numberOfRounds; i++) {
			double red = Math.random()*255+1;
			double green = Math.random()*255+1;
			double blue = Math.random()*255+1;
			Color color = new Color((int)red,(int)green,(int)blue);
			g.setColor(color);
			int numberOfStops = itineraries.get(i).getDetailedPath().size();
			for (int j = 0; j < numberOfStops - 1; j++) {
				latitude1 = (latMax - itineraries.get(i).getDetailedPath().get(j).getLatitude()) * widthScale;
				longitude1 = (longMax - itineraries.get(i).getDetailedPath().get(j).getLongitude()) * heightScale;
				latitude2 = (latMax - itineraries.get(i).getDetailedPath().get(j + 1).getLatitude()) * widthScale;
				longitude2 = (longMax - itineraries.get(i).getDetailedPath().get(j + 1).getLongitude()) * heightScale;
				
				
				Graphics2D g2 = (Graphics2D) g;
                g2.setStroke(new BasicStroke(2));
//                g2.draw(new Line2D.Float(30, 20, 80, 90));
				g2.draw(new Line2D.Float((int) (this.getWidth() - longitude1), (int) (latitude1),
						(int) (this.getWidth() - longitude2), (int) (latitude2)));
			}
		}

	}

	public void setItineraries(List<Itinerary> itineraries) {
		this.itineraries = itineraries;
	}
	
	

}
