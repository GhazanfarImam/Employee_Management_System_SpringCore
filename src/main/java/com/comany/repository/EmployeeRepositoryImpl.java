
package com.comany.repository;

import com.comany.entity.Employee;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class EmployeeRepositoryImpl implements EmployeeRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(Employee employee) {
        entityManager.persist(employee);
    }

    @Override
    public Optional<Employee> findById(Long employeeId) {

        Employee employee =
                entityManager.find(Employee.class, employeeId);

        return Optional.ofNullable(employee);
    }

    @Override
    public List<Employee> findAll() {

        String jpql = "SELECT e FROM Employee e";

        TypedQuery<Employee> query =
                entityManager.createQuery(jpql, Employee.class);

        return query.getResultList();
    }

    @Override
    public void update(Employee employee) {

        entityManager.merge(employee);
    }

    @Override
    public boolean deleteById(Long employeeId) {

        Employee employee =
                entityManager.find(Employee.class, employeeId);

        if (employee != null) {

            entityManager.remove(employee);

            return true;
        }

        return false;
    }


}
