package services.charges;

import model.ChargeConfiguration;
import model.Trip;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ChargeCalculatorImpl implements ChargeCalculator {

    List<ChargeConfiguration> chargeConfigurations;

    public ChargeCalculatorImpl() {
        chargeConfigurations = new ArrayList<>();
        chargeConfigurations.add(new ChargeConfiguration("Stop1", "Stop2", new BigDecimal("3.25")));
        chargeConfigurations.add(new ChargeConfiguration("Stop2", "Stop3", new BigDecimal("5.50")));
        chargeConfigurations.add(new ChargeConfiguration("Stop1", "Stop3", new BigDecimal("7.30")));
    }

    @Override
    public BigDecimal calculateCharge(Trip trip) {
        BigDecimal tripCost = new BigDecimal(0);

        ChargeConfiguration chargeConfiguration = chargeConfigurations.stream().filter(c -> c.getStop1().equals(trip.getFromStopId()) && c.getStop2().equals(trip.getToStopId()) ||
                c.getStop2().equals(trip.getFromStopId()) && c.getStop1().equals(trip.getToStopId())).findFirst().orElse(null);

        if (chargeConfiguration == null) {
            throw new RuntimeException("Charge configuration not found");
        }

        tripCost = chargeConfiguration.getCost();

        return tripCost;
    }

    @Override
    public List<Trip> calculateCharge(List<Trip> trips) {
        List<Trip> clone = trips.stream().collect(Collectors.toList());
        Trip trip = null;
        for (int i = 0; i < trips.size(); i++) {
            trip = trips.get(i);
            trip.setChargeAmount(calculateCharge(trip));
        }

        return clone;
    }
}
