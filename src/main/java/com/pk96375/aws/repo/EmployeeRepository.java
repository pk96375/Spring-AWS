package com.pk96375.aws.repo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.pk96375.aws.entity.Employee;

@Repository
public class EmployeeRepository {

	@Autowired
	private DynamoDBMapper dbMapper;

	public Employee save(Employee employee) {
		dbMapper.save(employee);
		return employee;
	}

	public Employee getEmployeeById(String employeeid) {
		return dbMapper.load(Employee.class, employeeid);
	}

	public String deleteEployee(String employeeId) {
		Employee emp = dbMapper.load(Employee.class, employeeId);
		dbMapper.delete(emp);
		return "Employee Deleted";
	}

	public String updateEmployee(String employeeId, Employee employee) {
		dbMapper.save(employee, new DynamoDBSaveExpression().withExpectedEntry("employeeid",
				new ExpectedAttributeValue(new AttributeValue().withS(employeeId))));
		return employeeId;
	}

	public List<Employee> getAll() {
		List<Employee> scanResults = dbMapper.scan(Employee.class, new DynamoDBScanExpression());
		return scanResults;
	}
}
