package com.epam.brest.model.service.web_app;

import com.epam.brest.service.impl.DepartmentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class DepartmentController {


    private final DepartmentServiceImpl departmentServiceImpl;

    @Autowired
    DepartmentController(DepartmentServiceImpl departmentServiceImpl){
        this.departmentServiceImpl = departmentServiceImpl;
    }

    /**
     * Goto departments list page.
     *
     * @return view name
     */
    @GetMapping(value = "/departments")
    public final String departments(Model model) {
        System.out.println("hHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH");
        model.addAttribute("departments", departmentServiceImpl.findAll());
        model.addAttribute("aa",Integer.valueOf(5));
        System.out.println(model.getAttribute("aa"));
        System.out.println(model.getAttribute("departments"));
        return "departments";
    }

    /**
     * Goto edit department page.
     *
     * @return view name
     */
    @GetMapping(value = "/department/{id}")
    public final String gotoEditDepartmentPage(@PathVariable Integer id, Model model) {
        return "department";
    }

    /**
     * Goto new department page.
     *
     * @return view name
     */
    @GetMapping(value = "/department/add")
    public final String gotoAddDepartmentPage(Model model) {
        return "department";
    }
}
