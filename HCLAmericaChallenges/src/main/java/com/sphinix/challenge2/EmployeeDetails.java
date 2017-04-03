package com.sphinix.challenge2;

import java.util.HashMap;
import java.util.Map;

public class EmployeeDetails {

	public static void main(String[] args) {
		// Load dummy data
		Map<Integer, Employee> employeeMap = loadEmployeeData();
		
		for (Map.Entry<Integer, Employee> entry : employeeMap.entrySet()) {
		    System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
		}
		
	}
	
	public static Map<Integer, Employee> loadEmployeeData() {
		
		Employee e1 = new Employee(1, "John", 123456789l, "NJ");
		
		Employee e2 = new Employee(2, "Mikki", 123456789l, "NY");
		
		Employee e3 = new Employee(3, "Nancy", 123456789l, "PA");
		
		Map<Integer, Employee> employeeMap = new HashMap<Integer, Employee>();
		
		employeeMap.put(1,e1);
		
		employeeMap.put(2,e2);
		
		employeeMap.put(3,e3);
		
		return employeeMap;
	}

}
