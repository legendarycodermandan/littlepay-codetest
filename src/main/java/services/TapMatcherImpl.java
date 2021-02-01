package services;

import model.Tap;
import model.Trip;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TapMatcherImpl implements TapMatcher {
    @Override
    public List<Trip> matchTaps(List<Tap> taps) {
        long diff;
        Tap theTapOn = null;
        Trip trip = null;
        List<Trip> trips = new ArrayList<>();
        List<Tap> tapOns = taps.stream().filter(a -> a.getTapType().equals("ON")).collect(Collectors.toList());
        List<Tap> tapOffs = taps.stream().filter(a -> a.getTapType().equals("OFF")).collect(Collectors.toList());

        for (Tap theTapOff : tapOffs) {
            theTapOn = tapOns.stream().filter(t -> t.getPan().equals(theTapOff.getPan()) && t.getBusId().equals(theTapOff.getBusId())).findFirst().orElse(null);

            if (theTapOn != null) {
                //it has been matched
                trip = new Trip();
                trip.setStarted(theTapOn.getDateTimeUTC());
                trip.setFinished(theTapOff.getDateTimeUTC());
                trip.setDurationSecs(ChronoUnit.SECONDS.between(theTapOn.getDateTimeUTC().toInstant(), theTapOff.getDateTimeUTC().toInstant()));
                trip.setFromStopId(theTapOn.getStopId());
                trip.setToStopId(theTapOff.getStopId());
                trip.setCompanyId(theTapOn.getCompanyId());
                trip.setBusId(theTapOn.getBusId());
                trip.setPan(theTapOn.getPan());
                trip.setStatus(theTapOff.getStopId().equals(theTapOn.getStopId()) ? "CANCELLED" : "COMPLETED");

                trips.add(trip);

            }
            tapOns.remove(theTapOn);
        }

        if (!tapOns.isEmpty()) {
            trips.addAll(getIncompleteTrips(tapOns));
        }

        return trips;
    }

    private List<Trip> getIncompleteTrips(List<Tap> taps) {
        List<Trip> trips = new ArrayList<>();
        Trip trip = null;
        for (Tap tap : taps) {
            trip = new Trip();
            trip.setStarted(tap.getDateTimeUTC());
            trip.setFromStopId(tap.getStopId());
            trip.setCompanyId(tap.getCompanyId());
            trip.setBusId(tap.getBusId());
            trip.setPan(tap.getPan());
            trip.setStatus("INCOMPLETE");

            trips.add(trip);
        }

        return trips;
    }
}
