package com.epam.brest.model;

public class DepartmentWithAverageSalary {

    Department department;

    Double averageSalary;

    public DepartmentWithAverageSalary() {
    }

    public DepartmentWithAverageSalary(Department department, Double averageSalary) {
        this.department = department;
        this.averageSalary = averageSalary;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Double getAverageSalary() {
        return averageSalary;
    }

    public void setAverageSalary(Double averageSalary) {
        this.averageSalary = averageSalary;
    }

    @Override
    public String toString() {
        return "DepartmentWithAverageSalary{" +
                "department=" + department +
                ", averageSalary=" + averageSalary +
                '}';
    }
}
