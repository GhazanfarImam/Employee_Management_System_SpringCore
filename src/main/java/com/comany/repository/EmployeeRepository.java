package com.comany.repository;

import com.comany.entity.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository {
    // Save a new employee
    void save(Employee employee);

    // Find employee using primary key
    Optional<Employee> findById(Long employeeId);

    // Retrieve all employees
    List<Employee> findAll();

    // Update employee information
    void update(Employee employee);

    // Delete employee using ID
    boolean deleteById(Long employeeId);
}
