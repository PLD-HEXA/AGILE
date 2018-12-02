package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import entities.Coordinate;
import entities.Itinerary;
import entities.Map;

/**
 * This class represents the graphic view of our project
 * It allows to draw the map , the deliveries points and also the rounds
 * @author User
 */
public class GraphicalView extends JPanel {

    /**
         * The map
    */
    private Map map;
    /**
         * The heightScale
    */
    private double heightScale;
     /**
         * The widthScale
    */
    private double widthScale;
     /**
         * The pointRadius
    */
    private static final int pointRadius = 5;
    /**
         * It allows to draw graphics  
    */
    private Graphics g;
     /**
         * The longitude max
    */
    double longMax;
     /**
         * The latitude max
    */
    double latMax;
     /**
         * The itineraries
    */
    List<Itinerary> itineraries;

    /**
     * The constructor
     * @param mainWindow
     *                  It represents our window
     */
    public GraphicalView(MainWindow mainWindow) {
        super();
        setLayout(null);
        setBackground(Color.gray);

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

    /**
     * This method allows to draw a plan
     * @param g
     *          It is an instance of the class Graphics that allows to draw graphics
     */
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

    /**
     * This method allows to draw all deliveries points and also the warehouse
     * @param g
     *          It is an instance of the class Graphics that allows to draw graphics
     */
    public void drawDeliveries(Graphics g) {
        double latitude;
        double longitude;
        int numberOfDeliveryPoints = map.getTabDeliveryPoints().size();
        for (int i = 0; i < numberOfDeliveryPoints; i++) {
            System.out.print(map.getCoordinates()[i]);
            latitude = (latMax - map.getCoordinates()[map.getTabDeliveryPoints().get(i).getKey()].getLatitude()) * widthScale;
            longitude = (longMax - map.getCoordinates()[map.getTabDeliveryPoints().get(i).getKey()].getLongitude()) * heightScale;
            g.setColor(Color.pink);
            g.drawOval((int) (this.getWidth() - longitude) - pointRadius, (int) latitude - pointRadius, pointRadius * 2, pointRadius * 2);
            g.fillOval((int) (this.getWidth() - longitude) - pointRadius, (int) latitude - pointRadius, pointRadius * 2, pointRadius * 2);
            try {
                BufferedImage image = ImageIO.read(new File("images/delivPoint.png"));
                g.drawImage(image, (int) (this.getWidth() - longitude) - pointRadius, (int) latitude - pointRadius - 13, null);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        latitude = (latMax - map.getCoordinates()[map.getWareHouse().getKey()].getLatitude()) * widthScale;
        longitude = (longMax - map.getCoordinates()[map.getWareHouse().getKey()].getLongitude()) * heightScale;
        g.setColor(Color.blue);
        g.drawOval((int) (this.getWidth() - longitude) - pointRadius, (int) latitude - pointRadius, pointRadius * 2, pointRadius * 2);
        g.fillOval((int) (this.getWidth() - longitude) - pointRadius, (int) latitude - pointRadius, pointRadius * 2, pointRadius * 2);
        try {
            BufferedImage image = ImageIO.read(new File("images/warehouse.png"));
            g.drawImage(image, (int) (this.getWidth() - longitude) - pointRadius, (int) latitude - pointRadius - 20, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * It allows to edit the map
     * @param map
     *           The new map
     */
    public void setMap(Map map) {
        this.map = map;
    }

    /**
     * It allows to get the map
     * @return
     *        The map
     */
    public Map getMap() {
        return map;
    }
    /**
     * This method allows to draw all rounds
     * @param g
     *          It is an instance of the class Graphics that allows to draw graphics
     */
    private void drawRounds(Graphics g) {
        double latitude1;
        double longitude1;
        double latitude2;
        double longitude2;
        int numberOfRounds = itineraries.size();
        for (int i = 0; i < numberOfRounds; i++) {
            double red = Math.random() * 255 + 1;
            double green = Math.random() * 255 + 1;
            double blue = Math.random() * 255 + 1;
            Color color = new Color((int) red, (int) green, (int) blue);
            g.setColor(color);
            int numberOfStops = itineraries.get(i).getDetailedPath().size();
            for (int j = 0; j < numberOfStops - 1; j++) {
                latitude1 = (latMax - itineraries.get(i).getDetailedPath().get(j).getLatitude()) * widthScale;
                longitude1 = (longMax - itineraries.get(i).getDetailedPath().get(j).getLongitude()) * heightScale;
                latitude2 = (latMax - itineraries.get(i).getDetailedPath().get(j + 1).getLatitude()) * widthScale;
                longitude2 = (longMax - itineraries.get(i).getDetailedPath().get(j + 1).getLongitude()) * heightScale;
                Graphics2D g2 = (Graphics2D) g;
                g2.setStroke(new BasicStroke(2));
                g2.draw(new Line2D.Float((int) (this.getWidth() - longitude1), (int) (latitude1),
                        (int) (this.getWidth() - longitude2), (int) (latitude2)));
            }
        }

    }

    /**
     * It allows to edit the itineraries
     * @param itineraries
     * *           The new itineraries
     */
    public void setItineraries(List<Itinerary> itineraries) {
        this.itineraries = itineraries;
    }

}
