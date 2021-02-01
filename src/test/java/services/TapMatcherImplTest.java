package services;

import model.Tap;
import model.Trip;
import org.junit.jupiter.api.Test;

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
//        assertEquals("", theTrip.getChargeAmount());
        assertEquals("Company1", theTrip.getCompanyId());
        assertEquals("Bus37", theTrip.getBusId());
        assertEquals("5500005555555559", theTrip.getPan());
        assertEquals("COMPLETED", theTrip.getStatus());


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
//        assertEquals("", theTrip.getChargeAmount());
        assertEquals("Company1", theTrip.getCompanyId());
        assertEquals("Bus37", theTrip.getBusId());
        assertEquals("5500005555555559", theTrip.getPan());
        assertEquals("CANCELLED", theTrip.getStatus());


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
//        assertEquals("", theTrip.getChargeAmount());
        assertEquals("Company1", theTrip.getCompanyId());
        assertEquals("Bus37", theTrip.getBusId());
        assertEquals("5500005555555559", theTrip.getPan());
        assertEquals("INCOMPLETE", theTrip.getStatus());
    }

}