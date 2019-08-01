package io.ennate.repository;

import io.ennate.entity.Alert;
import io.ennate.entity.GeoLocation;
import io.ennate.entity.Reading;
import io.ennate.entity.Vehicle;
import org.springframework.stereotype.Repository;
import sun.tools.jstat.Literal;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Repository
@NamedQueries({
        @NamedQuery(name = "Vehicle.findAll", query = "SELECT vehicle FROM Vehicle vehicle ORDER BY vehicle.make ASC"),
        @NamedQuery(name = "Reading.findLastLocation", query = "SELECT reading.latitude, reading.longitude FROM Reading reading WHERE reading.vin = :paramVin "),
        @NamedQuery(name = "Alert.findAllAlerts", query = "SELECT alert FROM Alert alert WHERE alert.vin  = :paramVin"),
        @NamedQuery(name = "Alert.findHighAlerts", query = "SELECT alert FROM Alert alert WHERE alert.priority = HIGH")
})
public class VehicleRepositoryImpl implements VehicleRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Vehicle> findAll() {
//        TypedQuery<Vehicle> query = entityManager.createNamedQuery("Vehicle.findAll", Vehicle.class);
        TypedQuery<Vehicle> query = entityManager.createQuery("SELECT vehicle FROM Vehicle vehicle ORDER BY vehicle.make ASC", Vehicle.class);
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
        TypedQuery<Reading> query = entityManager.createQuery("SELECT reading FROM Reading reading WHERE reading.vin = :paramVin", Reading.class);
        query.setParameter("paramVin", vin);
        List<Reading> readings = query.getResultList();
        List<GeoLocation> geoLocations = new ArrayList<GeoLocation>();

       for(Reading r: readings) {
            String timestamp = r.getTimestamp();
            LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            LocalDateTime readingTime = LocalDateTime.parse(timestamp, formatter);
            Duration duration = Duration.between(now, readingTime);

            System.out.println("Duration between " + now.toString() + " and " + readingTime + " is " + duration.toMinutes());
            if (duration.toMinutes() <= 30) {
                geoLocations.add(new GeoLocation(r.getLatitude().toString(), r.getLongitude().toString()));
            }
        }
        return geoLocations;
    }

    @Override
    public Alert createAlert(String message, String priority, Vehicle v) {
        Alert a = new Alert(message, priority, v);
        entityManager.persist(a);
        return a;
    }

    @Override
    public List<Alert> listAllAlerts(String vin) {
//        TypedQuery<Alert> query = entityManager.createNamedQuery("Alert.findAllAlerts", Alert.class);
        TypedQuery<Alert> query = entityManager.createQuery("SELECT alert FROM Alert alert WHERE alert.vehicle.vin  = :paramVin", Alert.class);
        query.setParameter("paramVin", vin);
        return query.getResultList();
    }

    @Override
    public List<Alert> listHighAlerts() {
//        TypedQuery<Alert> query = entityManager.createNamedQuery("Alert.findHighAlerts", Alert.class);
        TypedQuery<Alert> query = entityManager.createQuery("SELECT alert FROM Alert alert WHERE alert.priority = :paramPriority", Alert.class);
        query.setParameter("paramPriority", "HIGH");
        return query.getResultList();
    }
}
