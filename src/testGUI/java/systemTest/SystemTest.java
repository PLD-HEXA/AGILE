package systemTest;

import controler.Controler;
import entities.Coordinate;
import entities.Itinerary;
import org.junit.BeforeClass;
import org.junit.Test;
import utils.Utils;

import javax.swing.*;
import java.awt.*;
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
    private void loadFiles() throws InterruptedException {
        // choose an XML file
        utils.loadXML("Load an xml plan", utils.XML_FOLDER + "moyenPlan.xml");
        utils.loadXML("Load deliveries", utils.XML_FOLDER + "dl-moyen-9.xml");
    }

    private void validateTour(String planFile, String deliveriesFile, int deliveryMenNumber) throws InterruptedException {
        Thread.sleep(1000);
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
        int deliveryMenNumberMin = deliveries.get(1).getGeneralPath().size();
        int deliveryMenNumberMax = deliveryMenNumberMin;
        for (Itinerary delivery : deliveries) {
            int deliveryNumber = delivery.getGeneralPath().size();
            deliveryMenNumberMin = Math.min(deliveryMenNumberMin, deliveryNumber);
            deliveryMenNumberMax = Math.max(deliveryMenNumberMax, deliveryNumber);
            utils.clickOnRightArrow(deliveryNumber + 1);
        }

        assertEquals(deliveryMenNumberMin, deliveryMenNumberMax, 1);

        Thread.sleep(100000);
    }
}
