package com.ems.employee_management_system.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ems.employee_management_system.models.Department;
import com.ems.employee_management_system.models.Employee;
import com.ems.employee_management_system.repositories.DepartmentRepository;
import com.ems.employee_management_system.repositories.EmployeeRepository;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

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
        
        // Update basic information
        existingDepartment.setName(updatedDepartment.getName());
        existingDepartment.setDescription(updatedDepartment.getDescription());
        existingDepartment.setTotalEmployees(updatedDepartment.getTotalEmployees());

        // Update new fields
        existingDepartment.setLocation(updatedDepartment.getLocation());
        existingDepartment.setBudget(updatedDepartment.getBudget());
        existingDepartment.setBudgetUtilization(updatedDepartment.getBudgetUtilization());
        existingDepartment.setPerformanceMetric(updatedDepartment.getPerformanceMetric());

        // Update department head if provided
        if (updatedDepartment.getHead() != null && updatedDepartment.getHead().getId() != null) {
            Optional<Employee> head = employeeRepository.findById(updatedDepartment.getHead().getId());
            if (head.isEmpty()) {
                throw new RuntimeException("Invalid Department Head ID!");
            }
            existingDepartment.setHead(head.get());
        }

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
