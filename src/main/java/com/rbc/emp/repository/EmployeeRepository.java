package com.rbc.emp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rbc.emp.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	
	
	
}

