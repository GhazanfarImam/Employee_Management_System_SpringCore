
package com.comany.service;

import com.comany.entity.Employee;
import com.comany.repository.EmployeeRepository;
import com.comany.notification.NotificationService;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final NotificationService emailNotificationService;
    private final NotificationService smsNotificationService;

    // Constructor Injection with Qualifier
    public EmployeeService(
            EmployeeRepository employeeRepository,
            @Qualifier("emailNotificationService")
            NotificationService emailNotificationService,
            @Qualifier("smsNotificationService")
            NotificationService smsNotificationService) {

        this.employeeRepository = employeeRepository;
        this.emailNotificationService = emailNotificationService;
        this.smsNotificationService = smsNotificationService;
    }

    // Register Employee
    @Transactional
    public Employee registerEmployee(Employee employee) {

        validateEmployee(employee);

        employeeRepository.save(employee);

        System.out.println("Employee Registered Successfully");

        emailNotificationService.sendNotification(
                "Employee registered: " + employee.getName()
        );

        smsNotificationService.sendNotification(
                "Welcome " + employee.getName()
        );

        return employee;
    }

    // Find Employee By ID
    public Optional<Employee> getEmployeeById(Long employeeId) {

        validateEmployeeId(employeeId);

        return employeeRepository.findById(employeeId);
    }

    // Retrieve All Employees
    @Transactional(readOnly = true)
    public List<Employee> getAllEmployees() {

        return employeeRepository.findAll();
    }

    // Update Employee
    @Transactional
    public Employee updateEmployee(
            Long employeeId,
            String name,
            String email,
            String department,
            BigDecimal salary) {

        validateEmployeeId(employeeId);

        Optional<Employee> optionalEmployee =
                employeeRepository.findById(employeeId);

        // Correct condition: throw exception when employee is absent
        if (!optionalEmployee.isPresent()) {

            throw new IllegalArgumentException(
                    "Employee not found with ID: " + employeeId
            );
        }

        Employee employee = optionalEmployee.get();

        employee.setName(name);
        employee.setEmail(email);
        employee.setDepartment(department);
        employee.setSalary(salary);

        validateEmployee(employee);

        employeeRepository.update(employee);

        System.out.println("Employee Updated Successfully");

        return employee;
    }

    // Delete Employee
    public boolean deleteEmployee(Long employeeId) {

        validateEmployeeId(employeeId);

        boolean deleted =
                employeeRepository.deleteById(employeeId);

        if (!deleted) {

            throw new IllegalArgumentException(
                    "Employee not found with ID: " + employeeId
            );
        }

        System.out.println("Employee Deleted Successfully");

        return true;
    }

    // Employee Validation
    private void validateEmployee(Employee employee) {

        if (employee == null) {
            throw new IllegalArgumentException(
                    "Employee cannot be null"
            );
        }

        if (employee.getName() == null ||
                employee.getName().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Employee name cannot be empty"
            );
        }

        if (employee.getEmail() == null ||
                !Pattern.matches(
                        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
                        employee.getEmail())) {

            throw new IllegalArgumentException(
                    "Please enter a valid email address"
            );
        }

        if (employee.getDepartment() == null ||
                employee.getDepartment().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Department cannot be empty"
            );
        }

        if (employee.getSalary() == null ||
                employee.getSalary().compareTo(BigDecimal.ZERO) <= 0) {

            throw new IllegalArgumentException(
                    "Salary must be greater than zero"
            );
        }
    }

    // Employee ID Validation
    private void validateEmployeeId(Long employeeId) {

        if (employeeId == null || employeeId <= 0) {

            throw new IllegalArgumentException(
                    "Invalid employee ID"
            );
        }
    }
}
