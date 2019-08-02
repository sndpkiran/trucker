package io.ennate.repository;

import io.ennate.entity.Alert;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AlertRepository extends CrudRepository<Alert, String> {
    @Query("SELECT alert FROM Alert alert WHERE alert.vehicle.vin  = :paramVin")
    Iterable<Alert> findAllByVehicleVin(@Param("paramVin") String vin);

    Iterable<Alert> findAllByPriority(String priority);
}
