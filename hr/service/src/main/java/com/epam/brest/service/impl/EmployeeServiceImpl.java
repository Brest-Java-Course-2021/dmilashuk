package com.epam.brest.service.impl;

import com.epam.brest.dao.EmployeeDao;
import com.epam.brest.model.Employee;
import com.epam.brest.service.EmployeeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    EmployeeDao employeeDao;

    @Override
    public List<Employee> findAll() {
        return employeeDao.findAll();
    }

    @Override
    public Optional<Employee> findById(Integer employeeId) {
        return employeeDao.findById(employeeId);
    }

    @Override
    public Integer create(Employee employee) {
        return employeeDao.create(employee);
    }

    @Override
    public Integer update(Employee employee) {
        return employeeDao.update(employee);
    }

    @Override
    public Integer delete(Integer employeeId) {
        return employeeDao.delete(employeeId);
    }
}
