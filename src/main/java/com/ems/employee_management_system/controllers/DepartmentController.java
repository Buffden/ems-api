package com.ems.employee_management_system.controllers;

import com.ems.employee_management_system.models.Department;
import com.ems.employee_management_system.repositories.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    @GetMapping("/{id}")
    public Department getDepartmentById(@PathVariable UUID id) {
        return departmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Department not found with ID: " + id));
    }

    @PutMapping("/{id}")
    public String updateDepartment(@PathVariable UUID id, @RequestBody Department updatedDepartment) {
        Optional<Department> departmentOptional = departmentRepository.findById(id);
        if (departmentOptional.isEmpty()) {
            throw new RuntimeException("Department not found with ID: " + id);
        }

        Department existingDepartment = departmentOptional.get();
        existingDepartment.setName(updatedDepartment.getName());
        existingDepartment.setDescription(updatedDepartment.getDescription());
        existingDepartment.setTotalEmployees(updatedDepartment.getTotalEmployees());

        departmentRepository.save(existingDepartment);
        return "Department updated successfully!";
    }

    @DeleteMapping("/{id}")
    public String deleteDepartment(@PathVariable UUID id) {
        if (!departmentRepository.existsById(id)) {
            throw new RuntimeException("Department not found with ID: " + id);
        }
        departmentRepository.deleteById(id);
        return "Department deleted successfully!";
    }
}
