package com.example.milkmachineproject.service;

import com.example.milkmachineproject.model.MilkMachine;
import com.example.milkmachineproject.model.Sensor;
import com.example.milkmachineproject.repository.MilkMachineRepository;
import com.example.milkmachineproject.repository.SensorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class MilkMachineService {

    private MilkMachineRepository milkMachineRepository;


    public MilkMachineService(MilkMachineRepository milkMachineRepository) {
        this.milkMachineRepository = milkMachineRepository;
    }

    public List<MilkMachine> getMilkMachines() {
        return milkMachineRepository.findAll();
    }

    public MilkMachine addMilkMachine(MilkMachine milkMachine) {
        milkMachineRepository.findByName(milkMachine.getName())
                .ifPresent(o -> {
                    throw new IllegalArgumentException("Machine already exists!");
                } );
        return milkMachineRepository.save(milkMachine);
    }

    public MilkMachine editMilkMachine(MilkMachine milkMachine) {
        MilkMachine milkMachineToEdit = milkMachineRepository.findById(milkMachine.getId())
                .orElseThrow(() -> new NoSuchElementException("No machine to edit found!"));
        milkMachineRepository.findByName(milkMachine.getName())
                .ifPresent(o -> {
                    throw new IllegalArgumentException("Machine by this name is already exists!");
                });
        if(milkMachine.getName() != null) {
            milkMachineToEdit.setName(milkMachine.getName());
        }
        return milkMachineRepository.save(milkMachineToEdit);
    }

    public MilkMachine deleteMilkMachineFromListById(Long id) {
        MilkMachine milkMachineToDelete = milkMachineRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No machine to remove found!"));
        milkMachineRepository.delete(milkMachineToDelete);
        return milkMachineToDelete;
    }

    public MilkMachine getMilkMachineFromList(Long id) {
        return milkMachineRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No machine by given id found!"));
    }

    public MilkMachine addSensorToMachineByGivenId(Long id, Sensor newSensor) {
        MilkMachine milkMachineById = getMilkMachineFromList(id);
        List<Sensor> sensorsFromMachine = milkMachineById.getSensors();
        sensorsFromMachine.add(newSensor);
        milkMachineById.setSensors(sensorsFromMachine);
        return milkMachineRepository.save(milkMachineById);
    }

    public float getAverageTemperatureOfMachineGivenById(Long id) {
         MilkMachine chosenMilkMachine = getMilkMachineFromList(id);
         List<Sensor> sensorList = chosenMilkMachine.getSensors();

         float sum = 0;

        for (Sensor sensor: sensorList) {
            for (int i = 0; i < sensorList.size(); i++) {
                sum += sensor.getTemperature();
            }
        }
        return sum/sensorList.size();
    }
}
