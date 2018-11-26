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

        height = 800;
        width = 800;
        setLayout(null);
        setBackground(Color.black);
        setSize(width, height);
        mainWindow.getContentPane().add(this);

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.red);
        if (map != null) {
            drawPlan(g);
        }
        this.g = g;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public void drawPlan(Graphics g) {
        double longMax = map.getCoordinateMax().getLongitude();
        double latMax = map.getCoordinateMax().getLatitude();
        heightScale = height / (longMax - map.getCoordinateMin().getLongitude());
        widthScale = width / (latMax - map.getCoordinateMin().getLatitude());
        for (int i = 0; i < 1; i++) {
            int numberOfSuccessors = map.getGraph().get(i).size();//taille de la ième liste de segments dans graph
            for (int j = 0; j < numberOfSuccessors; j++) {//pour chaque successeur
                Coordinate curSuccessor = map.getCoordinates()[map.getMapId().get((int) map.getGraph().get(i).get(j).getDestId())];
                g.drawLine((int) ((latMax - map.getCoordinates()[i].getLatitude()) * widthScale), (int) ((longMax - map.getCoordinates()[i].getLongitude()) * heightScale),
                        (int) ((latMax - curSuccessor.getLatitude()) * widthScale), (int) ((longMax - curSuccessor.getLongitude()) * heightScale));
            }
        }
    }

   

}
