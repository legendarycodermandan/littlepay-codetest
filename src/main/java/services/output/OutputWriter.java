package services.output;

import model.Trip;

import java.util.List;

public interface OutputWriter {
    public void writeOutput(List<Trip> trips, String outputFilePath);
}
