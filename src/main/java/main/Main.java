package main;

import javax.swing.UIManager;

import controler.Controller;

public class Main {
    public static void main(String[] args) {
    	// TODO why this try?
    	try {
    		UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
    	}catch(Exception ignored) { }

    	new Controller();
    }
}
