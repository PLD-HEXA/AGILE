package main;

import javax.swing.UIManager;

import controler.Controller;

public class Main {
    public static void main(String[] args) {
    	//Choosing a theme for the graphical design of the interface
    	try {
    		UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
    	}catch(Exception ignored) { }

    	new Controller();
    }
}
