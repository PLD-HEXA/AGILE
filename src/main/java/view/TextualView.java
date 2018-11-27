package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

public class TextualView extends JPanel {
	
	public TextualView(MainWindow mainWindow) {
		super();
        setLayout(new BorderLayout());
        setBackground(Color.pink);
        JLabel label=new JLabel("         Delivery rounds :       ");
        Font f = new Font("TimesRoman",Font.BOLD,25);
        label.setFont(f);
        this.add(label, BorderLayout.NORTH);
        ImageIcon timer = new ImageIcon("C:/Users/asus/Downloads/timer.png");
        ImageIcon blackBicycle = new ImageIcon("C:/Users/asus/Downloads/bicycle.jpg");
        ImageIcon redBicycle = new ImageIcon("C:/Users/asus/Downloads/redbicycle.png");
		DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
		renderer.setLeafIcon(timer);
		renderer.setClosedIcon(blackBicycle);
		renderer.setOpenIcon(redBicycle);
        DefaultMutableTreeNode rounds = new DefaultMutableTreeNode("Rounds");
        DefaultMutableTreeNode round1 = new DefaultMutableTreeNode("1st delivery round");
        DefaultMutableTreeNode stop11 = new DefaultMutableTreeNode("1st stop");
        DefaultMutableTreeNode departure1 = new DefaultMutableTreeNode("10h15");
        DefaultMutableTreeNode arrival1 = new DefaultMutableTreeNode("10h30");
        DefaultMutableTreeNode duration1 = new DefaultMutableTreeNode("15min");
        rounds.add(round1);
        round1.add(stop11);
        stop11.add(departure1);
        stop11.add(arrival1);
        stop11.add(duration1);
        DefaultMutableTreeNode round2 = new DefaultMutableTreeNode("2nd delivery round");
        DefaultMutableTreeNode stop21 = new DefaultMutableTreeNode("1st stop");
        DefaultMutableTreeNode departure2 = new DefaultMutableTreeNode("10h25");
        DefaultMutableTreeNode arrival2 = new DefaultMutableTreeNode("10h30");
        DefaultMutableTreeNode duration2 = new DefaultMutableTreeNode("5min");
        rounds.add(round2);
        round2.add(stop21);
        stop21.add(departure2);
        stop21.add(arrival2);
        stop21.add(duration2);
		JTree listOfRounds=new JTree(rounds);
		final Font currentFont = listOfRounds.getFont();
		final Font bigFont = new Font(currentFont.getName(), currentFont.getStyle(), currentFont.getSize() + 10);
		listOfRounds.setFont(bigFont);
		listOfRounds.setCellRenderer(renderer);
		this.add(listOfRounds, BorderLayout.CENTER);
	}

}
