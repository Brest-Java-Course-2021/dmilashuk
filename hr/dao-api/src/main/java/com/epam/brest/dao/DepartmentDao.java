package com.epam.brest.dao;

import com.epam.brest.model.Department;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface DepartmentDao {

    List<Department> findAll();

    Map<Department, Double> findWithAverageSalary();

    Optional<Department> findById(Integer departmentId);

    Integer create(Department department);

    Integer update(Department department);

    Integer delete(Integer departmentId);

}
