package impl;


import com.epam.brest.Department;
import com.epam.brest.daoApi.DepartmentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.DepartmentService;

import java.util.List;
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
