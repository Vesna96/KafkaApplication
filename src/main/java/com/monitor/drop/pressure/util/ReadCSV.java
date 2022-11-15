package com.monitor.drop.pressure.util;

import model.Influx;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ReadCSV {

    private List<Influx> influxlist;


    public List<Influx> readInfluxFromCSVFile() {

        List<Influx> influxList = new ArrayList<>();
        Path pathToFile = Paths.get("data/influx.csv");

        try {

            BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII);
            br.readLine();
            br.readLine();
            br.readLine();

            String line = br.readLine();

            while (line != null && !line.isEmpty()) {
                String[] attributes = line.split(",");



                Influx influx = createInflux(attributes);

                influxList.add(influx);

                line = br.readLine();
            }

        } catch (IOException ioe) {
            System.out.println("File is not available...");

        }

        return influxList;
    }



    private Influx createInflux(String[] metadata) {

        Influx influx = new Influx();

        influx.setTime(metadata[0]);
        influx.setValue(Double.parseDouble(metadata[1].trim()));
        influx.setDevice_identifier(metadata[2]);

        return influx;
    }
}
