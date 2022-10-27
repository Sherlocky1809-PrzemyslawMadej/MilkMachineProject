package com.example.milkmachineproject.service;

import com.example.milkmachineproject.model.Sensor;
import com.example.milkmachineproject.repository.SensorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.swing.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class SensorServiceTest {

    @TestConfiguration
    static class SensorServiceTestConfiguration {

        @Bean
        public SensorService sensorService(SensorRepository sensorRepository) {
            return new SensorService(sensorRepository);
        }
    }

    @MockBean
    SensorRepository sensorRepository;

    @Autowired
    SensorService sensorService;

    @Test
    void should_return_list_of_sensors() {
        // when
        sensorService.getSensors();
        // then
        Mockito.verify(sensorRepository).findAll();
    }

}