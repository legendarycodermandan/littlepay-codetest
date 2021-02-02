package services;

import model.Tap;
import model.Trip;
import model.TripStatus;
import org.junit.jupiter.api.Test;
import services.charges.ChargeCalculator;
import services.charges.ChargeCalculatorImpl;
import services.input.InputReader;
import services.input.InputReaderImpl;
import services.matching.TapMatcher;
import services.matching.TapMatcherImpl;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TapMatcherImplTest {
    InputReader csvReader = new InputReaderImpl();
    TapMatcher tapMatcher = new TapMatcherImpl();
    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    @Test
    public void testMatcher() {
        Path resourceDirectory = Paths.get("src", "test", "resources", "input", "singleCompletedTrip.csv");
        String absolutePath = resourceDirectory.toFile().getAbsolutePath();

        List<Tap> taps = csvReader.getTaps(absolutePath);

        List<Trip> trips = tapMatcher.matchTaps(taps);
        assertEquals(1, trips.size());

        Trip theTrip = trips.get(0);
        assertEquals("22-01-2018 13:00:00", format.format(theTrip.getStarted()));
        assertEquals("22-01-2018 13:05:00", format.format(theTrip.getFinished()));
        assertEquals(300, theTrip.getDurationSecs());
        assertEquals("Stop1", theTrip.getFromStopId());
        assertEquals("Stop2", theTrip.getToStopId());
        assertEquals("Company1", theTrip.getCompanyId());
        assertEquals("Bus37", theTrip.getBusId());
        assertEquals("5500005555555559", theTrip.getPan());
        assertEquals(TripStatus.COMPLETED, theTrip.getStatus());


    }

    @Test
    public void testMatcherWhenStopsAreTheSameThenStatusShouldBeCancelled() {
        Path resourceDirectory = Paths.get("src", "test", "resources", "input", "singleCancelledTrip.csv");
        String absolutePath = resourceDirectory.toFile().getAbsolutePath();

        List<Tap> taps = csvReader.getTaps(absolutePath);

        List<Trip> trips = tapMatcher.matchTaps(taps);
        assertEquals(1, trips.size());

        Trip theTrip = trips.get(0);
        assertEquals("22-01-2018 13:00:00", format.format(theTrip.getStarted()));
        assertEquals("22-01-2018 13:01:00", format.format(theTrip.getFinished()));
        assertEquals(60, theTrip.getDurationSecs());
        assertEquals("Stop2", theTrip.getFromStopId());
        assertEquals("Stop2", theTrip.getToStopId());
        assertEquals("Company1", theTrip.getCompanyId());
        assertEquals("Bus37", theTrip.getBusId());
        assertEquals("5500005555555559", theTrip.getPan());
        assertEquals(TripStatus.CANCELLED, theTrip.getStatus());


    }

    @Test
    public void testMatcherWhenNoTapOff() {
        Path resourceDirectory = Paths.get("src", "test", "resources", "input", "singleIncompleteTrip.csv");
        String absolutePath = resourceDirectory.toFile().getAbsolutePath();

        List<Tap> taps = csvReader.getTaps(absolutePath);

        List<Trip> trips = tapMatcher.matchTaps(taps);
        assertEquals(1, trips.size());

        Trip theTrip = trips.get(0);
        assertEquals("22-01-2018 13:00:00", format.format(theTrip.getStarted()));
        assertNull(theTrip.getFinished());
        assertNull(theTrip.getDurationSecs());
        assertEquals("Stop1", theTrip.getFromStopId());
        assertNull(theTrip.getToStopId());
        assertEquals("Company1", theTrip.getCompanyId());
        assertEquals("Bus37", theTrip.getBusId());
        assertEquals("5500005555555559", theTrip.getPan());
        assertEquals(TripStatus.INCOMPLETE, theTrip.getStatus());
    }

    @Test
    public void testMatcherCombined() {
        ChargeCalculator chargeCalculator = new ChargeCalculatorImpl();
        Path resourceDirectory = Paths.get("src", "test", "resources", "input", "tripsForAllCases.csv");
        String absolutePath = resourceDirectory.toFile().getAbsolutePath();

        List<Tap> taps = csvReader.getTaps(absolutePath);

        List<Trip> trips = tapMatcher.matchTaps(taps);
        List<Trip> chargedTrips = chargeCalculator.calculateCharge(trips);

        assertEquals(5, trips.size());

        Long completedTripCount = trips.stream().filter(t -> t.getStatus() == TripStatus.COMPLETED).count();
        Long inCompleteTripCount = trips.stream().filter(t -> t.getStatus() == TripStatus.INCOMPLETE).count();
        Long cancelledTripCount = trips.stream().filter(t -> t.getStatus() == TripStatus.CANCELLED).count();

        assertEquals(3, completedTripCount);
        assertEquals(1, inCompleteTripCount);
        assertEquals(1, cancelledTripCount);
    }

}