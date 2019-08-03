package io.ennate.controller;

import io.ennate.entity.Alert;
import io.ennate.service.AlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://mocker.ennate.academy")
@RestController
@RequestMapping(value = "alerts")
public class AlertController {
    @Autowired
    private AlertService service;

    @RequestMapping(method = RequestMethod.GET, value = "{id}")
    public List<Alert> findAllByVehicleVin(@PathVariable("id") String id) {
        return service.findAllByVehicleVin(id);
    }

    @RequestMapping(method = RequestMethod.GET, value = "high")
    public List<Alert> listHighAlerts() {
        return service.findAllByPriority();
    }
}
