package com.monitor.drop.pressure.service;

import com.monitor.drop.pressure.kafka.producer.PressureDetectionProducer;
import org.springframework.stereotype.Service;

@Service
public class PressureDetectionService {

    PressureDetectionProducer pressureDetectionProducer;

    public PressureDetectionService(PressureDetectionProducer pressureDetectionProducer) {
        this.pressureDetectionProducer = pressureDetectionProducer;
    }

    public void send() throws InterruptedException {
        pressureDetectionProducer.sendData();
    }





}
