package com.epam.brest.dao;

import com.epam.brest.model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeDao {

    List<Employee> findAll();

    Optional<Employee> findById(Integer employeeId);

    Integer create(Employee employee);

    Integer update(Employee employee);

    Integer delete(Integer employeeId);

}
