package io.ennate.repository;

import io.ennate.entity.GeoLocation;
import io.ennate.entity.Reading;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReadingRepository extends CrudRepository<Reading, String> {
    @Query("SELECT reading from Reading reading WHERE reading.vin = :paramVin")
    Iterable<Reading> findLastLocation(@Param("paramVin") String vin);
}
