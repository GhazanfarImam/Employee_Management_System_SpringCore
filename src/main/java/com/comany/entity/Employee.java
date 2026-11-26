package com.comany.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name="employees")
public class Employee
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
   private Long employeeId;
    @Column(name = "name", nullable = false,length = 100)
   private String name;
    @Column(name = "email", nullable = false, unique = true, length = 100)
   private String email;
    @Column(name = "department", nullable = false,length = 100)
   private String department;
    @Column(name = "salary", nullable = false, precision = 12, scale = 2)
    //Precision = 12 total digits.
    //Scale = 2 digits after the decimal point.
   private BigDecimal salary;

    public Employee() {

    }

    public Employee(Long employeeId, String name, String email, String department, BigDecimal salary) {
        this.employeeId = employeeId;
        this.name = name;
        this.email = email;
        this.department = department;
        this.salary = salary;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId=" + employeeId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", department='" + department + '\'' +
                ", salary=" + salary +
                '}';
    }
}
