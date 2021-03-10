package com.epam.brest.model.service.web_app;

import com.epam.brest.model.Employee;
import com.epam.brest.service.impl.DepartmentServiceImpl;
import com.epam.brest.service.impl.EmployeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeServiceImpl employeeService;
    private final DepartmentServiceImpl departmentService;

    @Autowired
    public EmployeeController(EmployeeServiceImpl employeeService, DepartmentServiceImpl departmentService) {
        this.employeeService = employeeService;
        this.departmentService = departmentService;
    }

    @GetMapping
    public final String employees(Model model) {
        model.addAttribute("employees", employeeService.findAll());
        return "employees";
    }

    @GetMapping("/new")
    public final String goToCreateEmployeePage(Model model) {
        model.addAttribute("tittle","Creat new employee");
        model.addAttribute(new Employee());
        model.addAttribute("method","POST");
        model.addAttribute("departments",departmentService.findAll());
        return "employee";
    }

    @PostMapping
    public final String create(@ModelAttribute Employee employee){
        employeeService.create(employee);
        return "redirect:/employees";
    }

    @GetMapping("{id}/edit")
    public final String goToEditDepartmentPage(@PathVariable Integer id,
                                               Model model) {
        model.addAttribute("tittle","Edit employee");
        Employee employee = employeeService.findById(id).orElseThrow();
        model.addAttribute(employee);
        model.addAttribute("method","PUT");
        model.addAttribute("myDepartment",departmentService.findById(employee.getDepartmentId()).orElseThrow());
        return "employee";
    }

    @PutMapping("/{id}")
    public final String create(@PathVariable Integer id,
                               @ModelAttribute Employee employee){
        employee.setEmployeeId(id);
        employeeService.update(employee);
        System.out.println(employeeService.findAll());
        return "redirect:/employees";
    }

    @DeleteMapping("/{id}")
    public final String delete (@PathVariable Integer id){
        employeeService.delete(id);
        return "redirect:/employees";
    }
}
