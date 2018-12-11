package systemTest;

import controler.Controler;
import entities.Itinerary;
import org.junit.BeforeClass;
import org.junit.Test;
import utils.Utils;

import javax.swing.*;
import java.awt.*;
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
        utils.setOrigin(controler.getMainWindow().getInputView());
        validateTour("petitPlan.xml", "dl-petit-3.xml", 1);
    }

    @Test
    public void threeDeliveryMenSmallPlanSystemTest() throws InterruptedException {
        utils.setOrigin(controler.getMainWindow().getInputView());
        validateTour("petitPlan.xml", "dl-petit-3.xml", 3);
    }

    @Test
    public void threeDeliveryMediumPlanSystemTest() throws InterruptedException {
        utils.setOrigin(controler.getMainWindow().getInputView());
        validateTour("moyenPlan.xml", "dl-moyen-9.xml", 3);
    }

    @Test
    public void sixDeliveryMediumPlanSystemTest() throws InterruptedException {
        utils.setOrigin(controler.getMainWindow().getInputView());
        validateTour("moyenPlan.xml", "dl-moyen-9.xml", 6);
    }

    private void validateTour(String planFile, String deliveriesFile, int deliveryMenNumber) throws InterruptedException {
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

        // check if there is the same number of delivery in each itinerary
        int deliveryMenNumberMin = deliveries.get(0).getGeneralPath().size();
        int deliveryMenNumberMax = deliveryMenNumberMin;
        for (Itinerary delivery : deliveries) {
            deliveryMenNumberMin = Math.min(deliveryMenNumberMin, delivery.getGeneralPath().size());
            deliveryMenNumberMax = Math.max(deliveryMenNumberMax, delivery.getGeneralPath().size());
        }
        assertEquals(deliveryMenNumberMin, deliveryMenNumberMax, 1);

        // check if the response is correct
        Thread.sleep(100000);
    }
}
