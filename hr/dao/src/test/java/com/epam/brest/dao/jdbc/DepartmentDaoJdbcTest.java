package com.epam.brest.dao.jdbc;

import com.epam.brest.dao.DepartmentDao;
import com.epam.brest.model.Department;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-dao.xml")
public class DepartmentDaoJdbcTest {

    @Autowired
    private DepartmentDao departmentDao;

    @Test
    @SqlGroup({
            @Sql(value = "classpath:init-test-db.sql",
                    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:clean-up-test-db.sql",
                    executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    })
    public void findAllTest() {
        List<Department> departments = departmentDao.findAll();
        assertNotNull(departments);
        assertTrue(departments.size() > 0);
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:init-test-db.sql",
                    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:clean-up-test-db.sql",
                    executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    })
    public void findWithAverageSalaryTest(){
        Map<Department, Double> resultMap = departmentDao.findWithAverageSalary();
        assertNotNull(resultMap);
        assertEquals(3,resultMap.size());
        Optional<Department> department3 = resultMap.keySet().stream().filter(x -> x.getDepartmentId() == 3).findFirst();
        assertTrue(department3.isPresent());
        assertEquals(Double.valueOf(2000),resultMap.get(department3.get()));
        Optional<Department> department2 = resultMap.keySet().stream().filter(x -> x.getDepartmentId() == 2).findFirst();
        assertTrue(department2.isPresent());
        assertEquals(Double.valueOf(0),resultMap.get(department2.get()));
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:init-test-db.sql",
                    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:clean-up-test-db.sql",
                    executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    })
    public void findByIdTest() {
        assertEquals(Integer.valueOf(2), departmentDao.findById(2).orElseThrow().getDepartmentId());
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:init-test-db.sql",
                    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:clean-up-test-db.sql",
                    executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    })
    public void createTest() {
        Department newDepartment = new Department("New department");
        assertEquals(Integer.valueOf(4), departmentDao.create(newDepartment));
        assertEquals(4,departmentDao.findAll().size());
        assertEquals(newDepartment.getDepartmentName(),departmentDao.findById(4).orElseThrow().getDepartmentName());
    }

    @Test(expected = IllegalArgumentException.class)
    @SqlGroup({
            @Sql(value = "classpath:init-test-db.sql",
                    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:clean-up-test-db.sql",
                    executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    })
    public void createTestShouldThrowExceptionWithNullDepartmentName() {
        Department newDepartment = new Department();
        departmentDao.create(newDepartment);
    }

    @Test(expected = IllegalArgumentException.class)
    @SqlGroup({
            @Sql(value = "classpath:init-test-db.sql",
                    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:clean-up-test-db.sql",
                    executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    })
    public void createTestShouldThrowExceptionWithNotUniqueDepartmentName() {
        Department newDepartment = new Department("HR");
        departmentDao.create(newDepartment);
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:init-test-db.sql",
                    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:clean-up-test-db.sql",
                    executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    })
    public void deleteTest() {
        assertEquals(Integer.valueOf(1),departmentDao.delete(2));
        assertEquals(2,departmentDao.findAll().size());
        assertEquals(Integer.valueOf(0),departmentDao.delete(999));
    }

    @Test(expected = IllegalArgumentException.class)
    @SqlGroup({
            @Sql(value = "classpath:init-test-db.sql",
                    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:clean-up-test-db.sql",
                    executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    })
    public void deleteTestShouldThrowException() {
        assertEquals(Integer.valueOf(1),departmentDao.delete(3));
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:init-test-db.sql",
                    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:clean-up-test-db.sql",
                    executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    })
    public void updateTest() {
        Department departmentForUpdating = new Department("Updated department 1");
        departmentForUpdating.setDepartmentId(3);
        assertEquals(Integer.valueOf(1),departmentDao.update(departmentForUpdating));
        assertEquals(departmentForUpdating.getDepartmentName(),departmentDao.findById(3).orElseThrow().getDepartmentName());
        departmentForUpdating = new Department("Updated department 2");
        departmentForUpdating.setDepartmentId(9999);
        assertEquals(Integer.valueOf(0),departmentDao.update(departmentForUpdating));
    }

    @Test(expected = IllegalArgumentException.class)
    @SqlGroup({
            @Sql(value = "classpath:init-test-db.sql",
                    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:clean-up-test-db.sql",
                    executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    })
    public void updateTestShouldThrowExceptionWithNullDepartmentName() {
        Department newDepartment = new Department();
        departmentDao.update(newDepartment);
    }

    @Test(expected = IllegalArgumentException.class)
    @SqlGroup({
            @Sql(value = "classpath:init-test-db.sql",
                    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:clean-up-test-db.sql",
                    executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    })
    public void updateTestShouldThrowExceptionWithNotUniqueDepartmentName() {
        Department newDepartment = new Department("HR");
        departmentDao.update(newDepartment);
    }

}