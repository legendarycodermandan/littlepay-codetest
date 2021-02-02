import model.Tap;
import model.Trip;
import services.*;
import services.charges.ChargeCalculator;
import services.charges.ChargeCalculatorImpl;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class App {

    public static void main(String[] args) {

        if (args.length < 2) {
            System.out.println("The source file for taps and an output destination is required");
            System.exit(1);
        }

        String sourceArg = args[0];
        String targetArg = args[1];

        if (!isSourceValid(sourceArg)) {
            System.out.println("A valid source file for taps is required");
            System.exit(1);
        }

        if (!isDestinationValid(targetArg)) {
            System.out.println("A valid destination file for trips is required");
            System.exit(1);
        }


//        String targetArg = "/home/danny/DEL/t.csv";

        InputReader csvReader = new InputReaderImpl();
        TapMatcher tapMatcher = new TapMatcherImpl();
        ChargeCalculator chargeCalculator = new ChargeCalculatorImpl();
        OutputWriter outputWriter = new OutputWriterImpl();

        List<Tap> taps = csvReader.getTaps(sourceArg);
        List<Trip> trips = tapMatcher.matchTaps(taps);
        List<Trip> chargedTrips = chargeCalculator.calculateCharge(trips);

        System.out.println("*************************************************");
        chargedTrips.forEach(t -> System.out.println(t));
        System.out.println("*************************************************");

        outputWriter.writeOutput(chargedTrips, targetArg);

    }

    private static boolean isSourceValid(String source) {
        Path path = Paths.get(source);
        return Files.exists(path) ? true : false;
    }

    private static boolean isDestinationValid(String destination) {
        Path path = Paths.get(destination);
        System.out.println("******************************");
        System.out.println("path.getParent(): " + path.getParent());
        System.out.println("******************************");

        return Files.exists(path.getParent()) ? true : false;
    }
}
