package com.example.milkmachineproject.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Sensor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String position;

    private float temperature;

    private Frequency frequency;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "milk_machine_id")
    private MilkMachine milkMachine;

    public Sensor() {
    }

    public Sensor(String position, float temperature, Frequency frequency) {
        this.position = position;
        this.temperature = temperature;
        this.frequency = frequency;
    }

    public Sensor(Long id, String position, float temperature,
                  Frequency frequency, MilkMachine milkMachine) {
        this.id = id;
        this.position = position;
        this.temperature = temperature;
        this.frequency = frequency;
        this.milkMachine = milkMachine;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public Frequency getFrequency() {
        return frequency;
    }

    public void setFrequency(Frequency frequency) {
        this.frequency = frequency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sensor sensor = (Sensor) o;
        return temperature == sensor.temperature && Objects.equals(id, sensor.id) && Objects.equals(position, sensor.position) && frequency == sensor.frequency;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, position, temperature, frequency);
    }
}
