package io.ennate.service;

import io.ennate.entity.Alert;
import io.ennate.repository.AlertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AlertServiceImpl implements AlertService {
    @Autowired
    private AlertRepository alertRepository;

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
