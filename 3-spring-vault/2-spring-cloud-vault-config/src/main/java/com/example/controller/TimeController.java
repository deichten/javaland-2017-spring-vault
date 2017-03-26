package com.example.controller;


import com.example.service.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/time")
public class TimeController {

    private TimeService timeSvc;

    @Autowired
    public TimeController(TimeService timeSvc) {
        this.timeSvc = timeSvc;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getTimeStamp() {
        return "{\"time\": \"" + timeSvc.getTimeStamp().get().toString() + "\"}";
    }
}
