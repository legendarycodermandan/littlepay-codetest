package services.charges;

import model.Trip;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

    @Test
    public void testListOfTrips() {
        List<Trip> trips = new ArrayList<>();
        Trip trip1 = new Trip();
        trip1.setFromStopId("Stop1");
        trip1.setToStopId("Stop2");

        Trip trip2 = new Trip();
        trip2.setFromStopId("Stop2");
        trip2.setToStopId("Stop3");

        Trip trip3 = new Trip();
        trip3.setFromStopId("Stop1");
        trip3.setToStopId("Stop3");

        trips.add(trip1);
        trips.add(trip2);
        trips.add(trip3);

        List<Trip> chargedTrips = chargeCalculator.calculateCharge(trips);

        assertTrue(chargedTrips.get(0).getChargeAmount().compareTo(new BigDecimal("3.25")) == 0);
        assertTrue(chargedTrips.get(1).getChargeAmount().compareTo(new BigDecimal("5.50")) == 0);
        assertTrue(chargedTrips.get(2).getChargeAmount().compareTo(new BigDecimal("7.30")) == 0);
    }

    @Test
    public void testMaxCostForIncompleteTrips() {
        BigDecimal stop1Cost = chargeCalculator.calculateMaxChargeForStop("Stop1");
        BigDecimal stop2Cost = chargeCalculator.calculateMaxChargeForStop("Stop2");
        BigDecimal stop3Cost = chargeCalculator.calculateMaxChargeForStop("Stop3");

        assertTrue(stop1Cost.compareTo(new BigDecimal("7.30")) == 0);
        assertTrue(stop2Cost.compareTo(new BigDecimal("5.50")) == 0);
        assertTrue(stop3Cost.compareTo(new BigDecimal("7.30")) == 0);
    }


}