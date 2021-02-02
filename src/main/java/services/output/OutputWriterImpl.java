package services.output;

import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import model.Trip;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class OutputWriterImpl implements OutputWriter {
    @Override
    public void writeOutput(List<Trip> trips, String outputFilePath) {
        try {
            FileWriter writer = new FileWriter(outputFilePath);

            ColumnPositionMappingStrategy mappingStrategy = new ColumnPositionMappingStrategy();
            mappingStrategy.setType(Trip.class);

            String[] columns = new String[]
                    {"started", "finished", "durationSecs", "fromStopId", "toStopId", "chargeAmount", "companyId", "busId", "pan", "status"};
            mappingStrategy.setColumnMapping(columns);

            StatefulBeanToCsvBuilder<Trip> builder = new StatefulBeanToCsvBuilder(writer);
            StatefulBeanToCsv beanWriter = builder.withMappingStrategy(mappingStrategy).build();
            beanWriter.write(trips);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvRequiredFieldEmptyException e) {
            e.printStackTrace();
        } catch (CsvDataTypeMismatchException e) {
            e.printStackTrace();
        }
    }
}
