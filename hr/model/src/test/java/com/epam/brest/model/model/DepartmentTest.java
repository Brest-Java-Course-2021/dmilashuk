package com.epam.brest.model.model;

import com.epam.brest.model.Department;
import org.junit.Test;

import static org.junit.Assert.*;

public class DepartmentTest {

    @Test
    public void getDepartmentNameConstructor() {
        Department department = new Department("IT");
        assertEquals("IT",department.getDepartmentName());
    }

    @Test
    public void getDepartmentNameSetter() {
        Department department = new Department("IT");
        department.setDepartmentName("HR");
        assertEquals("HR",department.getDepartmentName());
    }
}