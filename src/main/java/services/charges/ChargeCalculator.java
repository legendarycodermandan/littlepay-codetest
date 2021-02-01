package services.charges;

import model.Trip;

import java.math.BigDecimal;

public interface ChargeCalculator {
    public BigDecimal calculateCharge(Trip trip);
}
