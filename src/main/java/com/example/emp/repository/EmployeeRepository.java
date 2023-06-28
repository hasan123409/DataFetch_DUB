package com.example.emp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.emp.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
