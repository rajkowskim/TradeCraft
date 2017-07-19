package com.tradecraft.data;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataLoader {

    public static Map<DateTime, List<DataPoint>> loadData(String fileName) {
        Map<DateTime, List<DataPoint>> result = new HashMap<DateTime, List<DataPoint>>();

        Path pathToFile = Paths.get(fileName);
        try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII)) {
            String line = br.readLine();
            while (line != null) {
                String[] attributes = line.split(",");
                DataPoint entry = createDataObject(attributes);

                DateTime dateOnly = entry.getDateTime().dayOfMonth().roundFloorCopy();
                List<DataPoint> dataPointList = result.getOrDefault(dateOnly, new ArrayList<DataPoint>());

                if (isEntryValid(entry)) {
                    dataPointList.add(entry);
                    result.put(dateOnly, dataPointList);
                }
                line = br.readLine();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return result;
    }

    private static boolean isEntryValid(DataPoint entry) {
        return entry.getVolume() > 0 &&
                entry.getDateTime().getDayOfWeek() != DateTimeConstants.SATURDAY &&
                entry.getDateTime().getDayOfWeek() != DateTimeConstants.SUNDAY;
    }

    private static DataPoint createDataObject(String[] metadata) {
        DateTimeFormatter dtf = DateTimeFormat.forPattern("dd.MM.YYYY HH:mm:ss.SSS");
        DateTime dateTime = DateTime.parse(metadata[0], dtf);
        double open = Double.parseDouble(metadata[1]);
        double high = Double.parseDouble(metadata[2]);
        double low = Double.parseDouble(metadata[3]);
        double close = Double.parseDouble(metadata[4]);
        double volume = Double.parseDouble(metadata[5]);

        return new DataPoint(dateTime, open, high, low, close, volume);
    }

}
