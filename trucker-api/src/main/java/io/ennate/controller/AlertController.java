package io.ennate.controller;

import io.ennate.entity.Alert;
import io.ennate.service.AlertService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://mocker.ennate.academy")
@RestController
@RequestMapping(value = "alerts")
@Api(description = "Vehicle endpoints")
public class AlertController {
    @Autowired
    private AlertService service;

    @ApiOperation(value = "Find all the alerts created for a vehicle with given vin.",
            notes = "Returns a list of all alerts created for the vehicle")
    @ApiResponse(code = 200, message = "OK")
    @RequestMapping(method = RequestMethod.GET, value = "{id}")
    public List<Alert> findAllByVehicleVin(@PathVariable("id") String id) {
        return service.findAllByVehicleVin(id);
    }

    @ApiOperation(value = "Find all alerts with HIGH priority",
            notes = "Returns a list of all HIGH priority alerts")
    @ApiResponse(code = 200, message = "OK")
    @RequestMapping(method = RequestMethod.GET, value = "high")
    public List<Alert> listHighAlerts() {
        return service.findAllByPriority();
    }
}
