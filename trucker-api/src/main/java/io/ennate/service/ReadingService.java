package io.ennate.service;

import io.ennate.entity.GeoLocation;
import io.ennate.entity.Reading;

import java.util.List;

public interface ReadingService {
    Reading create(Reading reading);
    List<GeoLocation> findLastLocation(String vin);
}
