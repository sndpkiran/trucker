package io.ennate.service;

import io.ennate.entity.Alert;
import io.ennate.entity.GeoLocation;
import io.ennate.entity.Reading;
import io.ennate.entity.Vehicle;

import java.util.List;

public interface VehicleService {
    List<Vehicle> findAll();
    Vehicle find(String vin);
    Vehicle create(Vehicle vehicle);
    List<Vehicle> update(List<Vehicle> vehicles);
    Vehicle delete(String vin);
}
