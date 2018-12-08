package main;

import javax.swing.UIManager;

import controler.Controler;

public class Main {
    public static void main(String[] args) {
    	
    	try {
    		UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
    	}catch(Exception e) {
    		
    	}
        Controler controler=new Controler();
    }
}
