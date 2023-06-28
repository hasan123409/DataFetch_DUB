package com.example.emp.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.emp.model.Department;
import com.example.emp.model.Employee;
import com.example.emp.repository.DepartmentRepository;
import com.example.emp.repository.EmployeeRepository;

@RestController
@RequestMapping("/api")
public class EmployeeController {
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    public EmployeeController(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
    }

    @PostMapping("/employees")
    public ResponseEntity<String> createEmployee(@RequestBody Employee employee,
                                                 @RequestParam(required = false) String departmentName) {
        if (departmentName != null && !departmentName.isEmpty()) {
            Department existingDepartment = departmentRepository.findByName(departmentName);
            if (existingDepartment != null) {
                employee.setDepartment(existingDepartment);
            } else {
                Department newDepartment = new Department();
                newDepartment.setName(departmentName);
                newDepartment.setEmployees(Collections.singletonList(employee));
                departmentRepository.save(newDepartment);
            }
        }

        employeeRepository.save(employee);

        return ResponseEntity.status(HttpStatus.CREATED).body("Employee created successfully.");
    }

    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/departments/max-salary")
    public ResponseEntity<Department> getDepartmentWithMaxSalary() {
        Department department = departmentRepository.findTopByOrderByEmployeesSalaryDesc();
        return ResponseEntity.ok(department);
    }
}
