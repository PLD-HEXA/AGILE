package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import entities.Coordinate;
import entities.Itinerary;
import entities.Map;
import java.util.ArrayList;
import javafx.util.Pair;

/**
 * This class represents the graphic view of our project It allows to draw the
 * map , the deliveries points and also the rounds
 *
 * @author PLD-HEXA-301
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
     *
     * The list of index to delete
     */
    private List<Integer> indexToDelete;
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
     * The index of the current highlighted itinerary
     */
    private Integer itineraryIndex;
    /**
     * The index of the current highlihghted delivery point in the itinerary
     */
    private Integer deliveryPointIndex;
    /**
     * The previous number Of rounds
     */
    private Integer previousNumberOfRounds;
    /**
     * The list of colors
     */
    private List<Color> colors;

    /**
     * the current zoom level (100 means the image is shown in original size)
     */
    private double zoom = 100;
    /**
     * the current scale (scale = zoom/100)
     */
    private double scale = 1;
    /**
     * the last seen scale
     */
    private double lastScale = 1;
    /**
     * The scrool pane that contains the graphical view
     */
    private JScrollPane scrollPane;
    /**
     * The map size
     */
    private int mapSize;
    /**
     * The main window
     */
    private MainWindow mainWindow;

    /**
     * The constructor
     *
     * @param mainWindow It represents our window
     */
    public GraphicalView(MainWindow mainWindow) {
        super();
        setLayout(null);
        setBackground(Color.gray);
        this.indexToDelete = new ArrayList<>();
        colors = new ArrayList<Color>();
        this.mainWindow = mainWindow;
        addMouseWheelListener(new MouseAdapter() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                super.mouseWheelMoved(e);
                int rotation = e.getWheelRotation();
                boolean zoomed = false;
                if (rotation > 0) {
                    if (scrollPane.getHeight() < getPreferredSize().getHeight()
                            || scrollPane.getWidth() < getPreferredSize().getWidth()) {
                        zoom = zoom / 1.3;
                        zoomed = true;
                    }
                } else {
                    double newCurrentZoom = zoom * 1.3;
                    if (newCurrentZoom < 1000) {
                        zoom = newCurrentZoom;
                        zoomed = true;
                    }
                }
                if (zoomed) {
                    scale = (float) (zoom / 100f);
                    alignViewPort(e.getPoint());
                    revalidate();
                    scrollPane.repaint();
                }
            }
        });
    }

    /**
     * It allows to align the view port
     *
     * @param mousePosition represents the position of the mouse
     */
    public void alignViewPort(Point mousePosition) {
        if (scale != lastScale) {
            double scaleChange = scale / lastScale;
            Point scaledMousePosition = new Point((int) Math.round(mousePosition.x * scaleChange),
                    (int) Math.round(mousePosition.y * scaleChange));
            Point viewportPosition = scrollPane.getViewport().getViewPosition();
            Point newViewportPosition = new Point(viewportPosition.x + scaledMousePosition.x - mousePosition.x,
                    viewportPosition.y + scaledMousePosition.y - mousePosition.y);
            scrollPane.getViewport().setViewPosition(newViewportPosition);
            lastScale = scale;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension((int) Math.round(mapSize * scale), (int) Math.round(mapSize * scale));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.scale(scale, scale);
        g2d.setColor(Color.white);
        if (map != null) {
            drawPlan(g2d);
            if (map.getWareHouse() != null) {
                drawDeliveries(g2d);
            }
        }
        if (itineraries != null) {
            drawRounds(g2d);
        }

        if (itineraryIndex != null && deliveryPointIndex != null) {
            displaySpecificRound(g2d);
        }

        g2d.dispose();

        this.g = g;

    }

    /**
     * This method allows to draw a plan
     *
     * @param g It is an instance of the class Graphics that allows to draw
     * graphics
     */
    public void drawPlan(Graphics g) {
        longMax = map.getCoordinateMax().getLongitude();
        latMax = map.getCoordinateMax().getLatitude();
        heightScale = (mapSize) / (longMax - map.getCoordinateMin().getLongitude());
        widthScale = mapSize / (latMax - map.getCoordinateMin().getLatitude());
        for (int i = 0; i < map.getGraph().size(); i++) {
            int numberOfSuccessors = map.getGraph().get(i).size();// taille de la i�me liste de segments dans graph
            for (int j = 0; j < numberOfSuccessors; j++) {// pour chaque successeur
                Coordinate curSuccessor = map.getCoordinates()[map.getGraph().get(i).get(j).getDestIndex()];
                g.drawLine((int) (mapSize - (longMax - map.getCoordinates()[i].getLongitude()) * heightScale),
                        (int) ((latMax - map.getCoordinates()[i].getLatitude()) * widthScale),
                        (int) (mapSize - (longMax - curSuccessor.getLongitude()) * heightScale),
                        (int) ((latMax - curSuccessor.getLatitude()) * widthScale));
            }
        }
    }

    /**
     * This method allows to draw all deliveries points and also the warehouse
     *
     * @param g It is an instance of the class Graphics that allows to draw
     * graphics
     */
    public void drawDeliveries(Graphics g) {
        double latitude;
        double longitude;
        int numberOfDeliveryPoints = map.getTabDeliveryPoints().size();
        for (int i = 0; i < numberOfDeliveryPoints; i++) {
            latitude = (latMax - map.getCoordinates()[map.getTabDeliveryPoints().get(i).getKey()].getLatitude())
                    * widthScale;
            longitude = (longMax - map.getCoordinates()[map.getTabDeliveryPoints().get(i).getKey()].getLongitude())
                    * heightScale;
            if (!indexToDelete.contains(i)) {
                g.setColor(Color.pink);
                g.drawOval((int) (mapSize - longitude) - pointRadius, (int) latitude - pointRadius, pointRadius * 2,
                        pointRadius * 2);
                g.fillOval((int) (mapSize - longitude) - pointRadius, (int) latitude - pointRadius, pointRadius * 2,
                        pointRadius * 2);
                try {
                    BufferedImage image = ImageIO.read(new File("images/delivPoint.png"));
                    g.drawImage(image, (int) (mapSize - longitude) - pointRadius, (int) latitude - pointRadius - 13,
                            null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                g.setColor(Color.DARK_GRAY);
                g.drawOval((int) (mapSize - longitude) - pointRadius, (int) latitude - pointRadius, pointRadius * 2,
                        pointRadius * 2);
                g.fillOval((int) (mapSize - longitude) - pointRadius, (int) latitude - pointRadius, pointRadius * 2,
                        pointRadius * 2);
                try {
                    BufferedImage image = ImageIO.read(new File("images/delivPointDeleted.png"));
                    g.drawImage(image, (int) (mapSize - longitude) - pointRadius, (int) latitude - pointRadius - 13,
                            null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        latitude = (latMax - map.getCoordinates()[map.getWareHouse().getKey()].getLatitude()) * widthScale;
        longitude = (longMax - map.getCoordinates()[map.getWareHouse().getKey()].getLongitude()) * heightScale;
        g.setColor(Color.blue);
        g.drawOval((int) (mapSize - longitude) - pointRadius, (int) latitude - pointRadius, pointRadius * 2,
                pointRadius * 2);
        g.fillOval((int) (mapSize - longitude) - pointRadius, (int) latitude - pointRadius, pointRadius * 2,
                pointRadius * 2);
        try {
            BufferedImage image = ImageIO.read(new File("images/warehouse.png"));
            g.drawImage(image, (int) (mapSize - longitude) - pointRadius, (int) latitude - pointRadius - 20, null);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * It allows to edit the map
     *
     * @param map The new map
     */
    public void setMap(Map map) {
        this.map = map;
    }

    /**
     * It allows to get the map
     *
     * @return The map
     */
    public Map getMap() {
        return map;
    }

    /**
     * This method allows to draw all rounds
     *
     * @param g It is an instance of the class Graphics that allows to draw
     * graphics
     */
    private void drawRounds(Graphics g) {
        double latitude1;
        double longitude1;
        double latitude2;
        double longitude2;
        int numberOfRounds = itineraries.size();
        if (previousNumberOfRounds == null || previousNumberOfRounds != numberOfRounds) {
            colors = new ArrayList<Color>();
            for (int i = 0; i < numberOfRounds; i++) {
                double red = Math.random() * 255 + 1;
                double green = Math.random() * 255 + 1;
                double blue = Math.random() * 255 + 1;
                Color color = new Color((int) red, (int) green, (int) blue);
                colors.add(color);
                g.setColor(color);
                int numberOfStops = itineraries.get(i).getDetailedPath().size();
                for (int j = 0; j < numberOfStops - 1; j++) {
                    latitude1 = (latMax - itineraries.get(i).getDetailedPath().get(j).getLatitude()) * widthScale;
                    longitude1 = (longMax - itineraries.get(i).getDetailedPath().get(j).getLongitude()) * heightScale;
                    latitude2 = (latMax - itineraries.get(i).getDetailedPath().get(j + 1).getLatitude()) * widthScale;
                    longitude2 = (longMax - itineraries.get(i).getDetailedPath().get(j + 1).getLongitude())
                            * heightScale;
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setStroke(new BasicStroke(3));
                    g2.draw(new Line2D.Float((int) (mapSize - longitude1), (int) (latitude1),
                            (int) (mapSize - longitude2), (int) (latitude2)));
                }
            }
        } else {
            for (int i = 0; i < numberOfRounds; i++) {
                g.setColor(colors.get(i));
                int numberOfStops = itineraries.get(i).getDetailedPath().size();
                for (int j = 0; j < numberOfStops - 1; j++) {
                    latitude1 = (latMax - itineraries.get(i).getDetailedPath().get(j).getLatitude()) * widthScale;
                    longitude1 = (longMax - itineraries.get(i).getDetailedPath().get(j).getLongitude()) * heightScale;
                    latitude2 = (latMax - itineraries.get(i).getDetailedPath().get(j + 1).getLatitude()) * widthScale;
                    longitude2 = (longMax - itineraries.get(i).getDetailedPath().get(j + 1).getLongitude())
                            * heightScale;
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setStroke(new BasicStroke(3));
                    g2.draw(new Line2D.Float((int) (mapSize - longitude1), (int) (latitude1),
                            (int) (mapSize - longitude2), (int) (latitude2)));
                }
            }
        }
        previousNumberOfRounds = numberOfRounds;

    }

    /**
     * This method allows to draw a specific round
     *
     * @param g It is an instance of the class Graphics that allows to draw
     * graphics
     */
    public void displaySpecificRound(Graphics g) {
        double latitude1;
        double longitude1;
        double latitude2;
        double longitude2;
        int numberOfDetailedStops = itineraries.get(itineraryIndex).getDetailedPath().size();
        for (int j = 0; j < numberOfDetailedStops - 1; j++) {
            latitude1 = (latMax - itineraries.get(itineraryIndex).getDetailedPath().get(j).getLatitude()) * widthScale;
            longitude1 = (longMax - itineraries.get(itineraryIndex).getDetailedPath().get(j).getLongitude())
                    * heightScale;
            latitude2 = (latMax - itineraries.get(itineraryIndex).getDetailedPath().get(j + 1).getLatitude())
                    * widthScale;
            longitude2 = (longMax - itineraries.get(itineraryIndex).getDetailedPath().get(j + 1).getLongitude())
                    * heightScale;
            g.setColor(Color.black);
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(6));
            g2.draw(new Line2D.Float((int) (mapSize - longitude1), (int) (latitude1), (int) (mapSize - longitude2),
                    (int) (latitude2)));
        }
        double delivLatitude;
        double delivLongitude;
        int numberOfGeneralStops = itineraries.get(itineraryIndex).getGeneralPath().size();
        for (int i = 0; i < numberOfGeneralStops; i++) {
            delivLatitude = (latMax
                    - itineraries.get(itineraryIndex).getGeneralPath().get(i).getCoordinate().getLatitude())
                    * widthScale;
            delivLongitude = (longMax
                    - itineraries.get(itineraryIndex).getGeneralPath().get(i).getCoordinate().getLongitude())
                    * heightScale;
            if (!mainWindow.getTextualView().getIndexItineraryToDelete().contains(new Pair(itineraryIndex, i))) {
                if (i > deliveryPointIndex) {
                    g.setColor(Color.red);
                    g.drawOval((int) (mapSize - delivLongitude) - pointRadius, (int) delivLatitude - pointRadius,
                            pointRadius * 2, pointRadius * 2);
                    g.fillOval((int) (mapSize - delivLongitude) - pointRadius, (int) delivLatitude - pointRadius,
                            pointRadius * 2, pointRadius * 2);
                    try {
                        BufferedImage image = ImageIO.read(new File("images/delivPoint.png"));
                        g.drawImage(image, (int) (mapSize - delivLongitude) - pointRadius,
                                (int) delivLatitude - pointRadius - 13, null);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (i == deliveryPointIndex) {
                    g.setColor(Color.orange);
                    g.drawOval((int) (mapSize - delivLongitude) - pointRadius, (int) delivLatitude - pointRadius,
                            pointRadius * 2, pointRadius * 2);
                    g.fillOval((int) (mapSize - delivLongitude) - pointRadius, (int) delivLatitude - pointRadius,
                            pointRadius * 2, pointRadius * 2);
                    try {
                        BufferedImage image = ImageIO.read(new File("images/deliveryMan.png"));
                        g.drawImage(image, (int) (mapSize - delivLongitude) - pointRadius,
                                (int) delivLatitude - pointRadius - 13, null);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    g.setColor(Color.green);
                    g.drawOval((int) (mapSize - delivLongitude) - pointRadius, (int) delivLatitude - pointRadius,
                            pointRadius * 2, pointRadius * 2);
                    g.fillOval((int) (mapSize - delivLongitude) - pointRadius, (int) delivLatitude - pointRadius,
                            pointRadius * 2, pointRadius * 2);
                    try {
                        BufferedImage image = ImageIO.read(new File("images/delivPoint.png"));

                        g.drawImage(image, (int) (mapSize - delivLongitude) - pointRadius,
                                (int) delivLatitude - pointRadius - 13, null);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                g.setColor(Color.DARK_GRAY);
                g.drawOval((int) (mapSize - delivLongitude) - pointRadius, (int) delivLatitude - pointRadius,
                        pointRadius * 2, pointRadius * 2);
                g.fillOval((int) (mapSize - delivLongitude) - pointRadius, (int) delivLatitude - pointRadius,
                        pointRadius * 2, pointRadius * 2);
                if (i == deliveryPointIndex) {
                    try {
                        BufferedImage image = ImageIO.read(new File("images/deliveryMan.png"));
                        g.drawImage(image, (int) (mapSize - delivLongitude) - pointRadius,
                                (int) delivLatitude - pointRadius - 13, null);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        BufferedImage image = ImageIO.read(new File("images/delivPointDeleted.png"));
                        g.drawImage(image, (int) (mapSize - delivLongitude) - pointRadius,
                                (int) delivLatitude - pointRadius - 13, null);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
        if (deliveryPointIndex == 0) {
            delivLatitude = (latMax - itineraries.get(itineraryIndex).getGeneralPath().get(deliveryPointIndex)
                    .getCoordinate().getLatitude()) * widthScale;
            delivLongitude = (longMax - itineraries.get(itineraryIndex).getGeneralPath().get(deliveryPointIndex)
                    .getCoordinate().getLongitude()) * heightScale;
            g.setColor(Color.orange);
            g.drawOval((int) (mapSize - delivLongitude) - pointRadius, (int) delivLatitude - pointRadius,
                    pointRadius * 2, pointRadius * 2);
            g.fillOval((int) (mapSize - delivLongitude) - pointRadius, (int) delivLatitude - pointRadius,
                    pointRadius * 2, pointRadius * 2);
            try {
                BufferedImage image = ImageIO.read(new File("images/deliveryMan.png"));
                g.drawImage(image, (int) (mapSize - delivLongitude) - pointRadius,
                        (int) delivLatitude - pointRadius - 13, null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * It allows to get the scale
     *
     * @return double
     */
    public double getScale() {
        return scale;
    }

    /**
     * It allows to get the map size
     *
     * @return
     */
    public int getMapSize() {
        return mapSize;
    }

    /**
     * It allows to get the coordinate position
     *
     * @param coordinate the coordinate
     * @return a board of double
     */
    public double[] getCoordinatePosition(Coordinate coordinate) {
        double x = mapSize - ((longMax - coordinate.getLongitude()) * heightScale + pointRadius);
        double y = ((latMax - coordinate.getLatitude()) * widthScale - pointRadius);
        return new double[]{x, y};
    }

    /**
     * It allows to edit the itineraries
     *
     * @param itineraries * The new itineraries
     */
    public void setItineraries(List<Itinerary> itineraries) {
        this.itineraries = itineraries;
    }

    /**
     * It allows to get the max longitude
     *
     * @return
     */
    public double getLongMax() {
        return longMax;
    }

    /**
     * It allows to get the max latitude
     *
     * @return
     */
    public double getLatMax() {
        return latMax;
    }

    /**
     * It allows to get the height scale
     *
     * @return
     */
    public double getHeightScale() {
        return heightScale;
    }

    /**
     * It allows to get width scale
     *
     * @return
     */
    public double getWidthScale() {
        return widthScale;
    }

    /**
     * It allows to get the point radius
     *
     * @return
     */
    public static int getPointradius() {
        return pointRadius;
    }

    /**
     * It allows to get the itinerary index
     *
     * @return
     */
    public Integer getItineraryIndex() {
        return itineraryIndex;
    }

    /**
     * It allows to get the scroll pane that contains the graphical view
     *
     * @return a JScrollPane
     */
    public JScrollPane getScrollPane() {
        return scrollPane;
    }

    /**
     * It allows to set the itinerary index
     *
     * @param itineraryIndex
     */
    public void setItineraryIndex(Integer itineraryIndex) {
        this.itineraryIndex = itineraryIndex;
    }

    /**
     * It allows to get the delivery point index
     *
     * @return
     */
    public Integer getDeliveryPointIndex() {
        return deliveryPointIndex;
    }

    /**
     * It allows to set the delivery point index
     *
     * @param deliveryPointIndex
     */
    public void setDeliveryPointIndex(Integer deliveryPointIndex) {
        this.deliveryPointIndex = deliveryPointIndex;
    }

    /**
     * It allows to get the iteneraries
     *
     * @return
     */
    public List<Itinerary> getItineraries() {
        return itineraries;
    }

    /**
     * It allows to set the scrollPane
     *
     * @param scrollPane
     */
    public void setScrollPane(JScrollPane scrollPane) {
        this.scrollPane = scrollPane;
    }

    /**
     * It allows to set the map size
     *
     * @param mapSize
     */
    public void setMapSize(int mapSize) {
        this.mapSize = mapSize;
    }

    /**
     * It allows to get the list of index to delete
     *
     * @return
     */
    public List<Integer> getIndexToDelete() {
        return indexToDelete;
    }

    /**
     * It allows to set the List of index to delete
     *
     * @param indexToDelete
     */
    public void setIndexToDelete(List<Integer> indexToDelete) {
        this.indexToDelete = indexToDelete;
    }
}
