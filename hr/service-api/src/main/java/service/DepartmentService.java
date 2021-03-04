package service;



import com.epam.brest.Department;

import java.util.List;
import java.util.Optional;

public interface DepartmentService {

    List<Department> findAll();

    Optional<Department> findById(Integer departmentId);

    Integer create(Department department);

    Integer update(Department department);

    Integer delete(Integer departmentId);
}
