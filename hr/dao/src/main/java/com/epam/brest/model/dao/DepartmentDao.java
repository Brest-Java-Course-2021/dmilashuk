package com.epam.brest.model.dao;

import com.epam.brest.Department;

import java.util.List;

public interface DepartmentDao {
    List<Department> findAll();
}
