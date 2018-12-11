package view;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import entities.Coordinate;
import entities.DeliveryPoint;
import entities.Itinerary;
import entities.Map;
import entities.Segment;
import java.util.ArrayList;

public class GraphicalView extends JPanel {

    private Map map;
    private double heightScale;
    private double widthScale;
    private static final int pointRadius = 5;
    private Graphics g;
    private List<Integer> indexToDelete;
    private double longMax;
    private double latMax;
    private List<Itinerary> itineraries;
    private Integer itineraryIndex; //Index of the current highlighted itinerary
    private Integer deliveryPointIndex; // Index of the index of the current highlihghted delivery point in the itinerary
    private Integer previousNumberOfRounds;
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

    private JScrollPane scrollPane;

    private int mapSize;

    public GraphicalView(MainWindow mainWindow) {
        super();
        setLayout(null);
        setBackground(Color.gray);
        this.indexToDelete = new ArrayList<>();
        colors = new ArrayList<Color>();
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
                    if (newCurrentZoom < 1000) { // 1000 ~ 10 times zoom
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

    public void alignViewPort(Point mousePosition) {
        if (scale != lastScale) {
            double scaleChange = scale / lastScale;
            Point scaledMousePosition = new Point(
                    (int) Math.round(mousePosition.x * scaleChange),
                    (int) Math.round(mousePosition.y * scaleChange)
            );
            Point viewportPosition = scrollPane.getViewport().getViewPosition();
            Point newViewportPosition = new Point(
                    viewportPosition.x + scaledMousePosition.x - mousePosition.x,
                    viewportPosition.y + scaledMousePosition.y - mousePosition.y
            );
            scrollPane.getViewport().setViewPosition(newViewportPosition);
            lastScale = scale;
        }
    }

    @Override
    public Dimension getPreferredSize() {
        //Faut changer par la taille r�elle
        return new Dimension((int) Math.round(mapSize * scale), (int) Math.round(mapSize * scale));
    }

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

    public void drawDeliveries(Graphics g) {
        double latitude;
        double longitude;
        int numberOfDeliveryPoints = map.getTabDeliveryPoints().size();
        for (int i = 0; i < numberOfDeliveryPoints; i++) {
            if (indexToDelete.isEmpty()) {
                latitude = (latMax - map.getCoordinates()[map.getTabDeliveryPoints().get(i).getKey()].getLatitude()) * widthScale;
                longitude = (longMax - map.getCoordinates()[map.getTabDeliveryPoints().get(i).getKey()].getLongitude()) * heightScale;
                g.setColor(Color.pink);
                g.drawOval((int) (mapSize - longitude) - pointRadius, (int) latitude - pointRadius, pointRadius * 2, pointRadius * 2);
                g.fillOval((int) (mapSize - longitude) - pointRadius, (int) latitude - pointRadius, pointRadius * 2, pointRadius * 2);
                try {
                    BufferedImage image = ImageIO.read(new File("images/delivPoint.png"));
                    g.drawImage(image, (int) (mapSize - longitude) - pointRadius, (int) latitude - pointRadius - 13, null);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else {
                System.out.println("delete index : " + indexToDelete.get(0));
                for (int deletedIndex : indexToDelete) {
                    latitude = (latMax - map.getCoordinates()[map.getTabDeliveryPoints().get(i).getKey()].getLatitude()) * widthScale;
                    longitude = (longMax - map.getCoordinates()[map.getTabDeliveryPoints().get(i).getKey()].getLongitude()) * heightScale;
                    if (deletedIndex != i) {
                        g.setColor(Color.pink);
                        try {
                            BufferedImage image = ImageIO.read(new File("images/delivPoint.png"));
                            g.drawImage(image, (int) (mapSize - longitude) - pointRadius, (int) latitude - pointRadius - 13, null);
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    } else {
                        g.setColor(Color.gray);
                        try {
                            BufferedImage image = ImageIO.read(new File("images/delivPointDeleted.png"));
                            g.drawImage(image, (int) (mapSize - longitude) - pointRadius, (int) latitude - pointRadius - 13, null);
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    g.drawOval((int) (mapSize - longitude) - pointRadius, (int) latitude - pointRadius, pointRadius * 2, pointRadius * 2);
                    g.fillOval((int) (mapSize - longitude) - pointRadius, (int) latitude - pointRadius, pointRadius * 2, pointRadius * 2);
                }
            }
        }
        latitude = (latMax - map.getCoordinates()[map.getWareHouse().getKey()].getLatitude()) * widthScale;
        longitude = (longMax - map.getCoordinates()[map.getWareHouse().getKey()].getLongitude()) * heightScale;

        g.setColor(Color.blue);

        g.drawOval((int) (mapSize - longitude) - pointRadius, (int) latitude
                - pointRadius, pointRadius
                * 2, pointRadius
                * 2);
        g.fillOval(
                (int) (mapSize - longitude) - pointRadius, (int) latitude
                - pointRadius, pointRadius
                * 2, pointRadius
                * 2);
        try {
            BufferedImage image = ImageIO.read(new File("images/warehouse.png"));
            g.drawImage(image, (int) (mapSize - longitude) - pointRadius, (int) latitude - pointRadius - 20, null);
        } catch (IOException e
    
        ) {
          // TODO Auto-generated catch block
          e.printStackTrace();
    }
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
		if(previousNumberOfRounds==null || previousNumberOfRounds!=numberOfRounds) {
			colors = new ArrayList<Color>();
			for (int i = 0; i < numberOfRounds; i++) {
				double red = Math.random()*255+1;
				double green = Math.random()*255+1;
				double blue = Math.random()*255+1;
				Color color = new Color((int)red,(int)green,(int)blue);
				colors.add(color);
				g.setColor(color);
				int numberOfStops = itineraries.get(i).getDetailedPath().size();
				for (int j = 0; j < numberOfStops - 1; j++) {
					latitude1 = (latMax - itineraries.get(i).getDetailedPath().get(j).getLatitude()) * widthScale;
					longitude1 = (longMax - itineraries.get(i).getDetailedPath().get(j).getLongitude()) * heightScale;
					latitude2 = (latMax - itineraries.get(i).getDetailedPath().get(j + 1).getLatitude()) * widthScale;
					longitude2 = (longMax - itineraries.get(i).getDetailedPath().get(j + 1).getLongitude()) * heightScale;
					Graphics2D g2 = (Graphics2D) g;
                                        g2.setStroke(new BasicStroke(3));
					g2.draw(new Line2D.Float((int) (mapSize - longitude1), (int) (latitude1),
							(int) (mapSize - longitude2), (int) (latitude2)));
				}
			}
		}
		else {
			for (int i = 0; i < numberOfRounds; i++) {
				g.setColor(colors.get(i));
				int numberOfStops = itineraries.get(i).getDetailedPath().size();
				for (int j = 0; j < numberOfStops - 1; j++) {
					latitude1 = (latMax - itineraries.get(i).getDetailedPath().get(j).getLatitude()) * widthScale;
					longitude1 = (longMax - itineraries.get(i).getDetailedPath().get(j).getLongitude()) * heightScale;
					latitude2 = (latMax - itineraries.get(i).getDetailedPath().get(j + 1).getLatitude()) * widthScale;
					longitude2 = (longMax - itineraries.get(i).getDetailedPath().get(j + 1).getLongitude()) * heightScale;
					Graphics2D g2 = (Graphics2D) g;
                                        g2.setStroke(new BasicStroke(3));
					g2.draw(new Line2D.Float((int) (mapSize - longitude1), (int) (latitude1),
							(int) (mapSize - longitude2), (int) (latitude2)));
				}
			}
		}
		previousNumberOfRounds=numberOfRounds;
		

	}

	public void displaySpecificRound(Graphics g){
		//Display the specific detailed path
		double latitude1;
		double longitude1;
		double latitude2;
		double longitude2;
		int numberOfDetailedStops = itineraries.get(itineraryIndex).getDetailedPath().size();
		for (int j = 0; j < numberOfDetailedStops - 1; j++) {
			latitude1 = (latMax - itineraries.get(itineraryIndex).getDetailedPath().get(j).getLatitude()) * widthScale;
			longitude1 = (longMax - itineraries.get(itineraryIndex).getDetailedPath().get(j).getLongitude()) * heightScale;
			latitude2 = (latMax - itineraries.get(itineraryIndex).getDetailedPath().get(j + 1).getLatitude()) * widthScale;
			longitude2 = (longMax - itineraries.get(itineraryIndex).getDetailedPath().get(j + 1).getLongitude()) * heightScale;
			g.setColor(Color.black);
			Graphics2D g2 = (Graphics2D) g;
                        g2.setStroke(new BasicStroke(6));
			g2.draw(new Line2D.Float((int) (mapSize - longitude1), (int) (latitude1),
					(int) (mapSize - longitude2), (int) (latitude2)));
		}   
		//Display the specific general path
		double delivLatitude;
		double delivLongitude;
		int numberOfGeneralStops = itineraries.get(itineraryIndex).getGeneralPath().size();		
		for (int i = 0; i < numberOfGeneralStops ; i++) {
			delivLatitude = (latMax - itineraries.get(itineraryIndex).getGeneralPath().get(i).getCoordinate().getLatitude()) * widthScale;
			delivLongitude = (longMax - itineraries.get(itineraryIndex).getGeneralPath().get(i).getCoordinate().getLongitude()) * heightScale;
			if(i>deliveryPointIndex ) {
				g.setColor(Color.red);
				g.drawOval((int) (mapSize - delivLongitude)-pointRadius, (int) delivLatitude-pointRadius, pointRadius*2, pointRadius*2);
				g.fillOval((int) (mapSize - delivLongitude)-pointRadius, (int) delivLatitude-pointRadius, pointRadius*2, pointRadius*2);
				 try {
						BufferedImage image = ImageIO.read(new File("images/delivPoint.png"));
						g.drawImage(image, (int) (mapSize - delivLongitude)-pointRadius, (int) delivLatitude-pointRadius-13, null);
				    } catch (IOException e) {
						e.printStackTrace();
					} 
			}
			else if(i==deliveryPointIndex) {
				g.setColor(Color.orange);
				g.drawOval((int) (mapSize - delivLongitude)-pointRadius, (int) delivLatitude-pointRadius, pointRadius*2, pointRadius*2);
				g.fillOval((int) (mapSize - delivLongitude)-pointRadius, (int) delivLatitude-pointRadius, pointRadius*2, pointRadius*2);
				 try {
						BufferedImage image = ImageIO.read(new File("images/deliveryMan.png"));
						g.drawImage(image, (int) (mapSize - delivLongitude)-pointRadius, (int) delivLatitude-pointRadius-13, null);
				    } catch (IOException e) {
						e.printStackTrace();
					} 
			}
			else{
				g.setColor(Color.green);
				g.drawOval((int) (mapSize - delivLongitude)-pointRadius, (int) delivLatitude-pointRadius, pointRadius*2, pointRadius*2);
				g.fillOval((int) (mapSize - delivLongitude)-pointRadius, (int) delivLatitude-pointRadius, pointRadius*2, pointRadius*2);
				 try {
						BufferedImage image = ImageIO.read(new File("images/delivPoint.png"));

						g.drawImage(image, (int) (mapSize - delivLongitude)-pointRadius, (int) delivLatitude-pointRadius-13, null);
				    } catch (IOException e) {
						e.printStackTrace();
					} 
			}
		}	
		if(deliveryPointIndex==0 ) {
			delivLatitude = (latMax - itineraries.get(itineraryIndex).getGeneralPath().get(deliveryPointIndex).getCoordinate().getLatitude()) * widthScale;
			delivLongitude = (longMax - itineraries.get(itineraryIndex).getGeneralPath().get(deliveryPointIndex).getCoordinate().getLongitude()) * heightScale;
			g.setColor(Color.orange);
			g.drawOval((int) (mapSize - delivLongitude)-pointRadius, (int) delivLatitude-pointRadius, pointRadius*2, pointRadius*2);
			g.fillOval((int) (mapSize - delivLongitude)-pointRadius, (int) delivLatitude-pointRadius, pointRadius*2, pointRadius*2);
			 try {
					BufferedImage image = ImageIO.read(new File("images/deliveryMan.png"));
					g.drawImage(image, (int) (mapSize - delivLongitude)-pointRadius, (int) delivLatitude-pointRadius-13, null);
			    } catch (IOException e) {
					e.printStackTrace();
				} 
		}
		
	}
	
	

	public void setItineraries(List<Itinerary> itineraries) {
		this.itineraries = itineraries;
	}


	public double getLongMax() {
		return longMax;
	}

	public double getLatMax() {
		return latMax;
	}

	public double getHeightScale() {
		return heightScale;
	}

	public double getWidthScale() {
		return widthScale;
	}

	public static int getPointradius() {
		return pointRadius;
	}

	public Integer getItineraryIndex() {
		return itineraryIndex;
	}

	public void setItineraryIndex(Integer itineraryIndex) {
		this.itineraryIndex = itineraryIndex;
	}

	public Integer getDeliveryPointIndex() {
		return deliveryPointIndex;
	}

	public void setDeliveryPointIndex(Integer deliveryPointIndex) {
		this.deliveryPointIndex = deliveryPointIndex;
	}

	public List<Itinerary> getItineraries() {
		return itineraries;
	}
	public void setScrollPane(JScrollPane scrollPane) {
		this.scrollPane = scrollPane;
	}
	public void setMapSize(int mapSize) {
		this.mapSize = mapSize;
	}

        public List<Integer> getIndexToDelete() {
            return indexToDelete;
        }

        public void setIndexToDelete(List<Integer> indexToDelete) {
            this.indexToDelete = indexToDelete;
        }
        
        
        
}
