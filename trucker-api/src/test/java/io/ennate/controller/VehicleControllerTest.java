package io.ennate.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.ennate.entity.Vehicle;
import io.ennate.repository.VehicleRepository;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK )
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class VehicleControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Before
    public void setup() {
        Vehicle v = new Vehicle("1HGCR2F3XFA027534", "HONDA", "ACCORD", 2015, 5500, 15, "2017-05-25T17:31:25.268Z");
        vehicleRepository.save(v);
        v = new Vehicle("7FHYT2F3FYH075953", "FORD", "FIESTA", 2012, 4500, 15, "2017-04-15T17:31:25.268Z");
        vehicleRepository.save(v);
    }

    @After
    public void cleanup() {
        vehicleRepository.deleteAll();
    }

    @Test
    public void findAll() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/vehicles"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].vin", Matchers.is("1HGCR2F3XFA027534")));
    }

    @Test
    public void find() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/vehicles/1HGCR2F3XFA027534"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.vin", Matchers.is("1HGCR2F3XFA027534")));
    }

    @Test
    public void findNotFound() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/vehicles/invalidVin"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void create() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Vehicle v = new Vehicle("7FHYT2F3FYH075953", "FORD", "FIESTA", 2012, 4500, 15, "2017-04-15T17:31:25.268Z");

        mvc.perform(MockMvcRequestBuilders.post("/vehicles")
                                          .contentType(MediaType.APPLICATION_JSON)
                                          .content(mapper.writeValueAsBytes(v)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.vin", Matchers.is("7FHYT2F3FYH075953")));
    }

    @Test
    public void update() throws Exception {
        ObjectMapper mapper =new ObjectMapper();
        Vehicle v = new Vehicle("7FHYT2F3FYH075953", "FORD US", "FIESTA", 2013, 4500, 15, "2017-04-15T17:31:25.268Z");

        mvc.perform(MockMvcRequestBuilders.put("/vehicles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(Collections.singletonList(v))))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].make", Matchers.is("FORD US")));
    }

    @Test
    public void delete() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/vehicles/1HGCR2F3XFA027534"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}