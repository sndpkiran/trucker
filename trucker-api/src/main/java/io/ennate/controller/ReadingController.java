package io.ennate.controller;

import io.ennate.entity.GeoLocation;
import io.ennate.entity.Reading;
import io.ennate.service.ReadingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://mocker.ennate.academy")
@RestController
@RequestMapping(value = "readings")
@Api(description = "Vehicle endpoints")
public class ReadingController {
    @Autowired
    private ReadingService service;

    @ApiOperation(value = "Create a Reading",
            notes = "Returns the reading stored in the database.")
    @ApiResponse(code = 200, message = "OK")
    @RequestMapping(method = RequestMethod.POST)
    public Reading createReading(@RequestBody Reading reading) {
        return service.create(reading);
    }

    @ApiOperation(value = "Find the geo coordinates of a vehicles in the last 30 minutes.",
            notes = "Returns a list of geo coordinates")
    @ApiResponse(code = 200, message = "OK")
    @RequestMapping(method = RequestMethod.GET, value = "{id}/location")
    public List<GeoLocation> findLastLocation(@PathVariable("id") String id) {
        return service.findLastLocation(id);
    }
}
