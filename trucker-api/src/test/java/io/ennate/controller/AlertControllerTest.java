package io.ennate.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.ennate.entity.Alert;
import io.ennate.entity.Reading;
import io.ennate.entity.Tire;
import io.ennate.entity.Vehicle;
import io.ennate.repository.AlertRepository;
import io.ennate.repository.ReadingRepository;
import io.ennate.repository.VehicleRepository;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AlertControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private AlertRepository alertRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private ReadingRepository readingRepository;

    @Before
    public void setup() {
        Vehicle v = new Vehicle("1HGCR2F3XFA027534", "HONDA", "ACCORD", 2015, 5500, 15, "2017-05-25T17:31:25.268Z");
        vehicleRepository.save(v);

        ObjectMapper mapper = new ObjectMapper();
        Reading r = new Reading("1HGCR2F3XFA027534", 41.803194, -88.144406, "2019-08-03T08:30:25.268Z", 1.5, 85, 240, false, true, true, 6300, new Tire(34, 36,29, 34));
        readingRepository.save(r);

        alertRepository.save(new Alert("High RPM alert", "HIGH", v));
    }

    @After
    public void cleanup() {
    }
    @Test
    public void findAllByVehicleVin() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/alerts/1HGCR2F3XFA027534"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].message", Matchers.is("High RPM alert")));
    }

    @Test
    public void listHighAlerts() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/alerts/1HGCR2F3XFA027534"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].priority", Matchers.is("HIGH")));
    }
}