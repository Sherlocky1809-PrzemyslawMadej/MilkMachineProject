package com.example.milkmachineproject.service;

import com.example.milkmachineproject.model.Sensor;
import com.example.milkmachineproject.repository.SensorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SensorService {

    private SensorRepository sensorRepository;

    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    public List<Sensor> getSensors() {
        return sensorRepository.findAll();
    }
}
