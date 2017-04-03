/**
 * 
 */
package com.sphinix.challenge2;

/**
 * @author Vardhan
 *
 */
public class Employee {
	
	private int id;
	
	private String name;
	
	private long phone;
	
	private String address;
	
	

	public Employee(int id, String name, long phone, String address) {
		super();
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.address = address;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getPhone() {
		return phone;
	}

	public void setPhone(long phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	
}
