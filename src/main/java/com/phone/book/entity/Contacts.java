package com.phone.book.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="contacts")
@JsonIgnoreProperties({ "user"})

public class Contacts {
	
	@Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name= "id")
	private int id;
	
	private int status;
	
	
	@Column(name= "name",nullable = false)
     private String name;
	
	@Size(min = 3, max = 10)
	@Column(name= "phoneNumber", nullable = false)
      private String phoneNumber;
	
	@Column(name= "countryCode", nullable = false)
    private int countryCode;
	
	@Email
	@NotBlank(message = "email is required")
	@Column(name= "email")
     private String email;
	
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userid", referencedColumnName = "id")
    private User user;

	
	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
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


	public String getPhoneNumber() {
		return phoneNumber;
	}


	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}


	public int getCountryCode() {
		return countryCode;
	}

	
	public void setCountryCode(int countryCode) {
		this.countryCode = countryCode;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public Contacts(int id, User userId, String name,int status, String phoneNumber, int countryCode, String email) {
		super();
		this.id = id;
		this.status = status;
		this.user = user;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.countryCode = countryCode;
		this.email = email;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}

	public Contacts(int id, int status, User user, String name, @Size(min = 3, max = 10) String phoneNumber,
			int countryCode, @Email @NotBlank(message = "email is required") String email) {
		super();
		this.id = id;
		this.status = status;
		this.user = user;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.countryCode = countryCode;
		this.email = email;
	}


	@Override
	public String toString() {
		return "Contacts [id=" + id + ", user=" + user + ", name=" + name + ", phoneNumber=" + phoneNumber
				+ ", countryCode=" + countryCode + ", email=" + email + "]";
	}

	@JsonIgnore
	public boolean isDeleted() {
		return this.getStatus()==2;
	}

	public Contacts() {}


	
}
	
	
	
	


