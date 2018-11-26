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
	
	public GraphicalView( MainWindow mainWindow ){
		super();
		//plan.addObserver(this); 
		
		height=800;
		width=800;
		setLayout(null);
		setBackground(Color.black);
		setSize(width, height);
		mainWindow.getContentPane().add(this);
		
		
	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.red);
		if(map!=null) {
			double longMax=map.getCoordinateMax().getLongitude();
			double latMax=map.getCoordinateMax().getLatitude();
			heightScale =height/(longMax-map.getCoordinateMin().getLongitude());
			widthScale =width/(latMax-map.getCoordinateMin().getLatitude());
			for(int i =0;i<1;i++) {
				int numberOfSuccessors = map.getGraph().get(i).size();//taille de la ième liste de segments dans graph
				for(int j=0;j<numberOfSuccessors;j++) {//pour chaque successeur
					Coordinate curSuccessor= map.getCoordinates()[map.getMapId().get((int)map.getGraph().get(i).get(j).getDestId())];
					g.drawLine((int)((latMax-map.getCoordinates()[i].getLatitude())*widthScale), (int)((longMax-map.getCoordinates()[i].getLongitude())*heightScale),
							(int)((latMax-curSuccessor.getLatitude())*widthScale), (int)((longMax-curSuccessor.getLongitude())*heightScale));
				}						
			}
		}
		this.g = g;
	}
	
	public void drawPlan(Map map) {
		this.map = map;
		double longMax=map.getCoordinateMax().getLongitude();
		System.out.println("longMax "+longMax);
		double latMax=map.getCoordinateMax().getLatitude();
		System.out.println("latMax "+latMax);
		heightScale =height/(longMax-map.getCoordinateMin().getLongitude());
		System.out.println("heightScale "+heightScale);
		widthScale =width/(latMax-map.getCoordinateMin().getLatitude());
		System.out.println("widthScale "+widthScale);
		for(int i =0;i<1;i++) {
			int numberOfSuccessors = map.getGraph().get(i).size();//taille de la ième liste de segments dans graph
			for(int j=0;j<numberOfSuccessors;j++) {//pour chaque successeur
				System.out.println("destId "+ map.getGraph().get(i).get(j).getDestId());
//				System.out.printlmap.getMapId().get((int)map.getGraph().get(i).get(j).getDestId());
				Coordinate curSuccessor= map.getCoordinates()[map.getMapId().get((int)map.getGraph().get(i).get(j).getDestId())];
				//Coordonee de l'index du successeur
				System.out.println("Cursuccessor "+curSuccessor);
				System.out.println("Origin "+map.getCoordinates()[i]);
//				g=this.getGraphics();
//				g.setColor(Color.red);
				System.out.println("x1 = "+(int)((latMax-map.getCoordinates()[i].getLatitude())*widthScale));
				System.out.println("y1 = "+(int)((longMax-map.getCoordinates()[i].getLongitude())*heightScale));
				System.out.println("x2 = "+(int)((latMax-curSuccessor.getLatitude())*widthScale));
				System.out.println("y2 = "+(int)((longMax-curSuccessor.getLongitude())*heightScale));
				
				g.drawLine((int)((latMax-map.getCoordinates()[i].getLatitude())*widthScale), (int)((longMax-map.getCoordinates()[i].getLongitude())*heightScale),
						(int)((latMax-curSuccessor.getLatitude())*widthScale), (int)((longMax-curSuccessor.getLongitude())*heightScale));
			}		
			
		}
	}
	
}
