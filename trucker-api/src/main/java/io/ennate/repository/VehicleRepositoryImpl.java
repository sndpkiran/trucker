package io.ennate.repository;

import io.ennate.entity.Alert;
import io.ennate.entity.GeoLocation;
import io.ennate.entity.Reading;
import io.ennate.entity.Vehicle;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;

@Repository
@NamedQueries({

        @NamedQuery(name = "Vehicle.findAll", query = "SELECT vehicle FROM Vehicle vehicle ORDER BY vehicle.make DESC"),
        @NamedQuery(name = "Vehicle.findLastLocation", query = "SELECT reading.latitude, reading.longitude FROM Reading reading WHERE reading.vin = :paramVin "),
        @NamedQuery(name = "Vehicle.findAllAlerts", query = "SELECT alert FROM Alert alert WHERE alert.vin  = :paramVin"),
        @NamedQuery(name = "Vehicle.findHighAlerts", query = "SELECT alert FROM Alert alert WHERE alert.priority = HIGH")
})
public class VehicleRepositoryImpl implements VehicleRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Vehicle> findAll() {
        TypedQuery<Vehicle> query = entityManager.createNamedQuery("Vehicle.findAll", Vehicle.class);
        return query.getResultList();
    }

    @Override
    public Vehicle find(String vin) {
        return entityManager.find(Vehicle.class, vin);
    }

    @Override
    public Vehicle create(Vehicle vehicle) {
        entityManager.persist(vehicle);
        return vehicle;
    }

    @Override
    public Reading createReading(Reading reading) {
        entityManager.persist(reading);
        return reading;
    }

    @Override
    public List<Vehicle> update(List<Vehicle> vehicles) {
//        List<Vehicle> currentVehicles = findAll();

        for(Vehicle v: vehicles) {
            entityManager.merge(v);
        }
        return vehicles;
    }

    @Override
    public void delete(Vehicle vehicle) {
        entityManager.remove(vehicle);
    }

    @Override
    public List<GeoLocation> findLastLocation(String vin) {
        TypedQuery<Vehicle> query = entityManager.createNamedQuery("Vehicle.findLastLocation", Vehicle.class);
        query.setParameter("paramVin", vin);
        return null;
    }

    @Override
    public List<Alert> listAllAlerts(String vin) {
        TypedQuery<Vehicle> query = entityManager.createNamedQuery("Vehicle.findAllAlerts", Vehicle.class);
        query.setParameter("paramVin", vin);
        return null;
    }

    @Override
    public List<Vehicle> listHighAlerts() {
        TypedQuery<Vehicle> query = entityManager.createNamedQuery("Vehicle.findHighAlerts", Vehicle.class);

        return null;
    }
}
