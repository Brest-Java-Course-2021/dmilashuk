package com.epam.brest.dao.jdbc;

import com.epam.brest.model.Department;
import com.epam.brest.dao.DepartmentDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class DepartmentDaoJdbc implements DepartmentDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentDaoJdbc.class);

    @Value("${sql.getAllDepartments}")
    private String sqlGetAllDepartments;

    @Value("${sql.getAllDepartmentsWithAverageSalaries}")
    private String sqlGetAllDepartmentsWithAverageSalaries;

    @Value("${sql.findDepartmentById}")
    private String sqlFindDepartmentById;

    @Value("${sql.createDepartment}")
    private String sqlCreateDepartment;

    @Value("${sql.deleteDepartment}")
    private String sqlDeleteDepartment;

    @Value("${sql.updateDepartment}")
    private String sqlUpdateDepartment;

    @Value("${sql.checkingDepartmentName}")
    private String sqlCheckingDepartmentName;

    @Value("${sql.checkingThatDepartmentIsEmpty}")
    private String sqlCheckingThatDepartmentIsEmpty;

    private final NamedParameterJdbcTemplate template;

    private final RowMapper<Department> departmentRowMapper = BeanPropertyRowMapper.newInstance(Department.class);

    DepartmentDaoJdbc(NamedParameterJdbcTemplate template){
        this.template = template;
    }

    @Override
    public List<Department> findAll() {
        LOGGER.debug("DepartmentDaoJdbc: findAll");
        return template.query(sqlGetAllDepartments, departmentRowMapper);
    }

    @Override
    public Map<Department, Double> findWithAverageSalary(){
        Map<Department,Double> resultMap = new HashMap<>();
        template.query(sqlGetAllDepartmentsWithAverageSalaries,new DepartmentWithAverageSalaryRowMapper())
                .forEach(x -> resultMap.put(x.getKey(), x.getValue()));
        return resultMap;
    }

    @Override
    public Optional<Department> findById(Integer departmentId){
        LOGGER.debug("DepartmentDaoJdbc: findById({}})",departmentId);
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("DEPARTMENT_ID", departmentId);
        return Optional.ofNullable(DataAccessUtils.uniqueResult(template.query(sqlFindDepartmentById,sqlParameterSource, departmentRowMapper)));
    }

    @Override
    public Integer create (Department department) {

        checkingThatDepartmentNameUniqueAndNotNull(department);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("DEPARTMENT_NAME", department.getDepartmentName());
        template.update(sqlCreateDepartment, sqlParameterSource, keyHolder);

        Integer departmentId = Objects.requireNonNull(keyHolder.getKey()).intValue();
        department.setDepartmentId(departmentId);
        LOGGER.debug("DepartmentDaoJdbc: create({})",department);
        return departmentId;
    }

    @Override
    public Integer delete(Integer department_id) {

        checkingThatDepartmentDoNotHaveEmployees(department_id);

        MapSqlParameterSource parameterSource = new MapSqlParameterSource("DEPARTMENT_ID",department_id);

        LOGGER.debug("DepartmentDaoJdbc: delete({})",department_id);
        return template.update(sqlDeleteDepartment, parameterSource);
    }

    @Override
    public Integer update(Department department){

        checkingThatDepartmentNameUniqueAndNotNull(department);

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("DEPARTMENT_NAME",department.getDepartmentName());
        parameterSource.addValue("DEPARTMENT_ID",department.getDepartmentId());

        LOGGER.debug("DepartmentDaoJdbc: update({})",department);
        return template.update(sqlUpdateDepartment,parameterSource);
    }

    private void checkingThatDepartmentNameUniqueAndNotNull(Department department) {

        if(department.getDepartmentName() == null ){
            LOGGER.warn("Department's field departmentName may not be null");
            throw new  IllegalArgumentException("Department's field departmentName may not be null");
        }

        boolean checkThatDepartmentNameUnique = template.queryForObject(sqlCheckingDepartmentName,
                new MapSqlParameterSource("DEPARTMENT_NAME", department.getDepartmentName()), Integer.class) == 0;

        if (!checkThatDepartmentNameUnique){
            LOGGER.warn("The same department is already exist {}", department);
            throw new IllegalArgumentException("Department is already exist:" + department);
        }
    }

    private void checkingThatDepartmentDoNotHaveEmployees(Integer departmentId){
        boolean checkingThatDepartmentDoNotHaveEmployees = template.queryForObject(sqlCheckingThatDepartmentIsEmpty,
                new MapSqlParameterSource("DEPARTMENT_ID", departmentId),Integer.class) == 0;
        if(!checkingThatDepartmentDoNotHaveEmployees){
            LOGGER.warn("The department is not empty. Department id: {}", departmentId);
            throw new IllegalArgumentException("Department is not empty");
        }
    }

    private static class DepartmentWithAverageSalaryRowMapper implements RowMapper<AbstractMap.SimpleEntry<Department,Double>>{
        @Override
        public AbstractMap.SimpleEntry<Department,Double> mapRow(ResultSet resultSet, int i) throws SQLException {
            Department department = new Department();
            department.setDepartmentId(resultSet.getInt("DEPARTMENT_ID"));
            department.setDepartmentName(resultSet.getString("DEPARTMENT_NAME"));
            Double averageSalary = resultSet.getDouble("AVG");
            return new AbstractMap.SimpleEntry<>(department,averageSalary);
        }
    }

}
