package io.ennate.service;

import io.ennate.entity.Alert;
import io.ennate.entity.Vehicle;
import io.ennate.repository.AlertRepository;
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

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class AlertServiceImplTest {

    @TestConfiguration
    static class AlertServiceImplTestConfig {
        @Bean
        public AlertService getService() {
            return new AlertServiceImpl();
        }
    }

    @Autowired
    private AlertService alertService;

    @MockBean
    private AlertRepository alertRepository;

    private List<Alert> alerts;

    @Before
    public void setup() {
        Vehicle v = new Vehicle("1HGCR2F3XFA027534", "HONDA", "ACCORD", 2015, 5500, 15, "2017-05-25T17:31:25.268Z");
        Alert a = new Alert("High RPM alert", "HIGH", v);

        alerts = Collections.singletonList(a);

        Mockito.when(alertRepository.findAllByVehicleVin("1HGCR2F3XFA027534")).thenReturn(alerts);
        Mockito.when(alertRepository.findAllByPriority("1HGCR2F3XFA027534")).thenReturn(alerts);
    }


    @After
    public void cleanup() {

    }

    @Test
    public void findAllByVehicleVin() {
        List<Alert> result = alertService.findAllByVehicleVin("1HGCR2F3XFA027534");
        Assert.assertEquals("High rpm alert expected", alerts, result);
    }

    @Test
    public void findAllByPriority() {
        List<Alert> result = alertService.findAllByVehicleVin("1HGCR2F3XFA027534");
        Assert.assertEquals("High alert expected", alerts, result);
    }
}