package services.matching;

import model.Tap;
import model.Trip;

import java.util.List;

public interface TapMatcher {
    public List<Trip> matchTaps(List<Tap> taps);
}
