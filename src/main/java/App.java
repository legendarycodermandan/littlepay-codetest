import model.Tap;
import model.Trip;
import services.InputReader;
import services.InputReaderImpl;
import services.TapMatcher;
import services.TapMatcherImpl;
import services.charges.ChargeCalculator;
import services.charges.ChargeCalculatorImpl;

import java.util.List;

public class App {

//    InputReader csvReader = new InputReaderImpl();
//    TapMatcher tapMatcher = new TapMatcherImpl();

    public static void main(String[] args) {
        System.out.println("Argument count: " + args.length);


        if (args.length == 0) {
            System.out.println("The source file for taps is required");
            System.exit(1);
        }

        String sourceArg = args[0];
//        String targetArg = args[1];

        InputReader csvReader = new InputReaderImpl();
        TapMatcher tapMatcher = new TapMatcherImpl();
        ChargeCalculator chargeCalculator = new ChargeCalculatorImpl();

        List<Tap> taps = csvReader.getTaps(sourceArg);
        List<Trip> trips = tapMatcher.matchTaps(taps);
        List<Trip> chargedTrips = chargeCalculator.calculateCharge(trips);

        System.out.println("*************************************************");
        chargedTrips.forEach(t -> System.out.println(t));
        System.out.println("*************************************************");

    }
}
