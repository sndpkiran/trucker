package io.ennate.service;

import io.ennate.entity.Alert;

import java.util.List;

public interface AlertService {
    List<Alert> findAllByVehicleVin(String vin);
    List<Alert> findAllByPriority();
}
