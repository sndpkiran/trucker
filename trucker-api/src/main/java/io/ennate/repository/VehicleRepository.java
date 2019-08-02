package io.ennate.repository;

import io.ennate.entity.Alert;
import io.ennate.entity.GeoLocation;
import io.ennate.entity.Vehicle;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface VehicleRepository extends CrudRepository<Vehicle, String> {
}
