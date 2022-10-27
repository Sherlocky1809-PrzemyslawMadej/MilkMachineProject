package com.example.milkmachineproject.controller;

import com.example.milkmachineproject.model.MilkMachine;
import com.example.milkmachineproject.model.Sensor;
import com.example.milkmachineproject.service.MilkMachineService;
import com.example.milkmachineproject.service.SensorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/api/milk_machines")
public class MachineController {
//
    private final MilkMachineService milkMachineService;

    public MachineController(MilkMachineService milkMachineService) {
        this.milkMachineService = milkMachineService;
    }
//
    @GetMapping
    List<MilkMachine> getMilkMachines() {
        return milkMachineService.getMilkMachines();
    }
//
    @PostMapping
    MilkMachine addPostMilkMachine(@RequestBody MilkMachine milkMachine) {
        return milkMachineService.addMilkMachine(milkMachine);
    }
//
    @PutMapping
    MilkMachine modifyMilkMachine(@RequestBody MilkMachine milkMachine) {
       return milkMachineService.editMilkMachine(milkMachine);
    }
//
    @DeleteMapping
    MilkMachine removeMilkMachine(@PathVariable("/{id}") Long id) {
        return milkMachineService.deleteMilkMachineFromListById(id);
    }
//
    @PostMapping("/{idMachine}")
    MilkMachine addSensorToMachine(@PathVariable("idMachine") Long idMachine, @RequestBody Sensor sensor) {
        return milkMachineService.addSensorToMachineByGivenId(idMachine, sensor);
    }

    @GetMapping ("/{idMachine}")
    float getTemperatureAverageFromMachine(@PathVariable("idMachine") Long idMachine) {
        return milkMachineService.getAverageTemperatureOfMachineGivenById(idMachine);
    }
////
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Object> handleNoSuchElementException(NoSuchElementException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
