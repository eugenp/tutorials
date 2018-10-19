package org.baeldung.spring43.composedmapping;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.apache.logging.log4j.Logger;

@Controller
@RequestMapping("/appointments")
public class AppointmentsController {

    private AppointmentService appointmentService;

    @Autowired
    private Logger logger;

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