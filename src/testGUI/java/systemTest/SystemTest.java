package systemTest;

import controler.Controler;
import entities.Coordinate;
import entities.Itinerary;
import org.junit.BeforeClass;
import org.junit.Test;
import utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.List;

import static org.junit.Assert.*;

public class SystemTest {
    private static Controler controler;
    private static Utils utils;

    @BeforeClass
    public static void launchGUI() throws AWTException {
        controler = new Controler();
        utils = new Utils(controler);
    }

    @Test
    public void oneDeliveryManSmallPanSystemTest() throws InterruptedException {
        validateTour("petitPlan.xml", "dl-petit-3.xml", 1);
    }

    @Test
    public void threeDeliveryMenSmallPlanSystemTest() throws InterruptedException {
        validateTour("petitPlan.xml", "dl-petit-3.xml", 3);
    }

    @Test
    public void threeDeliveryMediumPlanSystemTest() throws InterruptedException {
        validateTour("moyenPlan.xml", "dl-moyen-9.xml", 3);
    }

    @Test
    public void sixDeliveryMediumPlanSystemTest() throws InterruptedException {
        validateTour("moyenPlan.xml", "dl-moyen-9.xml", 6);
    }

    /**
     * Choose and load an XML plan and deliveries
     * @throws InterruptedException
     */
    @Test
    public void loadFiles() throws InterruptedException {
        Thread.sleep(1000);
        utils.setInputViewOrigin(controler.getMainWindow().getInputView());
        utils.setGraphicalViewOrigin(controler.getMainWindow().getGraphicalView());

        // choose an XML file
        utils.loadXML("Load an xml plan", utils.XML_FOLDER + "moyenPlan.xml");
        utils.loadXML("Load deliveries", utils.XML_FOLDER + "dl-moyen-9.xml");

        Thread.sleep(100000);
    }

    @Test
    public void launchDemo() throws InterruptedException {
        Component compute = utils.getButton("Compute");
        Component deleteButton = utils.getButton("Delete");
        Component addButton = utils.getButton("Add");
        Component undoButton = utils.getButton("Undo");
        Component redoButton = utils.getButton("Redo");

        assertNotNull(compute);
        assertNotNull(deleteButton);
        assertNotNull(addButton);
        assertNotNull(undoButton);
        assertNotNull(redoButton);

        Thread.sleep(1000);
        utils.setInputViewOrigin(controler.getMainWindow().getInputView());
        utils.setGraphicalViewOrigin(controler.getMainWindow().getGraphicalView());

        // choose an XML file
        utils.loadXML("Load an xml plan", utils.XML_FOLDER + "moyenPlan.xml");
        utils.loadXML("Load deliveries", utils.XML_FOLDER + "dl-moyen-9.xml");

        // define the number of delivery men
        JSpinner deliveryMenNumberJSpinner = (JSpinner) utils.getButton("Number of delivery men");
        assertNotNull(deliveryMenNumberJSpinner);
        deliveryMenNumberJSpinner.setValue(3);
        utils.clickOnComponent(deliveryMenNumberJSpinner);

        // compute by pressing the correct button
        utils.clickOnComponent(compute);

        Thread.sleep(1000);

        // click on the first delivery
        List<Itinerary> deliveries = controler.getMainWindow().getGraphicalView().getItineraries();
        Coordinate coordinate = deliveries.get(0).getGeneralPath().get(0).getCoordinate();
        double[] positions = controler.getMainWindow().getGraphicalView().getCoordinatePosition(coordinate);
        utils.clickOnCoordinate(positions[0], positions[1]);
        Thread.sleep(1000);

        // navigate through the deliveries
        utils.goThroughDeliveries();
        Thread.sleep(1000);

        // click on delete button
        utils.clickOnComponent(deleteButton);
        Thread.sleep(500);

        // skip pop-up
        utils.getRobot().keyPress(KeyEvent.VK_ENTER);
        Thread.sleep(500);

        // select and click on the point to delete
        Coordinate lastCoordinate = deliveries.get(0).getGeneralPath().get(Math.max(0, deliveries.size() - 1)).getCoordinate();
        positions = controler.getMainWindow().getGraphicalView().getCoordinatePosition(lastCoordinate);
        utils.clickOnCoordinate(positions[0], positions[1]);
        Thread.sleep(1000);

        // skip pop-up
        utils.getRobot().keyPress(KeyEvent.VK_ENTER);
        Thread.sleep(1000);

        // click on delete button
        utils.clickOnComponent(addButton);
        Thread.sleep(1000);

        // define the duration of the new delivery point
        utils.getRobot().keyPress('1');
        utils.getRobot().keyPress('0');
        Thread.sleep(1000);
        utils.getRobot().keyPress(KeyEvent.VK_ENTER);
        Thread.sleep(1000);

        // go to the position of the new delivery point and click on it
        utils.getRobot().mouseMove(700, 700);
        utils.getRobot().mousePress(InputEvent.BUTTON1_MASK);
        utils.getRobot().mouseRelease(InputEvent.BUTTON1_MASK);
        Thread.sleep(1000);
        utils.getRobot().keyPress(KeyEvent.VK_ENTER);
        Thread.sleep(1000);

        // make one undo
        utils.clickOnComponent(undoButton);
        Thread.sleep(1000);

        // make one undo
        utils.clickOnComponent(undoButton);
        Thread.sleep(1000);

        // make one redo
        utils.clickOnComponent(redoButton);
        Thread.sleep(1000);
        utils.getRobot().keyPress(KeyEvent.VK_ENTER);
        Thread.sleep(1000);

        // choose an XML file
        utils.loadXML("Load an xml plan", utils.XML_FOLDER + "grandPlan.xml");
        utils.loadXML("Load deliveries", utils.XML_FOLDER + "dl-grand-20.xml");

        Thread.sleep(100000);
    }

