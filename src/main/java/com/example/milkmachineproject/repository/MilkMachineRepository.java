package com.example.milkmachineproject.repository;

import com.example.milkmachineproject.model.MilkMachine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MilkMachineRepository extends JpaRepository<MilkMachine, Long> {

    Optional<MilkMachine> findByName(String name);
}
