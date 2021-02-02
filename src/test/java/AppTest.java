import model.Tap;
import org.junit.jupiter.api.Test;
import services.input.InputReader;
import services.input.InputReaderImpl;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppTest {
    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    InputReader csvReader = new InputReaderImpl();

    @Test
    public void testInputReaderShouldReturnCorrectTaps() {
        Path resourceDirectory = Paths.get("src", "test", "resources", "input", "example.csv");
        String absolutePath = resourceDirectory.toFile().getAbsolutePath();

        List<Tap> taps = csvReader.getTaps(absolutePath);
        Tap firstTap = taps.get(0);
        Tap secondTap = taps.get(1);

        assertEquals(2, taps.size());
        assertEquals(1, firstTap.getId());
        assertEquals("22-01-2018 13:00:00", format.format(firstTap.getDateTimeUTC()));
        assertEquals("ON", firstTap.getTapType());
        assertEquals("Company1", firstTap.getCompanyId());
        assertEquals("Bus37", firstTap.getBusId());
        assertEquals("5500005555555559", firstTap.getPan());

        assertEquals(2, secondTap.getId());
        assertEquals("22-01-2018 13:05:00", format.format(secondTap.getDateTimeUTC()));
        assertEquals("OFF", secondTap.getTapType());
        assertEquals("Company1", secondTap.getCompanyId());
        assertEquals("Bus37", secondTap.getBusId());
        assertEquals("5500005555555559", secondTap.getPan());
    }
}