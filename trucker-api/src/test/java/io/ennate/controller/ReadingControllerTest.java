package io.ennate.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.ennate.entity.Reading;
import io.ennate.entity.Tire;
import io.ennate.entity.Vehicle;
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
import org.springframework.http.MediaType;
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
public class ReadingControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ReadingRepository readingRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Before
    public void setup() {
        Vehicle v = new Vehicle("1HGCR2F3XFA027534", "HONDA", "ACCORD", 2015, 5500, 15, "2019-08-03T15:10:25.268Z");
        vehicleRepository.save(v);
    }

    @After
    public void cleanup() {
        readingRepository.deleteAll();
    }

    @Test
    public void createReading() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Reading r = new Reading("1HGCR2F3XFA027534", 41.803194, -88.144406, "2019-08-03T15:10:25.268Z", 1.5, 85, 240, false, true, true, 6300, new Tire(34, 36,29, 34));

        mvc.perform(MockMvcRequestBuilders.post("/readings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(r)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.vin", Matchers.is("1HGCR2F3XFA027534")));
    }

    @Test
    public void findLastLocation() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/readings/1HGCR2F3XFA027534/location"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}