package io.ennate.service;

import io.ennate.entity.*;
import io.ennate.exception.VehicleNotFoundException;
import io.ennate.repository.AlertRepository;
import io.ennate.repository.ReadingRepository;
import io.ennate.repository.VehicleRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
public class ReadingServiceImplTest {
    @TestConfiguration
    static class ReadingServiceImplTestConfig {
        @Bean
        public ReadingService getService()  {
            return new ReadingServiceImpl();
        }
    }

    @Autowired
    private ReadingService readingService;

    @MockBean
    private VehicleRepository vehicleRepository;

    @MockBean
    private ReadingRepository readingRepository;

    @MockBean
    private AlertRepository alertRepository;

    private List<Reading> readings;

    @Before
    public void setup() {
        Vehicle v = new Vehicle("1HGCR2F3XFA027534", "HONDA", "ACCORD", 2015, 5500, 15, "2017-05-25T17:31:25.268Z");
        Reading r = new Reading("1HGCR2F3XFA027534", 41.803194, -88.144406, "2019-08-03T15:10:25.268Z", 1.5, 85, 240, false, true, true, 6300, new Tire(34, 36,29, 34));
        Alert a = new Alert("High RPM alert", "HIGH", v);

        readings = Collections.singletonList(r);

//        GeoLocation gl = new GeoLocation("41.803194", "-88.144406"))
        Mockito.when(alertRepository.save(any(Alert.class))).thenReturn(a);
        Mockito.when(readingRepository.save(any(Reading.class))).thenReturn(r);
        Mockito.when(readingRepository.findLastLocation("1HGCR2F3XFA027534")).thenReturn(readings);
    }

    @After
    public void cleanup() {
    }

    @Test
    public void create() {
        Vehicle v = new Vehicle("1HGCR2F3XFA027534", "HONDA", "ACCORD", 2015, 5500, 15, "2017-05-25T17:31:25.268Z");
        Mockito.when(vehicleRepository.findById(v.getVin())).thenReturn(Optional.of(v));

        Reading r = new Reading("1HGCR2F3XFA027534", 41.803194, -88.144406, "2019-08-03T08:30:25.268Z", 1.5, 85, 240, false, true, true, 6300, new Tire(34, 36,29, 34));
        Reading result = readingService.create(r);

        Assert.assertEquals("Readings must match", readings.get(0), result);
    }

    @Test(expected = VehicleNotFoundException.class)
    public void createWithoutVehicleInDb() {
        Reading r = new Reading("1HGCR2F3XFA027534", 41.803194, -88.144406, "2019-08-03T08:30:25.268Z", 1.5, 85, 240, false, true, true, 6300, new Tire(34, 36,29, 34));
        Reading result = readingService.create(r);
    }

    @Test
    public void findLastLocation() {
        List<GeoLocation> result = readingService.findLastLocation("1HGCR2F3XFA027534");

        GeoLocation gl = new GeoLocation(readings.get(0).getLatitude().toString(), readings.get(0).getLongitude().toString());

        Assert.assertEquals("List of geo coordinates must match", readings.get(0).getLatitude().toString()
                , result.get(0).getLatitude());
        Assert.assertEquals("List of geo coordinates must match", readings.get(0).getLongitude().toString(), result.get(0).getLongitude());
    }
}