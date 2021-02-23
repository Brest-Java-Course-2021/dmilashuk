package com.epam.brest.dao;

import com.epam.brest.Department;

import java.util.List;

public interface DepartmentDao {
    List<Department> findAll();
    Department findById(int department_id);
    boolean add(Department department);
    boolean deleteById(int department_id);
    boolean updateById(int department_id, Department department);
}
