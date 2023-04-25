package com.example.milkmachineproject.service;

import com.example.milkmachineproject.model.Frequency;
import com.example.milkmachineproject.model.MilkMachine;
import com.example.milkmachineproject.model.Sensor;
import com.example.milkmachineproject.repository.MilkMachineRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class MilkMachineServiceTest {

    @TestConfiguration
    static class MilkMachineServiceTestConfiguration {
        @Bean
        public MilkMachineService milkMachineService(MilkMachineRepository milkMachineRepository) {
            return new MilkMachineService(milkMachineRepository);
        }
    }

    @MockBean
    MilkMachineRepository milkMachineRepository;

    @Autowired
    MilkMachineService milkMachineService;

    @Test
    void should_add_milk_machine_if_repository_not_contain_machine_by_given_name() {
        // given
        String name = "test1";
        MilkMachine milkMachineToAdd = new MilkMachine(name);
        Mockito.when(milkMachineRepository.findByName(name)).thenReturn(Optional.empty());
        // when
        milkMachineService.addMilkMachine(milkMachineToAdd);
        // then
        Mockito.verify(milkMachineRepository).save(milkMachineToAdd);
    }

    @Test
    void should_throw_illegal_argument_exception_if_add_machine_by_existing_name() {
        // given
        String name = "test1";
        MilkMachine milkMachineToAdd = new MilkMachine(name);
        Mockito.when(milkMachineRepository.findByName(milkMachineToAdd.getName()))
                .thenReturn(Optional.of(milkMachineToAdd));
        // then
        assertThatThrownBy(() -> milkMachineService.addMilkMachine(milkMachineToAdd))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Machine already exists!");
    }

    @Test
    void should_edit_machine_if_exists() {
        // given
        ArgumentCaptor<MilkMachine> milkMachineArgumentCaptor = ArgumentCaptor.forClass(MilkMachine.class);
        List<Sensor> sensors = new ArrayList<>();
        MilkMachine existingMilkMachine = new MilkMachine(1L, "test1", sensors);
        MilkMachine newMilkMachine = new MilkMachine(1L, "test2", sensors);
        Mockito.when(milkMachineRepository.findById(newMilkMachine.getId()))
                .thenReturn(Optional.of(existingMilkMachine));
        Mockito.when(milkMachineRepository.findByName(newMilkMachine.getName()))
                .thenReturn(Optional.empty());
        // when
        milkMachineService.editMilkMachine(newMilkMachine);
        // then
        Mockito.verify(milkMachineRepository).save(milkMachineArgumentCaptor.capture());
        MilkMachine editedMilkMachine = milkMachineArgumentCaptor.getValue();
        assertEquals(1L, editedMilkMachine.getId());
        assertEquals("test2", editedMilkMachine.getName());
    }

    @Test
    void should_throw_illegal_argument_exception_if_machine_name_to_update_exists() {
        // given
        List<Sensor> sensors = new ArrayList<>();
        MilkMachine existingmilkMachine = new MilkMachine(1L,"test", sensors);
        MilkMachine newMilkMachine = new MilkMachine(1L, "word", sensors);
        Mockito.when(milkMachineRepository.findById(newMilkMachine.getId()))
                .thenReturn(Optional.of(existingmilkMachine));
        Mockito.when(milkMachineRepository.findByName(newMilkMachine.getName()))
                .thenReturn(Optional.of(newMilkMachine));
        // when & then
        assertThatThrownBy(() -> milkMachineService.editMilkMachine(newMilkMachine))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Machine by this name is already exists!");
    }

    @Test
    void should_throw_no_such_element_exception_if_machine_to_edit_is_not_found() {
        // given
        List<Sensor> sensors = new ArrayList<>();
        MilkMachine milkMachine = new MilkMachine(1L,"test", sensors);
        Mockito.when(milkMachineRepository.findById(milkMachine.getId()))
                .thenReturn(Optional.empty());
        // when & then
        assertThatThrownBy(() -> milkMachineService.editMilkMachine(milkMachine))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("No machine to edit found!");
    }

    @Test
    void should_throw_no_such_element_exception_if_machine_to_delete_not_exists() {
        // given
        Mockito.when(milkMachineRepository.findById(1L))
                .thenReturn(Optional.empty());
        // when & then
        assertThatThrownBy(() -> milkMachineService.deleteMilkMachineFromListById(1L))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("No machine to remove found!");
    }

    @Test
    void should_delete_milk_machine_by_given_id() {
        // given
        List<Sensor> sensors = new ArrayList<>();
        MilkMachine milkMachineToDelete = new MilkMachine(1L, "test", sensors);
        Mockito.when(milkMachineRepository.findById(milkMachineToDelete.getId()))
                .thenReturn(Optional.of(milkMachineToDelete));
        // when
        milkMachineService.deleteMilkMachineFromListById(milkMachineToDelete.getId());
        // then
        Mockito.verify(milkMachineRepository).delete(milkMachineToDelete);
    }

    @Test
    void should_find_machine_by_given_id() {
        // given
        List<Sensor> sensors = new ArrayList<>();
        MilkMachine milkMachineToFind = new MilkMachine(1L, "test", sensors);
        Mockito.when(milkMachineRepository.findById(milkMachineToFind.getId()))
                .thenReturn(Optional.of(milkMachineToFind));
        // when
        milkMachineService.getMilkMachineFromList(milkMachineToFind.getId());
        // then
        Mockito.verify(milkMachineRepository).findById(milkMachineToFind.getId());
    }

    @Test
    void should_throw_no_such_element_exception_if_machine_by_given_id_not_exists() {
        // given
        Mockito.when(milkMachineRepository.findById(1L))
                .thenReturn(Optional.empty());
        // when & then
        assertThatThrownBy(() -> milkMachineService.getMilkMachineFromList(1L))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("No machine by given id found!");
    }

    @Test
    void should_add_sensor_to_machine_with_given_id() {
        // given
        ArgumentCaptor<MilkMachine> milkMachineArgumentCaptor = ArgumentCaptor.forClass(MilkMachine.class);
        Sensor sensor = new Sensor();
        List<Sensor> emptyList = new ArrayList<>();
        List<Sensor> sensors = List.of(sensor);
        MilkMachine milkMachine = new MilkMachine(1L, "test", emptyList);
        MilkMachine milkMachineWithNewSensor = new MilkMachine(1L, "test", sensors);
        Mockito.when(milkMachineRepository.findById(1L))
                .thenReturn(Optional.of(milkMachine));
        Mockito.when(milkMachineRepository.findByName(milkMachineWithNewSensor.getName()))
                .thenReturn(Optional.of(milkMachineWithNewSensor));
        // when
        milkMachineService.addSensorToMachineByGivenId(1L, sensor);
        // then
        Mockito.verify(milkMachineRepository).save(milkMachineArgumentCaptor.capture());
        MilkMachine editedMilkMachine = milkMachineArgumentCaptor.getValue();
        assertEquals(1L, editedMilkMachine.getId());
        assertEquals("test", editedMilkMachine.getName());
    }


    @Test
    void should_return_proper_average_temperature_when_a_sensors_list_given() {
        // given
        Sensor sensor1 = new Sensor("upper", 4.0f, Frequency.ONCE_DAY);
        Sensor sensor2 = new Sensor("bottom", 6.0f, Frequency.ONCE_DAY);
        List<Sensor> sensorList = List.of(sensor1, sensor2);
        MilkMachine milkMachine = new MilkMachine(1L, "test", sensorList);
        Mockito.when(milkMachineRepository.findById(milkMachine.getId()))
                .thenReturn(Optional.of(milkMachine));
        float expectedResult = 5.0f;
        // when
        float result = milkMachineService.getAverageTemperatureOfMachineGivenById(milkMachine.getId());
        // then
        Mockito.verify(milkMachineRepository).findById(milkMachine.getId());
        assertEquals(expectedResult, result);

    }
}