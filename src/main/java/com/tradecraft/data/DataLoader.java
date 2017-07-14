package com.tradecraft.data;

import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DataLoader {

    public static List<Data> loadData(String fileName) {
        List<Data> dataList = new ArrayList<Data>();

        Path pathToFile = Paths.get(fileName);
        try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII)) {
            String line = br.readLine();
            while (line != null) {
                String[] attributes = line.split(",");
                Data entry = createDataObject(attributes);
                if(entry.getVolume() > 0) {
                    dataList.add(entry);
                }
                line = br.readLine();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return dataList;
    }

    private static Data createDataObject(String[] metadata) {
        DateTimeFormatter dtf = DateTimeFormat.forPattern("dd.MM.YYYY HH:mm:ss.SSS");
        DateTime dateTime = DateTime.parse(metadata[0], dtf);
        double open = Double.parseDouble(metadata[1]);
        double high = Double.parseDouble(metadata[2]);
        double low = Double.parseDouble(metadata[3]);
        double close = Double.parseDouble(metadata[4]);
        double volume = Double.parseDouble(metadata[5]);

        return new Data(dateTime, open, high, low, close, volume);
    }

}
