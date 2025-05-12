package com.ems.employee_management_system.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.ems.employee_management_system.dtos.ProjectDTO;
import com.ems.employee_management_system.dtos.TaskDTO;
import com.ems.employee_management_system.mappers.ProjectMapper;
import com.ems.employee_management_system.mappers.TaskMapper;
import com.ems.employee_management_system.models.Department;
import com.ems.employee_management_system.models.Employee;
import com.ems.employee_management_system.models.Project;
import com.ems.employee_management_system.services.DepartmentService;
import com.ems.employee_management_system.services.EmployeeService;
import com.ems.employee_management_system.services.ProjectService;
import com.ems.employee_management_system.services.TaskService;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    private final ProjectService projectService;
    private final DepartmentService departmentService;
    private final EmployeeService employeeService;
    private final TaskService taskService;

    public ProjectController(ProjectService projectService, DepartmentService departmentService, EmployeeService employeeService, TaskService taskService) {
        this.projectService = projectService;
        this.departmentService = departmentService;
        this.employeeService = employeeService;
        this.taskService = taskService;
    }

    @GetMapping("/{id}/details")
    public Map<String, Object> getProjectWithTasks(@PathVariable UUID id) {
        Project project = projectService.getById(id);
        if (project == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found");
        }
        List<TaskDTO> tasks = taskService.getAll().stream()
            .filter(task -> task.getProject().getId().equals(id))
            .map(TaskMapper::toDTO)
            .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("project", ProjectMapper.toDTO(project));
        response.put("tasks", tasks);
        return response;
    }

    @GetMapping
    public List<ProjectDTO> getAll() {
        return projectService.getAll().stream()
                .map(ProjectMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ProjectDTO getById(@PathVariable UUID id) {
        Project project = projectService.getById(id);
        return project != null ? ProjectMapper.toDTO(project) : null;
    }

    @PostMapping
    public ProjectDTO create(@RequestBody ProjectDTO dto) {
        Department department = departmentService.getById(dto.getDepartmentId());
        Employee manager = dto.getProjectManagerId() != null ? employeeService.getById(dto.getProjectManagerId()) : null;
        Project project = ProjectMapper.toEntity(dto, department, manager);
        return ProjectMapper.toDTO(projectService.save(project));
    }

    @PutMapping("/{id}")
    public ProjectDTO update(@PathVariable UUID id, @RequestBody ProjectDTO dto) {
        Department department = departmentService.getById(dto.getDepartmentId());
        Employee manager = dto.getProjectManagerId() != null ? employeeService.getById(dto.getProjectManagerId()) : null;
        Project project = ProjectMapper.toEntity(dto, department, manager);
        project.setId(id);
        return ProjectMapper.toDTO(projectService.save(project));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        projectService.delete(id);
    }
} 