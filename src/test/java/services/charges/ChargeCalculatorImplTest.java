package services.charges;

import model.Trip;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ChargeCalculatorImplTest {

    ChargeCalculator chargeCalculator = new ChargeCalculatorImpl();

    @Test
    public void testChargeCalculatorCalculatesEitherDirection() {
        Trip trip = new Trip();
        trip.setFromStopId("Stop1");
        trip.setToStopId("Stop2");

        BigDecimal tripCost = chargeCalculator.calculateCharge(trip);
        assertTrue(new BigDecimal("3.25").compareTo(tripCost) == 0);

        trip.setFromStopId("Stop2");
        trip.setToStopId("Stop1");
        tripCost = chargeCalculator.calculateCharge(trip);

        assertTrue(new BigDecimal("3.25").compareTo(tripCost) == 0);
    }

    @Test
    public void testSomething() {

        Trip trip = new Trip();
        trip.setFromStopId("Stop1");
        trip.setToStopId("Stop2");

        BigDecimal tripCost = chargeCalculator.calculateCharge(trip);
        assertTrue(tripCost.compareTo(new BigDecimal("3.25")) == 0);

        trip.setFromStopId("Stop2");
        trip.setToStopId("Stop3");
        tripCost = chargeCalculator.calculateCharge(trip);

        assertTrue(tripCost.compareTo(new BigDecimal("5.50")) == 0);

        trip.setFromStopId("Stop1");
        trip.setToStopId("Stop3");
        tripCost = chargeCalculator.calculateCharge(trip);

        assertTrue(tripCost.compareTo(new BigDecimal("7.30")) == 0, tripCost.toPlainString());

    }


}