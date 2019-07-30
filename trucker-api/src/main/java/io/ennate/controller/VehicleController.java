package io.ennate.controller;

import io.ennate.entity.Alert;
import io.ennate.entity.GeoLocation;
import io.ennate.entity.Reading;
import io.ennate.entity.Vehicle;
import io.ennate.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://mocker.ennate.academy")
@RestController
@RequestMapping(value = "vehicles")
public class VehicleController {
    @Autowired
    private VehicleService service;

    @RequestMapping(method = RequestMethod.GET)
    public List<Vehicle> findAll() {
        return service.findAll();
    }

    @RequestMapping(method = RequestMethod.GET, value = "{id}")
    public Vehicle find(@PathVariable("id") String id) {
        return service.find(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Vehicle create(@RequestBody Vehicle vehicle) {
        return service.create(vehicle);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public List<Vehicle> update(@RequestBody List<Vehicle> vehicles) {
        return service.update(vehicles);
    }

    @RequestMapping(method = RequestMethod.POST, value = "readings")
    public Reading createReading(@RequestBody Reading reading) {
        return service.createReading(reading);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "{id}")
    public void delete(@PathVariable("id") String id) {
        return;
    }

    @RequestMapping(method = RequestMethod.GET, value = "{id}/last_locations")
    public List<GeoLocation> findLastLocation(@PathVariable("id") String id) {
        return null;
    }

    @RequestMapping(method = RequestMethod.GET, value = "{id}/alerts")
    public List<Alert> listAllAlerts(@PathVariable("id") String id) {
        return null;
    }

    @RequestMapping(method = RequestMethod.GET, value = "high_alerts")
    public List<Vehicle> listHighAlerts() {
        return null;
    }
}
