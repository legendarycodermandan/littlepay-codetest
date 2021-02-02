package services.input;

import model.Tap;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InputReaderImpl implements InputReader {
    @Override
    public List<Tap> getTaps(String inputFilePath) {
        if (inputFilePath == null || inputFilePath.isBlank()) {
            throw new RuntimeException("Invalid file path supplied");
        }
        List<Tap> taps = readFile(inputFilePath);

        return taps;
    }

    private List<Tap> readFile(String inputFilePath) {
        List<Tap> records = new ArrayList<>();
        Tap tap;
        try (BufferedReader br = new BufferedReader(new FileReader(inputFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(", ");
                if (values[0].equals("ID")) {
                    continue;
                }
                tap = createTap(values);
                records.add(tap);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return records;
    }

    private Tap createTap(String[] data) {
        Tap tap = new Tap();
        tap.setId(Long.parseLong(data[0]));
        tap.setDateTimeUTC(getDate(data[1]));
        tap.setTapType(data[2]);
        tap.setStopId(data[3]);
        tap.setCompanyId(data[4]);
        tap.setBusId(data[5]);
        tap.setPan(data[6]);

        return tap;
    }

    private Date getDate(String dateString) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date date = new Date();

        try {
            date = format.parse(dateString.trim());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
