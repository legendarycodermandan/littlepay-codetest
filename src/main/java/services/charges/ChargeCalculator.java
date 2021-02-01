package services.charges;

import model.Trip;

import java.math.BigDecimal;
import java.util.List;

public interface ChargeCalculator {
    public BigDecimal calculateCharge(Trip trip);

    public BigDecimal calculateMaxChargeForStop(String stopId);

    public List<Trip> calculateCharge(List<Trip> trips);
}
