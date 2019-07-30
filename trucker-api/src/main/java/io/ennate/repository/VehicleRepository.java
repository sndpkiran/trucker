package io.ennate.repository;

import io.ennate.entity.Alert;
import io.ennate.entity.GeoLocation;
import io.ennate.entity.Reading;
import io.ennate.entity.Vehicle;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository {
    List<Vehicle> findAll();
    Vehicle find(String vin);
    Vehicle create(Vehicle vehicle);
    Reading createReading(Reading reading);
    List<Vehicle> update(List<Vehicle> vehicles);
    void delete(Vehicle vehicle);
    List<GeoLocation> findLastLocation(String id);
    List<Alert> listAllAlerts(String id);
    List<Vehicle> listHighAlerts();
}
