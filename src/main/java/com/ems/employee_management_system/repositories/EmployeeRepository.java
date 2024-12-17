package com.ems.employee_management_system.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ems.employee_management_system.models.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}
