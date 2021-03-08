package com.epam.brest.dao.jdbc;

import com.epam.brest.dao.EmployeeDao;
import com.epam.brest.model.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-dao.xml")
public class EmployeeDaoJdbcTest {

    @Autowired
    private EmployeeDao employeeDao;

    @Test
    @SqlGroup({
            @Sql(value = "classpath:init-test-db.sql",
                    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:clean-up-test-db.sql",
                    executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    })
    public void findAll() {
        List<Employee> resultList = employeeDao.findAll();
        assertNotNull(resultList);
        assertEquals(6,resultList.size());
        System.out.println(resultList);
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:init-test-db.sql",
                    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:clean-up-test-db.sql",
                    executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    })
    public void findById() {
        assertEquals(Integer.valueOf(4), employeeDao.findById(4).orElseThrow().getEmployeeId());
        assertFalse(employeeDao.findById(999).isPresent());
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:init-test-db.sql",
                    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:clean-up-test-db.sql",
                    executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    })
    public void createTest() {
         Employee employee = new Employee("Anna", "Milashuk", "anna@mail.ru",6000d,1);
         assertEquals(Integer.valueOf(7),employeeDao.create(employee));
         assertEquals(7,employeeDao.findAll().size());
         assertEquals(employee.getFirstName(),employeeDao.findById(7).orElseThrow().getFirstName());
    }

    @Test(expected = IllegalArgumentException.class)
    @SqlGroup({
            @Sql(value = "classpath:init-test-db.sql",
                    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:clean-up-test-db.sql",
                    executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    })
    public void createShouldThrowExceptionIfNonNullFieldsIsNull() {
        Employee employee = new Employee(null, "Milashuk", "anna@mail.ru",3000d,1);
        employeeDao.create(employee);
    }

    @Test(expected = IllegalArgumentException.class)
    @SqlGroup({
            @Sql(value = "classpath:init-test-db.sql",
                    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:clean-up-test-db.sql",
                    executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    })
    public void createShouldThrowExceptionIfNotUnique() {
        Employee employee = new Employee("Bob", "Fisher", "anna@mail.ru",6000d,1);
        employeeDao.create(employee);
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:init-test-db.sql",
                    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:clean-up-test-db.sql",
                    executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    })
    public void updateTest() {
        Employee employee = new Employee("Mike","Bronson",null,6566d,null);
        employee.setEmployeeId(3);
        assertEquals(Integer.valueOf(1),employeeDao.update(employee));
        assertEquals(employee.getFirstName(),employeeDao.findById(employee.getEmployeeId()).orElseThrow().getFirstName());
        employee = new Employee("Bill","Bronson",null,6566d,null);
        assertEquals(Integer.valueOf(0),employeeDao.update(employee));
    }

    @Test(expected = IllegalArgumentException.class)
    @SqlGroup({
            @Sql(value = "classpath:init-test-db.sql",
                    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:clean-up-test-db.sql",
                    executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    })
    public void updateShouldThrowExceptionIfNonNullFieldsIsNull() {
        Employee employee = new Employee("Anna", null, "anna@mail.ru",3000d,1);
        employeeDao.update(employee);
    }

    @Test(expected = IllegalArgumentException.class)
    @SqlGroup({
            @Sql(value = "classpath:init-test-db.sql",
                    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:clean-up-test-db.sql",
                    executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    })
    public void updateShouldThrowExceptionIfNotUnique() {
        Employee employee = new Employee("Kate", "Ross", "anna@mail.ru",6000d,1);
        employeeDao.update(employee);
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:init-test-db.sql",
                    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:clean-up-test-db.sql",
                    executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    })
    public void delete() {
        assertEquals(Integer.valueOf(1),employeeDao.delete(2));
        assertEquals(5,employeeDao.findAll().size());
        assertEquals(Integer.valueOf(0),employeeDao.delete(999));
    }
}