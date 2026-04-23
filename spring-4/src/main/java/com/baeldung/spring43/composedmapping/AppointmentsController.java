package com.baeldung.spring43.composedmapping;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/appointments")
public class AppointmentsController {

    private static final Logger logger = LoggerFactory.getLogger(AppointmentsController.class);

    private AppointmentService appointmentService;

    @Autowired
    public AppointmentsController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping
    public Map<String, Appointment> get() {
        logger.info("Getting appointments...");
        return appointmentService.getAppointmentsForToday();
    }

}