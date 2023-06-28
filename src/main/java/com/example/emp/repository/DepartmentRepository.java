package com.example.emp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.emp.model.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

	Department findTopByOrderByEmployeesSalaryDesc();

	Department findByName(String departmentName);
}
