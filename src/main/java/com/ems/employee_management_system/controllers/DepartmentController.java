package com.ems.employee_management_system.controllers;

import com.ems.employee_management_system.models.Department;
import com.ems.employee_management_system.repositories.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    @Autowired
    private DepartmentRepository departmentRepository;

    @GetMapping
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    @PostMapping
    public String addDepartment(@RequestBody Department department) {
        departmentRepository.save(department);
        return "Department added successfully!";
    }
}
