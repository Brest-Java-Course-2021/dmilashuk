package com.epam.brest.dao.jdbc;

import com.epam.brest.Department;
import com.epam.brest.daoApi.DepartmentDao;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


public class DepartmentDaoJdbc implements DepartmentDao {

    private static final String SQL_GET_ALL_DEPARTMENTS =
            "SELECT D.DEPARTMENT_ID, D.DEPARTMENT_NAME FROM DEPARTMENT AS D ORDER BY D.DEPARTMENT_NAME";

    private static final String SQL_FIND_DEPARTMENT_BY_ID = "SELECT * FROM DEPARTMENT WHERE DEPARTMENT_ID = :DEPARTMENT_ID";

    private static final String SQL_CREATE_DEPARTMENT = "INSERT INTO DEPARTMENT (DEPARTMENT_NAME) VALUES (:DEPARTMENT_NAME)";

    private static final String SQL_DELETE_DEPARTMENT = "DELETE FROM DEPARTMENT WHERE DEPARTMENT_ID = :DEPARTMENT_ID";

    private static final String SQL_UPDATE_DEPARTMENT = "UPDATE DEPARTMENT SET DEPARTMENT_NAME = :DEPARTMENT_NAME WHERE DEPARTMENT_ID = :DEPARTMENT_ID";

    private final NamedParameterJdbcTemplate template;

    private final RowMapper<Department> rowMapper = BeanPropertyRowMapper.newInstance(Department.class);

    public DepartmentDaoJdbc(NamedParameterJdbcTemplate template){
        this.template = template;
    }

    @Override
    public List<Department> findAll() {
        return template.query(SQL_GET_ALL_DEPARTMENTS,rowMapper);
    }

    @Override
    public Optional<Department> findById(Integer departmentId){

        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("DEPARTMENT_ID", departmentId);
        return Optional.ofNullable(template.queryForObject(SQL_FIND_DEPARTMENT_BY_ID,sqlParameterSource, rowMapper));

    }

    @Override
    public Integer create (Department department) {

        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("DEPARTMENT_NAME", department.getDepartmentName());
        template.update(SQL_CREATE_DEPARTMENT, sqlParameterSource, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }

    @Override
    public Integer delete(Integer department_id) {

        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource parameterSource = new MapSqlParameterSource("DEPARTMENT_ID",department_id);
        template.update(SQL_DELETE_DEPARTMENT, parameterSource, keyHolder);

        return department_id;
    }

    @Override
    public Integer update(Department department){

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();

        parameterSource.addValue("DEPARTMENT_NAME",department.getDepartmentName());
        parameterSource.addValue("DEPARTMENT_ID",department.getDepartmentId());
        template.update(SQL_UPDATE_DEPARTMENT,parameterSource);

        return department.getDepartmentId();
    }
}
