package com.epam.brest.service;

import com.epam.brest.model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    List<Employee> findAll();

    Optional<Employee> findById(Integer employeeId);

    Integer create(Employee employee);

    Integer update(Employee employee);

    Integer delete(Integer employeeId);

}
