package io.ennate.controller;

import io.ennate.entity.Vehicle;
import io.ennate.service.VehicleService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://mocker.ennate.academy")
@RestController
@RequestMapping(value = "vehicles")
public class VehicleController {
    @Autowired
    private VehicleService service;

    @ApiOperation(value = "Find all vehicles",
                  notes = "Returns a list of all vehicles in the database.")
    @ApiResponse(code = 200, message = "OK")
    @RequestMapping(method = RequestMethod.GET)
    public List<Vehicle> findAll() {
        return service.findAll();
    }


    @RequestMapping(method = RequestMethod.GET, value = "{id}")
    @ApiOperation(value = "Find a vehicles with given id.",
            notes = "Returns a vehicle with the given id.")
    public Vehicle find(
            @ApiParam(value = "VIN of the vehicle", required = true) @PathVariable("id") String id) {
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

    @RequestMapping(method = RequestMethod.DELETE, value = "{vin}")
    public Vehicle delete(@PathVariable("vin") String vin) {
        return service.delete(vin);
    }
}
