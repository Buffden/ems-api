package com.ems.employee_management_system.controllers;

import com.ems.employee_management_system.models.Employee;
import com.ems.employee_management_system.models.Department;
import com.ems.employee_management_system.repositories.EmployeeRepository;
import com.ems.employee_management_system.repositories.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    // Get all employees
    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    // Add a new employee
    @PostMapping
    public String addEmployee(@RequestBody Employee employee) {
        if (employee.getDepartment() == null || employee.getDepartment().getId() == null) {
            throw new RuntimeException("Department is required for the employee!");
        }

        // Verify if the department exists
        Optional<Department> department = departmentRepository.findById(employee.getDepartment().getId());
        if (department.isEmpty()) {
            throw new RuntimeException("Invalid Department ID!");
        }

        // Set the department and save
        employee.setDepartment(department.get());
        employeeRepository.save(employee);
        return "Employee added successfully!";
    }

    // Get employee by ID
    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable UUID id) {
        return employeeRepository.findById(id).orElseThrow(() -> new RuntimeException("Employee not found with ID: " + id));
    }

    // Update an employee
    @PutMapping("/{id}")
    public String updateEmployee(@PathVariable UUID id, @RequestBody Employee updatedEmployee) {
        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        if (employeeOptional.isEmpty()) {
            throw new RuntimeException("Employee not found with ID: " + id);
        }

        Employee existingEmployee = employeeOptional.get();
        existingEmployee.setName(updatedEmployee.getName());
        existingEmployee.setAddress(updatedEmployee.getAddress());
        existingEmployee.setEmail(updatedEmployee.getEmail());
        existingEmployee.setPhone(updatedEmployee.getPhone());
        existingEmployee.setDesignation(updatedEmployee.getDesignation());
        existingEmployee.setSalary(updatedEmployee.getSalary());
        existingEmployee.setJoiningDate(updatedEmployee.getJoiningDate());

        // Update department if provided
        if (updatedEmployee.getDepartment() != null && updatedEmployee.getDepartment().getId() != null) {
            Optional<Department> department = departmentRepository.findById(updatedEmployee.getDepartment().getId());
            if (department.isEmpty()) {
                throw new RuntimeException("Invalid Department ID!");
            }
            existingEmployee.setDepartment(department.get());
        }

        employeeRepository.save(existingEmployee);
        return "Employee updated successfully!";
    }

    // Delete an employee
    @DeleteMapping("/{id}")
    public String deleteEmployee(@PathVariable UUID id) {
        if (!employeeRepository.existsById(id)) {
            throw new RuntimeException("Employee not found with ID: " + id);
        }
        employeeRepository.deleteById(id);
        return "Employee deleted successfully!";
    }
}