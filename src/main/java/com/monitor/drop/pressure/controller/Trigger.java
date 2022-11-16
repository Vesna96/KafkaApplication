package com.monitor.drop.pressure.controller;

import com.monitor.drop.pressure.service.PressureDetectionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/kafka")
public class Trigger {

    public Trigger(PressureDetectionService pressureDetectionService) {
        this.pressureDetectionService = pressureDetectionService;
    }

    PressureDetectionService pressureDetectionService;

    @GetMapping(value = "/trigger")
    public void send() throws InterruptedException {
        System.out.println("sent");

        pressureDetectionService.send();
    }

}
