package controler;

import java.io.File;
import java.util.ArrayList;

import javax.swing.JFileChooser;

import entities.Map;
import entities.Network;

import java.awt.event.KeyEvent;
import javax.swing.JScrollBar;

import view.MainWindow;

/**
 * The default state of the window.
 *
 * @author PLD-HEXA-301
 */
public class DefaultState implements State {

    static final double minimalDistance = 0.0062;

    @Override
    public void loadPlan(Controller controller, MainWindow mainWindow) {
        mainWindow.displayMessage("Load plan");
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("/"));
        chooser.changeToParentDirectory();
        mainWindow.add(chooser);
        int returnValue = chooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = chooser.getSelectedFile();
            Network network = controller.getParser().parseCityPlan(selectedFile.toString());
            if (network != null) {
                Map map = new Map();
                map.fillMapIdAndCoordinate(network);
                if (map.getMapId() != null && map.getCoordinates() != null) {
                    map.fillGraph(network);
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
                        controller.setCurState(controller.planState);
                    } else {
                        mainWindow.showError("The content of the input xml file is invalid.");
                    }
                } else {
                    mainWindow.showError("The content of the input xml file is invalid.");
                }
            } else {
                mainWindow.showError("The input xml file is invalid");
            }
        }
    }

    @Override
    public void loadDeliveries(Controller controller, MainWindow mainWindow) {
    }

    @Override
    public void compute(Controller controller, MainWindow mainWindow) {

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
    public void mouseClick(Controller controller, MainWindow mainWindow, int x, int y) {
    }

    @Override
    public void mouseClick(Controller controller, MainWindow mainWindow, CmdList cmdList, int x, int y) {
    }

    @Override
    public void iconClick(Controller controller, MainWindow mainWindow, int x, int y) {
    }

    @Override
    public void clickDeleteButton(Controller controller) {
    }

    @Override
    public void clickAddButton(Controller controller) {
    }

    @Override
    public void keyPressed(Controller controller, MainWindow mainWindow, int keyCode) {
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

    public void reset(Controller controller, MainWindow mainWindow) {
        controller.addState.setOriginalPointNumber(0);
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
        controller.setCurState(controller.initState);
    }
}
