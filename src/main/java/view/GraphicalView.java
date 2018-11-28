
package view;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import javax.swing.JPanel;

import entities.Coordinate;
import entities.Map;
import entities.Segment;

public class GraphicalView extends JPanel {

    private Map map;
    private double heightScale;
    private double widthScale;
    private int height;
    private int width;
    private Graphics g;

    public GraphicalView(MainWindow mainWindow) {
        super();
        //plan.addObserver(this); 
//        height = 800;
//        width = 800;
        setLayout(null);
       
        setBackground(Color.gray);
//        mainWindow.getContentPane().add(this);

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.white);
        if (map != null) {
            drawPlan(g);
            if(map.getTabDeliveryPoints() != null) {
            	drawDeliveries(g);
            }
        }
        this.g = g;
    }

    

    public void drawPlan(Graphics g) {
        double longMax = map.getCoordinateMax().getLongitude();
        double latMax = map.getCoordinateMax().getLatitude();
        heightScale = this.getHeight() / (longMax - map.getCoordinateMin().getLongitude());
        widthScale = this.getWidth() / (latMax - map.getCoordinateMin().getLatitude());
        for (int i = 0; i < map.getGraph().size(); i++) {
            int numberOfSuccessors = map.getGraph().get(i).size();//taille de la i�me liste de segments dans graph
            for (int j = 0; j < numberOfSuccessors; j++) {//pour chaque successeur
                Coordinate curSuccessor = map.getCoordinates()[ map.getGraph().get(i).get(j).getDestIndex()];
                g.drawLine((int) (this.getWidth()-(longMax - map.getCoordinates()[i].getLongitude()) * heightScale), (int) ((latMax - map.getCoordinates()[i].getLatitude()) * widthScale),
                		(int) (this.getWidth()-(longMax - curSuccessor.getLongitude()) * heightScale), (int) ((latMax - curSuccessor.getLatitude()) * widthScale));
            }
        }
    }
    
    public void drawDeliveries(Graphics g) {
    	double latitude;
		double longitude;
//    	int numberOfDeliveryPoints=map.getTabDeliveryPoints().length;
//    	for(int i=0;i<numberOfDeliveryPoints;i++) {
//    		latitude= (map.getCoordinateMax().getLatitude()-map.getCoordinates()[i].getLatitude())*widthScale;
//    		longitude= (map.getCoordinateMax().getLongitude()-map.getCoordinates()[i].getLongitude())*heightScale;
//    		g.setColor(Color.pink);
//    		g.drawOval((int)latitude,(int)longitude,5,5);
//    		g.fillOval((int)latitude,(int)longitude,5,5);
//    	}
//    	latitude= (map.getCoordinateMax().getLatitude()-map.getCoordinates()[map.getWareHouse()].getLatitude())*widthScale;
//		longitude= (map.getCoordinateMax().getLongitude()-map.getCoordinates()[map.getWareHouse()].getLongitude())*heightScale;
//    	g.setColor(Color.blue);
//		g.drawOval((int)latitude,(int)longitude,5,5);
//		g.fillOval((int)latitude,(int)longitude,5,5);
    }
    
    public void setMap(Map map) {
        this.map = map;
    }
    
    public Map getMap() {
        return map;
    }

   

}


