package com.epam.brest.dao.jdbc;

import com.epam.brest.dao.EmployeeDao;
import com.epam.brest.model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@PropertySource("classpath:dao.properties")
public class EmployeeDaoJdbc implements EmployeeDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeDaoJdbc.class);

    private final NamedParameterJdbcTemplate template;

    @Value("${sql.getAllEmployees}")
    private String sqlGetAllEmployees="SELECT * FROM EMPLOYEE ORDER BY EMPLOYEE_ID";

    @Value("${sql.findEmployeeById}")
    private String sqlFindEmployeeById="SELECT * FROM EMPLOYEE WHERE EMPLOYEE_ID = :EMPLOYEE_ID";

    @Value("${sql.createEmployee}")
    private String sqlCreateEmployee="INSERT INTO EMPLOYEE (FIRST_NAME,LAST_NAME,E_MAIL,SALARY,DEPARTMENT_ID) " +
            "VALUES (:FIRST_NAME,:LAST_NAME,:E_MAIL,:SALARY,:DEPARTMENT_ID)";

    @Value("${sql.updateEmployee}")
    private String sqlUpdateEmployee="UPDATE EMPLOYEE SET FIRST_NAME = :FIRST_NAME, LAST_NAME = :LAST_NAME," +
            " E_MAIL = :E_MAIL, SALARY = :SALARY, DEPARTMENT_ID = :DEPARTMENT_ID";

    @Value("${sql.deleteEmployee}")
    private String sqlDeleteEmployee ="DELETE FROM EMPLOYEE WHERE EMPLOYEE_ID = :EMPLOYEE_ID";

    @Value("${sql.checkingThatEmployeeIsValid}")
    private String sqlCheckingThatEmployeeIsValid="SELECT COUNT(EMPLOYEE_ID) FROM EMPLOYEE WHERE " +
            "lower(FIRST_NAME) = lower(:FIRST_NAME) AND lower(LAST_NAME) = lower(:LAST_NAME)";

    private final RowMapper<Employee> employeeRowMapper = BeanPropertyRowMapper.newInstance(Employee.class);

    EmployeeDaoJdbc(NamedParameterJdbcTemplate template){
        this.template = template;
    }

    @Override
    public List<Employee> findAll() {
        LOGGER.debug("EmployeeDaoJdbc: findAll()");
        return template.query(sqlGetAllEmployees,employeeRowMapper);
    }

    @Override
    public Optional<Employee> findById(Integer employeeId) {
        LOGGER.debug("EmployeeDaoJdbc: findById({})",employeeId);
        SqlParameterSource parameterSource = new MapSqlParameterSource("EMPLOYEE_ID",employeeId);
        return Optional.ofNullable(DataAccessUtils.uniqueResult(template.query(sqlFindEmployeeById, parameterSource, employeeRowMapper)));
    }

    @Override
    public Integer create(Employee employee) {

        checkingThatEmployeeIsValid(employee);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource parameterSource = new MapSqlParameterSource("FIRST_NAME",employee.getFirstName())
                .addValue("LAST_NAME", employee.getLastName())
                .addValue("E_MAIL", employee.geteMail())
                .addValue("SALARY", employee.getSalary())
                .addValue("DEPARTMENT_ID", employee.getDepartmentId());

        LOGGER.debug("EmployeeDaoJdbc: create({})",employee);
        template.update(sqlCreateEmployee, parameterSource,keyHolder);
        Integer employeeId = Objects.requireNonNull(keyHolder.getKey()).intValue();
        employee.setEmployeeId(employeeId);
        return employeeId;
    }

    @Override
    public Integer update(Employee employee) {

        checkingThatEmployeeIsValid(employee);

        SqlParameterSource parameterSource = new MapSqlParameterSource("FIRST_NAME", employee.getFirstName())
                .addValue("LAST_NAME", employee.getLastName())
                .addValue("E_MAIL", employee.geteMail())
                .addValue("SALARY", employee.getSalary())
                .addValue("DEPARTMENT_ID", employee.getDepartmentId())
                .addValue("EMPLOYEE_ID", employee.getEmployeeId());
        LOGGER.debug("EmployeeDaoJdbc: update({})",employee);
        return template.update(sqlUpdateEmployee, parameterSource);
    }

    @Override
    public Integer delete(Integer employeeId) {
        SqlParameterSource parameterSource = new MapSqlParameterSource("EMPLOYEE_ID", employeeId);
        LOGGER.debug("EmployeeDaoJdbc: delete({})",employeeId);
        return template.update(sqlDeleteEmployee,parameterSource);
    }

    private void checkingThatEmployeeIsValid(Employee employee){
        if (employee.getFirstName() == null || employee.getLastName() == null || employee.getSalary() == null){
            LOGGER.warn("Employee's fields firstName, lastName and salary may not be null");
            throw new  IllegalArgumentException("Employee's fields firstName, lastName and salary may not be null");
        }
        SqlParameterSource parameterSource = new MapSqlParameterSource("FIRST_NAME",employee.getFirstName())
                .addValue("LAST_NAME",employee.getLastName());
        boolean checkingThatEmployeeUnique = template.queryForObject(sqlCheckingThatEmployeeIsValid, parameterSource,Integer.class) == 0;
        if (!checkingThatEmployeeUnique){
            LOGGER.warn("The same Employee is already exist: {}",employee);
            throw new IllegalArgumentException("The same Employee is already exist:" + employee);
        }
    }
}
