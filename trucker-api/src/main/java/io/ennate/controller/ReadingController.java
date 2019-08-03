package io.ennate.controller;

import io.ennate.entity.GeoLocation;
import io.ennate.entity.Reading;
import io.ennate.service.ReadingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://mocker.ennate.academy")
@RestController
@RequestMapping(value = "readings")
public class ReadingController {
    @Autowired
    private ReadingService service;

    @RequestMapping(method = RequestMethod.POST)
    public Reading createReading(@RequestBody Reading reading) {
        return service.create(reading);
    }

    @RequestMapping(method = RequestMethod.GET, value = "{id}/location")
    public List<GeoLocation> findLastLocation(@PathVariable("id") String id) {
        return service.findLastLocation(id);
    }
}
