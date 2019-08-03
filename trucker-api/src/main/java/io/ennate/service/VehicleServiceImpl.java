package io.ennate.service;

import io.ennate.entity.Vehicle;
import io.ennate.exception.VehicleNotFoundException;

import io.ennate.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

@Service
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    private VehicleRepository repository;


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

}
