package model;

import java.math.BigDecimal;

public class ChargeConfiguration {
    String stop1;
    String stop2;
    BigDecimal cost;

    public ChargeConfiguration() {

    }

    public ChargeConfiguration(String stop1, String stop2, BigDecimal cost) {
        this.stop1 = stop1;
        this.stop2 = stop2;
        this.cost = cost;
    }

    public String getStop1() {
        return stop1;
    }

    public void setStop1(String stop1) {
        this.stop1 = stop1;
    }

    public String getStop2() {
        return stop2;
    }

    public void setStop2(String stop2) {
        this.stop2 = stop2;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }
}
