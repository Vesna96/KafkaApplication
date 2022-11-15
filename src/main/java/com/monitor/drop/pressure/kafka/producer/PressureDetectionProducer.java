package com.monitor.drop.pressure.kafka.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.monitor.drop.pressure.util.ReadCSV;
import model.Influx;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PressureDetectionProducer {
    private final KafkaTemplate<String, String> pressureDetectionProducer;

    public PressureDetectionProducer(KafkaTemplate<String, String> pressureDetectionProducer) {
        this.pressureDetectionProducer = pressureDetectionProducer;
    }
    public void sendData() {

        System.out.println("Producer has been created...Start sending records ");

        ObjectMapper om = new ObjectMapper();

        ReadCSV readCSV = new ReadCSV();

        List<Influx> influxList = readCSV.readInfluxFromCSVFile();

        for (Influx influx : influxList) {

            String message;

            try {

                message = om.writeValueAsString(influx);

                System.out.println("Sending " + message);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }



            pressureDetectionProducer.send(new ProducerRecord<String, String>("influxNew-json", message));

        }

    }
}
