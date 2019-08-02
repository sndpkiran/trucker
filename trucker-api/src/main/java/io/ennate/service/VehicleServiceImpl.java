package io.ennate.service;

import io.ennate.entity.Alert;
import io.ennate.entity.GeoLocation;
import io.ennate.entity.Reading;
import io.ennate.entity.Vehicle;
import io.ennate.exception.VehicleNotFoundException;
import io.ennate.repository.AlertRepository;
import io.ennate.repository.ReadingRepository;
import io.ennate.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.lang.Math.abs;

@Service
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    private VehicleRepository repository;
    @Autowired
    private AlertRepository alertRepository;
    @Autowired
    private ReadingRepository readingRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Vehicle> findAll() {
            return (List<Vehicle>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Vehicle find(String vin) {
        Optional<Vehicle> vehicle = repository.findById(vin);
        if (!vehicle.isPresent()) {
            throw new VehicleNotFoundException("Vehicle with vin:  " + vin + "is not found!");
        }
        return vehicle.get();
    }

    @Override
    @Transactional
    public Vehicle create(Vehicle vehicle) {
        return repository.save(vehicle);
    }

    @Override
    @Transactional
    public Reading createReading(Reading reading) {
        Optional<Vehicle> v = repository.findById(reading.getVin());

        if (v.isPresent()) {
            if(reading.getEngineRpm() > v.get().getRedlineRpm()){
                alertRepository.save(new Alert("High RPM alert", "HIGH", v.get()));
            }
            if(reading.getFuelVolume() < (0.10 * v.get().getMaxFuelVolume())) {
                alertRepository.save(new Alert("Low Fuel alert", "MEDUIUM", v.get()));
            }

            if(reading.getTires().getFrontLeft() < 32 || reading.getTires().getFrontLeft() > 36) {
                alertRepository.save(new Alert("Tire pressure alert", "LOW", v.get()));
            }
            if(reading.getTires().getFrontRight() < 32 || reading.getTires().getFrontRight() > 36) {
                alertRepository.save(new Alert("Tire pressure alert", "LOW", v.get()));
            }
            if(reading.getTires().getRearLeft() < 32 || reading.getTires().getRearLeft() > 36) {
                alertRepository.save(new Alert("Tire pressure alert", "LOW", v.get()));
            }
            if(reading.getTires().getRearRight() < 32 || reading.getTires().getRearRight() > 36) {
                alertRepository.save(new Alert("Tire pressure alert", "LOW", v.get()));
            }

            if(reading.getEngineCoolantLow() || reading.getCheckEngineLightOn()) {
                alertRepository.save(new Alert("Low coolant alert", "LOW", v.get()));
            }
        } else {
            throw new VehicleNotFoundException("Reading from a vehicle not in database.");
        }

        return readingRepository.save(reading);
    }

    @Override
    @Transactional
    public List<Vehicle> update(List<Vehicle> vehicles) {
        for(Vehicle v: vehicles) {
            repository.save(v);
        }
        return vehicles;
    }

    @Override
    @Transactional
    public Vehicle delete(String vin) {
        Optional<Vehicle> v = repository.findById(vin);
        if (!v.isPresent()) {
            throw new VehicleNotFoundException("Vehicle with vin:  " + vin + "is not found!");
        }
        repository.delete(v.get());
        return v.get();
    }

    @Override
    @Transactional(readOnly = true)
    public List<GeoLocation> findLastLocation(String vin) {
        List<Reading> readings = (List<Reading>) readingRepository.findLastLocation(vin);
        List<GeoLocation> geoLocations = new ArrayList<GeoLocation>();

        for(Reading r: readings) {
            String timestamp = r.getTimestamp();
            LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            LocalDateTime readingTime = LocalDateTime.parse(timestamp, formatter);
            Duration duration = Duration.between(readingTime, now);

            System.out.println("Duration between " + now.toString() + " and " + readingTime + " is " + abs(duration.toMinutes()));
            if (abs(duration.toMinutes()) <= 30) {
                geoLocations.add(new GeoLocation(r.getLatitude().toString(), r.getLongitude().toString()));
            }
        }
        return geoLocations;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Alert> findAllByVehicleVin(String vin) {
        return (List<Alert>) alertRepository.findAllByVehicleVin(vin);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Alert> findAllByPriority() {
            return (List<Alert>) alertRepository.findAllByPriority("HIGH");
    }
}
