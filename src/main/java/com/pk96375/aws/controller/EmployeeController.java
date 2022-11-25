package com.pk96375.aws.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.pk96375.aws.entity.Employee;
import com.pk96375.aws.repo.EmployeeRepository;

@RestController
public class EmployeeController {

	@Autowired
	EmployeeRepository employeeRepository;

	@PostMapping("/employee")
	public Employee save(@RequestBody Employee emp) {
		return employeeRepository.save(emp);
	}

	@GetMapping("/employee/{employeeId}")
	public Employee getEmployee(@PathVariable("employeeId") String employeeId) {
		return employeeRepository.getEmployeeById(employeeId);
	}

	@DeleteMapping("/employee/{employeeId}")
	public String deleteEmployee(@PathVariable("employeeId") String employeeId) {
		return employeeRepository.deleteEployee(employeeId);
	}

	@PutMapping("/employee/{employeeId}")
	public String updateEmployee(@PathVariable("employeeId") String employeeId, @RequestBody Employee employee) {
		return employeeRepository.updateEmployee(employeeId, employee);
	}

	@GetMapping("/employee/all")
	public List<Employee> getAllEmployees() {
		return employeeRepository.getAll();
	}
}
