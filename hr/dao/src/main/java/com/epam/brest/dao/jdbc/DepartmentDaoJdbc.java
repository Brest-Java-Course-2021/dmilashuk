package com.epam.brest.dao.jdbc;

import com.epam.brest.Department;
import com.epam.brest.dao.DepartmentDao;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DepartmentDaoJdbc implements DepartmentDao {

    NamedParameterJdbcTemplate template;

    private static final String SQL_GET_DEPARTMENTS = "SELECT D.DEPARTMENT_ID,D.DEPARTMENT_NAME FROM DEPARTMENT AS d ORDER BY D.DEPARTMENT_NAME";

    private static final String SQL_FIND_DEPARTMENT_BY_ID = "SELECT * FROM DEPARTMENT WHERE DEPARTMENT_ID = :id";

    private static final String SQL_ADD_DEPARTMENT = "INSERT INTO DEPARTMENT (DEPARTMENT_NAME) VALUES (:department_name)";

    private static final String SQL_DELETE_DEPARTMENT = "DELETE FROM DEPARTMENT WHERE DEPARTMENT_ID = :id";

    private static final String SQL_UPDATE_DEPARTMENT = "UPDATE DEPARTMENT SET DEPARTMENT_NAME = :department_name WHERE DEPARTMENT_ID = :id";

    public DepartmentDaoJdbc(NamedParameterJdbcTemplate template){
        this.template = template;
    }

    @Override
    public List<Department> findAll() {
        return template.query(SQL_GET_DEPARTMENTS,new DepartmentRowMapper());
    }

    @Override
    public Department findById(int id){
        return template.queryForObject(SQL_FIND_DEPARTMENT_BY_ID,new MapSqlParameterSource("id",id), new DepartmentRowMapper());
    }

    @Override
    public boolean add (Department department) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource("department_name",department.getDepartmentName());
        try {
            template.update(SQL_ADD_DEPARTMENT, parameterSource);
        }catch (DataAccessException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteById(int department_id) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource("id",department_id);
        try {
            template.update(SQL_DELETE_DEPARTMENT,parameterSource);
        }catch (DataAccessException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean updateById(int department_id, Department department) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("department_name",department.getDepartmentName());
        parameterSource.addValue("id",department_id);
        try {
            template.update(SQL_UPDATE_DEPARTMENT,parameterSource);
        }catch (DataAccessException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private static class DepartmentRowMapper implements RowMapper<Department>{
        @Override
        public Department mapRow(ResultSet resultSet, int i) throws SQLException {
            Department department = new Department();
            department.setId(resultSet.getInt("DEPARTMENT_ID"));
            department.setDepartmentName(resultSet.getString("DEPARTMENT_NAME"));
            return department;
        }
    }

}
