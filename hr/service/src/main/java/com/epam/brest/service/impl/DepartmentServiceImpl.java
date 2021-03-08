package com.epam.brest.service.impl;


import com.epam.brest.model.Department;
import com.epam.brest.dao.DepartmentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.epam.brest.service.DepartmentService;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {

//    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentServiceImpl.class);

    private final DepartmentDao departmentDao;

    @Autowired
    public DepartmentServiceImpl(DepartmentDao departmentDao) {
        this.departmentDao = departmentDao;
    }

    @Override
    public List<Department> findAll() {
        return departmentDao.findAll();
    }

    @Override
    public Map<Department, Double> findWithAverageSalary() {
        return departmentDao.findWithAverageSalary();
    }

    @Override
    public Optional<Department> findById(Integer id) {
        return departmentDao.findById(id);
    }

    @Override
    public Integer create(Department department) {
        return departmentDao.create(department);
    }

    @Override
    public Integer update(Department department) {
        return departmentDao.update(department);
    }

    @Override
    public Integer delete(Integer departmentId) {
        return departmentDao.delete(departmentId);
    }
}
