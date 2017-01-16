package com.example.app.jobapp.shared;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 * for storing applicant information
 * @author xiaobin
 *
 */
public class ApplicantInfo implements Serializable {
	private String name = "";
	private String gender = "";
	private String email = "";
	private String phone = "";
	private Set<String> positions = new HashSet<>();
	private String degree = "";
	private String cv = "";

	//0-arg constructor
	public ApplicantInfo() {
		
	}
	
	//constructor
	public ApplicantInfo(String name, String gender, String email, String phone, Set<String> positions, String degree,
			String cv) {
		super();
		this.name = name;
		this.gender = gender;
		this.email = email;
		this.phone = phone;
		this.positions = positions;
		this.degree = degree;
		this.cv = cv;
	}

	//getters and setters
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Set<String> getPositions() {
		return positions;
	}
	public void setPositions(Set<String> positions) {
		this.positions = positions;
	}
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
	public String getCv() {
		return cv;
	}
	public void setCv(String cv) {
		this.cv = cv;
	}
	
}
