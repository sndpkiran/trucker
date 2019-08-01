package io.ennate.service;

import io.ennate.entity.Alert;
import io.ennate.entity.GeoLocation;
import io.ennate.entity.Reading;
import io.ennate.entity.Vehicle;
import io.ennate.exception.VehicleNotFoundException;
import io.ennate.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    private VehicleRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<Vehicle> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Vehicle find(String vin) {
        Vehicle vehicle = repository.find(vin);
        if (vehicle == null) {
            throw new VehicleNotFoundException("Vehicle with id = " + vin + " is not found.");
        } else {
            return vehicle;
        }
    }

    @Override
    @Transactional
    public Vehicle create(Vehicle vehicle) {
        return repository.create(vehicle);
    }

    @Override
    @Transactional
    public Reading createReading(Reading reading) {
        Vehicle v = repository.find(reading.getVin());

        if (v != null) {
            if(reading.getEngineRpm() > v.getRedlineRpm()){
                repository.createAlert("High RPM alert", "HIGH", v);
            }
            if(reading.getFuelVolume() < (0.10 * v.getMaxFuelVolume())) {
                repository.createAlert("Low Fuel alert", "MEDUIUM", v);
            }

            if(reading.getTires().getFrontLeft() < 32 || reading.getTires().getFrontLeft() > 36) {
                repository.createAlert("Tire pressure alert", "LOW", v);
            }
            if(reading.getTires().getFrontRight() < 32 || reading.getTires().getFrontRight() > 36) {
                repository.createAlert("Tire pressure alert", "LOW", v);
            }
            if(reading.getTires().getRearLeft() < 32 || reading.getTires().getRearLeft() > 36) {
                repository.createAlert("Tire pressure alert", "LOW", v);
            }
            if(reading.getTires().getRearRight() < 32 || reading.getTires().getRearRight() > 36) {
                repository.createAlert("Tire pressure alert", "LOW", v);
            }

            if(reading.getEngineCoolantLow() || reading.getCheckEngineLightOn()) {
                repository.createAlert("Low coolant alert", "LOW", v);
            }
        } else {
            throw new VehicleNotFoundException("Reading from a vehicle not in database.");
        }

        return repository.createReading(reading);
    }

    @Override
    @Transactional
    public List<Vehicle> update(List<Vehicle> vehicles) {
        return repository.update(vehicles);
    }

    @Override
    @Transactional
    public void delete(Vehicle vehicle) {
        repository.delete(vehicle);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GeoLocation> findLastLocation(String vin) {
        return repository.findLastLocation(vin);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Alert> listAllAlerts(String vin) {
        return repository.listAllAlerts(vin);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Alert> listHighAlerts() {
        return repository.listHighAlerts();
    }
}
