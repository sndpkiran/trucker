package io.ennate.service;

import io.ennate.entity.Vehicle;
import io.ennate.exception.VehicleNotFoundException;
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

@RunWith(SpringRunner.class)
public class VehicleServiceImplTest {

    @TestConfiguration
    static class VehicleServiceImplTestConfig {
        @Bean
        public VehicleService getService() {
            return new VehicleServiceImpl();
        }
    }

    @Autowired
    private VehicleService vehicleService;

    @MockBean
    private VehicleRepository vehicleRepository;

    private List<Vehicle> vehicles;

    @Before
    public void setup() {
        Vehicle v = new Vehicle("1HGCR2F3XFA027534", "HONDA", "ACCORD", 2015, 5500, 15, "2017-05-25T17:31:25.268Z");

        vehicles = Collections.singletonList(v);
        Mockito.when(vehicleRepository.findAll()).thenReturn(vehicles);

        Mockito.when(vehicleRepository.findById(v.getVin())).thenReturn(Optional.of(v));

        Mockito.when(vehicleRepository.save(v)).thenReturn(v);
    }

    @After
    public void cleanup() {

    }

    @Test
    public void findAll() {
        List<Vehicle> result = vehicleService.findAll();
        Assert.assertEquals("Vehicles lists match.", vehicles, result);
    }

    @Test
    public void find() {
        Vehicle v  = vehicleService.find(vehicles.get(0).getVin());
        Assert.assertEquals("Vehicle must match.", vehicles.get(0),v);
    }

    @Test(expected = VehicleNotFoundException.class)
    public void findVehicleBotFound() {
        Vehicle result  = vehicleService.find("InvalidVin");
    }

    @Test
    public void create() {

        Vehicle result  = vehicleService.create(vehicles.get(0));
        Assert.assertEquals("Vehicle must match.", vehicles.get(0), result);
    }

    @Test
    public void update() {
        Vehicle v = new Vehicle("1HGCR2F3XFA027534", "FORD", "ACCORD", 2015, 5500, 15, "2017-05-25T17:31:25.268Z");

        List<Vehicle> result = vehicleService.update(Collections.singletonList(v));
        Assert.assertEquals("Vehicle must match.", Collections.singletonList(v), result);
    }

    @Test
    public void delete() {
        Vehicle result  = vehicleService.delete(vehicles.get(0).getVin());
        Assert.assertEquals("Vehicle must match.",  vehicles.get(0), result);
    }


    @Test(expected = VehicleNotFoundException.class)
    public void deleteNotFound() {
        Vehicle result  = vehicleService.delete("InvalidId");
    }
}