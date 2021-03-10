package com.epam.brest.model.service.web_app;

import com.epam.brest.model.Department;
import com.epam.brest.service.impl.DepartmentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/departments")
public class DepartmentController {

    private final DepartmentServiceImpl departmentServiceImpl;

    @Autowired
    DepartmentController(DepartmentServiceImpl departmentServiceImpl){
        this.departmentServiceImpl = departmentServiceImpl;
    }

    @GetMapping
    public final String departments(Model model) {
        model.addAttribute("departments", departmentServiceImpl.findWithAverageSalary());
        return "departments";
    }

    @GetMapping("/new")
    public final String goToCreateDepartmentPage(Model model) {
        model.addAttribute("tittle","Creat new department");
        model.addAttribute(new Department());
        model.addAttribute("method","POST");
        return "department";
    }

    @PostMapping
    public final String create(@ModelAttribute Department department){
        departmentServiceImpl.create(department);
        return "redirect:/departments";
    }

    @GetMapping("{id}/edit")
    public final String goToEditDepartmentPage(@PathVariable Integer id,
                                               Model model) {
        model.addAttribute("tittle","Edit department");
        Department department = departmentServiceImpl.findById(id).orElseThrow();
        model.addAttribute(department);
/*        model.addAttribute("url","/" + department.getDepartmentId());*/
        model.addAttribute("method","PUT");
        return "department";
    }

    @PutMapping("/{id}")
    public final String create(@PathVariable Integer id,
                               @ModelAttribute Department department){
        department.setDepartmentId(id);
        departmentServiceImpl.update(department);
        return "redirect:/departments";
    }

    @DeleteMapping("/{id}")
    public final String delete (@PathVariable Integer id){
        departmentServiceImpl.delete(id);
        return "redirect:/departments";
    }
}
