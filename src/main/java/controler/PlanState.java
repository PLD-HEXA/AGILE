package controler;

import java.io.File;
import java.util.ArrayList;
import javax.swing.JFileChooser;

import entities.DemandeDeLivraisons;
import view.MainWindow;

/**
 * The state after a plan has been loaded.
 *
 * @author PLD-HEXA-301
 */
public class PlanState extends DefaultState {

    @Override
    public void loadDeliveries(Controller controller, MainWindow mainWindow) {
        mainWindow.displayMessage("Load deliveries");
        mainWindow.displayMessage("Load plan");
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("/"));
        chooser.changeToParentDirectory();
        mainWindow.add(chooser);
        int returnValue = chooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = chooser.getSelectedFile();
            DemandeDeLivraisons ddl = controller.getParser().parseDelivery(selectedFile.toString());
            if (ddl != null) {
                mainWindow.getGraphicalView().setIndexToDelete(new ArrayList<>());
                mainWindow.getGraphicalView().getMap().setTabDeliveryPoints(new ArrayList<>());
                mainWindow.getGraphicalView().getMap().fillTabDeliveryPoint(ddl);
                if (mainWindow.getGraphicalView().getMap().getTabDeliveryPoints() != null) {
                    mainWindow.getGraphicalView().getMap().fillUnreachablePoints();
                    boolean invalidFile = false;
                    int i = 0;
                    int numberOfDeliveryPoints = mainWindow.getGraphicalView().getMap().getTabDeliveryPoints().size();
                    while (!invalidFile && i < numberOfDeliveryPoints) {
                        if (mainWindow.getGraphicalView().getMap().getUnreachablePoints().contains(mainWindow.getGraphicalView().getMap().getTabDeliveryPoints().get(i).getKey())
                                || mainWindow.getGraphicalView().getMap().getNonReturnPoints().contains(mainWindow.getGraphicalView().getMap().getTabDeliveryPoints().get(i).getKey())) {
                            invalidFile = true;
                        }
                        i++;
                    }
                    if (invalidFile) {
                        mainWindow.getGraphicalView().getMap().setWareHouse(null);
                        mainWindow.getGraphicalView().setItineraries(null);
                        mainWindow.getGraphicalView().setDeliveryPointIndex(null);
                        mainWindow.getGraphicalView().setItineraryIndex(null);
                        mainWindow.getGraphicalView().repaint();
                        mainWindow.getTextualView().setItineraries(null);
                        mainWindow.getTextualView().setDeliveryPointIndex(null);
                        mainWindow.getTextualView().setItineraryIndex(null);
                        mainWindow.getTextualView().displayListOfRounds();
                        mainWindow.getTextualView().revalidate();
                        mainWindow.getTextualView().repaint();
                        controller.setCurState(controller.planState);                        mainWindow.showError("The input xml file is invalid.");
                    } else {
                        mainWindow.getGraphicalView().setItineraries(null);
                        mainWindow.getGraphicalView().setDeliveryPointIndex(null);
                        mainWindow.getGraphicalView().setItineraryIndex(null);
                        mainWindow.getGraphicalView().repaint();
                        mainWindow.getTextualView().setItineraries(null);
                        mainWindow.getTextualView().setDeliveryPointIndex(null);
                        mainWindow.getTextualView().setItineraryIndex(null);
                        mainWindow.getTextualView().displayListOfRounds();
                        mainWindow.getTextualView().revalidate();
                        mainWindow.getTextualView().repaint();
                        controller.setCurState(controller.deliveriesState);
                    }
                } else {
                    mainWindow.getGraphicalView().setItineraries(null);
                    mainWindow.getGraphicalView().setDeliveryPointIndex(null);
                    mainWindow.getGraphicalView().setItineraryIndex(null);
                    mainWindow.getGraphicalView().repaint();
                    mainWindow.getTextualView().setItineraries(null);
                    mainWindow.getTextualView().setDeliveryPointIndex(null);
                    mainWindow.getTextualView().setItineraryIndex(null);
                    mainWindow.getTextualView().displayListOfRounds();
                    mainWindow.getTextualView().revalidate();
                    mainWindow.getTextualView().repaint();
                    controller.setCurState(controller.planState);
                    mainWindow.showError("The input xml file is invalid.");
                }
            } else {
                mainWindow.getGraphicalView().setItineraries(null);
                mainWindow.getGraphicalView().setDeliveryPointIndex(null);
                mainWindow.getGraphicalView().setItineraryIndex(null);
                mainWindow.getGraphicalView().repaint();
                mainWindow.getTextualView().setItineraries(null);
                mainWindow.getTextualView().setDeliveryPointIndex(null);
                mainWindow.getTextualView().setItineraryIndex(null);
                mainWindow.getTextualView().displayListOfRounds();
                mainWindow.getTextualView().revalidate();
                mainWindow.getTextualView().repaint();
                controller.setCurState(controller.planState);
                mainWindow.showError("The input xml file is invalid.");
            }
        }
    }
}
