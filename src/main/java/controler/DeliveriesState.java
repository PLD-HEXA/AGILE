package controler;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;

import entities.DemandeDeLivraisons;
import entities.Itinerary;
import view.MainWindow;

/**
 * The state after the deliveries have been loaded.
 *
 * @author PLD-HEXA-301
 */
public class DeliveriesState extends DefaultState {
    @Override
    public void compute(Controller controller, MainWindow mainWindow) {
        int numberOfDeliveryMen = (int) mainWindow.getInputView().getNumOfRounds().getValue();
        int numberOfDeliveries = mainWindow.getGraphicalView().getMap().getTabDeliveryPoints().size();
        boolean compute = true;
        if (numberOfDeliveryMen > numberOfDeliveries) {
            int userAgree = mainWindow.showInformationDelivery(
                    numberOfDeliveryMen - numberOfDeliveries +
                            " delivery men will have nothing to do, "
                            + "do you want to continue ?");
            if (userAgree != 0) {
                compute = false;
            }
        }
        if (compute) {
            List<Itinerary> itineraries = controller.getPathFinder().findPath(mainWindow.getGraphicalView().getMap(),
                    Integer.min(numberOfDeliveries, numberOfDeliveryMen));
            if (itineraries != null) {
                controller.getCmdList().reset();
                controller.addState.setOriginalPointNumber(0);
                controller.deleteState.setNumberDeliveryPointDeleted(0);
                mainWindow.getGraphicalView().setItineraries(itineraries);
                mainWindow.getGraphicalView().setDeliveryPointIndex(null);
                mainWindow.getGraphicalView().setItineraryIndex(null);
                mainWindow.getGraphicalView().repaint();
                mainWindow.getTextualView().setItineraries(itineraries);
                mainWindow.getTextualView().setDeliveryPointIndex(null);
                mainWindow.getTextualView().setItineraryIndex(null);
                mainWindow.getTextualView().displayListOfRounds();
                mainWindow.getTextualView().revalidate();
                mainWindow.getTextualView().repaint();
                mainWindow.requestFocus();
                controller.setCurState(controller.computeState);
            } else {
                mainWindow.showError("Error when calculating"
                        + " routes");
            }
        }
    }

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
                        mainWindow.getGraphicalView().getMap().setTabDeliveryPoints(new ArrayList<>());
                        mainWindow.getGraphicalView().getMap().setWareHouse(null);
                        mainWindow.showError("The input xml file is invalid.");
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
                    mainWindow.showError("The input xml file is invalid.");
                }
            } else {
                mainWindow.showError("The input xml file is invalid.");
            }
        }
    }
}