    private void validateTour(String planFile, String deliveriesFile, int deliveryMenNumber) throws InterruptedException {
        Thread.sleep(1000);

        // define the origin
        utils.setInputViewOrigin(controler.getMainWindow().getInputView());
        utils.setGraphicalViewOrigin(controler.getMainWindow().getGraphicalView());

        // choose an XML file
        utils.loadXML("Load an xml plan", utils.XML_FOLDER + planFile);
        utils.loadXML("Load deliveries", utils.XML_FOLDER + deliveriesFile);

        // define the number of delivery men
        JSpinner deliveryMenNumberJSpinner = (JSpinner) utils.getButton("Number of delivery men");
        assertNotNull(deliveryMenNumberJSpinner);
        deliveryMenNumberJSpinner.setValue(deliveryMenNumber);
        utils.clickOnComponent(deliveryMenNumberJSpinner);

        // compute by pressing the correct button
        Component compute = utils.getButton("Compute");
        assertNotNull(compute);
        utils.clickOnComponent(compute);

        Thread.sleep(1000);

        // check if there is a correct number of delivery tours
        List<Itinerary> deliveries = controler.getMainWindow().getGraphicalView().getItineraries();
        assertEquals(deliveryMenNumber, deliveries.size());

        // click on the first delivery
        Coordinate coordinate = deliveries.get(0).getGeneralPath().get(0).getCoordinate();
        double[] positions = controler.getMainWindow().getGraphicalView().getCoordinatePosition(coordinate);
        utils.clickOnCoordinate(positions[0], positions[1]);

        // check if there is the same number of delivery in each itinerary
        int deliveryMenNumberMin = deliveries.get(0).getGeneralPath().size();
        int deliveryMenNumberMax = deliveryMenNumberMin;
        for (Itinerary delivery : deliveries) {
            int deliveryNumber = delivery.getGeneralPath().size();
            deliveryMenNumberMin = Math.min(deliveryMenNumberMin, deliveryNumber);
            deliveryMenNumberMax = Math.max(deliveryMenNumberMax, deliveryNumber);
            utils.clickOnRightArrow(deliveryNumber + 1);
        }

        assertEquals(deliveryMenNumberMin, deliveryMenNumberMax, 1);

        // check if the response is correct
        Thread.sleep(3000);
    }
}
