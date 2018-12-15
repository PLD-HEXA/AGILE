package controler;

import java.io.File;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import entities.Map;
import entities.Reseau;
import java.awt.event.KeyEvent;
import javax.swing.JScrollBar;
import view.MainWindow;

/**
 * The default state of the window.
 *
 * @author PLD-HEXA-301
 *
 */
public class DefaultState implements State {

    public static final double minimalDistance = 0.0062;

    @Override
    public void loadPlan(Controler controler, MainWindow mainWindow) {
        mainWindow.displayMessage("Load plan");
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("/"));
        chooser.changeToParentDirectory();
        mainWindow.add(chooser);
        int returnValue = chooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = chooser.getSelectedFile();
            // Ici rajouter l'appel a la methode qui traite l'xml
            Reseau reseau = controler.getParser().parseCityPlan(selectedFile.toString());
            if (reseau != null) {
                Map map = new Map();
                map.fillMapIdAndCoordinate(reseau);
                if (map.getMapId() != null && map.getCoordinates() != null) {
                    map.fillGraph(reseau);
                    if (map.getGraph() != null) {
                    	map.fillNonReturnPoints();
                        mainWindow.getTextualView().setItineraries(null);
                        mainWindow.getTextualView().setDeliveryPointIndex(null);
                        mainWindow.getTextualView().setItineraryIndex(null);
                        mainWindow.getTextualView().displayListOfRounds();
                        mainWindow.getTextualView().revalidate();
                        mainWindow.getTextualView().repaint();
                        mainWindow.getGraphicalView().setItineraries(null);
                        mainWindow.getGraphicalView().setMap(map);
                        mainWindow.getGraphicalView().setDeliveryPointIndex(null);
                        mainWindow.getGraphicalView().setItineraryIndex(null);
                        mainWindow.getGraphicalView().setIndexToDelete(new ArrayList<>());
                        mainWindow.getGraphicalView().repaint();
                        controler.setCurState(controler.planState);
                    } else {
                        mainWindow.showError("The content"
                                + " of the input xml file is invalid.");
                        // Le contenu du xml est incorrect
                        // Ici, due à un tronçon dont l'un des attributs à une valeur incorrecte
                    }
                } else {
                    mainWindow.showError("The content of"
                            + " the input xml file is invalid.");
                    // Le plan n'est pas valide (Ici, 
                    // car un attribut est incorrect ou bien aucune infos n'est valable)
                }
            } else {
                mainWindow.showError("The input xml file"
                        + " is invalid");
                // Le plan n'est pas valide (Ici, cas
                // ou l'extension est incorrecte, une balise non connue est ajoutée,
                // un attribut est rajoutée)
            }
        }
    }

    @Override
    public void loadDeliveries(Controler controler, MainWindow mainWindow) {
    }

    @Override
    public void compute(Controler controler, MainWindow mainWindow) {

    }

    @Override
    public void undo(CmdList cmdList) {
        cmdList.undo();
    }

    @Override
    public void redo(CmdList cmdList) {
        cmdList.redo();
    }

    @Override
    public void mouseClick(Controler controler, MainWindow mainWindow, int x, int y) {
    }

    @Override
    public void mouseClick(Controler controler, MainWindow mainWindow, CmdList cmdList, int x, int y) {
    }

    @Override
    public void iconeClick(Controler controler, MainWindow mainWindow, int x, int y) {
    }

    @Override
    public void clickDeleteButton(Controler controler) {
    }

    @Override
    public void clickAddButton(Controler controler) {

    }

   
     @Override
    public void keyPressed(Controler controler, MainWindow mainWindow, int keyCode) {

        if (keyCode == KeyEvent.VK_Q) {
            JScrollBar scrollBar = mainWindow.getGraphicalView().getScrollPane().getHorizontalScrollBar();
            if (scrollBar != null) {
                scrollBar.setValue(scrollBar.getValue()
                        - scrollBar.getBlockIncrement() * (int) mainWindow.getGraphicalView().getScale());
            }
        } else if (keyCode == KeyEvent.VK_S) {
            JScrollBar scrollBar = mainWindow.getGraphicalView().getScrollPane().getVerticalScrollBar();
            if (scrollBar != null) {
                scrollBar.setValue(scrollBar.getValue()
                        + scrollBar.getBlockIncrement() * (int) mainWindow.getGraphicalView().getScale());
            }
        } else if (keyCode == KeyEvent.VK_D) {
            JScrollBar scrollBar = mainWindow.getGraphicalView().getScrollPane().getHorizontalScrollBar();
            if (scrollBar != null) {
                scrollBar.setValue(scrollBar.getValue()
                        + scrollBar.getBlockIncrement() * (int) mainWindow.getGraphicalView().getScale());
            }
        } else if (keyCode == KeyEvent.VK_Z) {
            JScrollBar scrollBar = mainWindow.getGraphicalView().getScrollPane().getVerticalScrollBar();
            if (scrollBar != null) {
                scrollBar.setValue(scrollBar.getValue()
                        - scrollBar.getBlockIncrement() * (int) mainWindow.getGraphicalView().getScale());
            }
        }
        mainWindow.getGraphicalView().repaint();
    }
     
     public void reset(Controler controler, MainWindow mainWindow) {
    	 controler.addState.setNumberPointOriginal(0);
    	 mainWindow.getTextualView().setItineraries(null);
         mainWindow.getTextualView().setDeliveryPointIndex(null);
         mainWindow.getTextualView().setItineraryIndex(null);
         mainWindow.getTextualView().displayListOfRounds();
         mainWindow.getTextualView().revalidate();
         mainWindow.getTextualView().repaint();
         mainWindow.getGraphicalView().setIndexToDelete(new ArrayList<>());
         mainWindow.getGraphicalView().setItineraries(null);
         mainWindow.getGraphicalView().setMap(null);
         mainWindow.getGraphicalView().setDeliveryPointIndex(null);
         mainWindow.getGraphicalView().setItineraryIndex(null);
         mainWindow.getGraphicalView().repaint();
         controler.setCurState(controler.initState);
     }
}
