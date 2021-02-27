package com.epam.brest.dao.jdbc;

import com.epam.brest.Department;
import com.epam.brest.dao.DepartmentDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-dao.xml","classpath:test-db.xml"})
public class DepartmentDaoJdbcTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentDaoJdbcTest.class);

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
        assertEquals(newDepartment.getDepartmentName(),departmentDao.findById(4).get().getDepartmentName());
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:init-test-db.sql",
                    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:clean-up-test-db.sql",
                    executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    })
    public void deleteTest() {
        assertEquals(Integer.valueOf(3),departmentDao.delete(3));
        assertEquals(2,departmentDao.findAll().size());
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:init-test-db.sql",
                    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:clean-up-test-db.sql",
                    executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    })
    public void updateTest() {
        Department departmentForUpdating = new Department("Updated department");
        departmentForUpdating.setDepartmentId(3);
        assertEquals(Integer.valueOf(3),departmentDao.update(departmentForUpdating));
        assertEquals(departmentForUpdating.getDepartmentName(),departmentDao.findById(3).get().getDepartmentName());
    }

}