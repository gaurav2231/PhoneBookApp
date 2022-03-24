package com.phone.book.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="User", uniqueConstraints = @UniqueConstraint(columnNames = {"phoneNumber"}))
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties({ "status", "created","updated","otpDetails","contacts"})

public class User {
	
	
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "id")
    private int id;
    
	@Column(name= "name")

    private String name;
	
	@Column(name= "email")
    private String email;
	
	@Column(name= "countryCode")
    private int countryCode;
  
	
	@Column(name= "phoneNumber", unique = true)
	private long phoneNumber;
	
	
	
	@Column(name= "passCode")
	private String passCode;
	
	@Column(name= "status")
    private int status = 0;
   
    @CreatedDate
    @Column(name = "created", updatable=false)
    private Date created;
    
    @LastModifiedDate
    @Column(name = "updated" ,updatable = false)
    private Date updated;
    
    @OneToOne(mappedBy = "user")
    private OtpDetails otpDetails;
    
    @OneToOne(mappedBy = "user")
  	 private Contacts contacts;

    
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

 public String getEmail() {
		return email;
	}

  public void setEmail(String email) {
		this.email = email;
	}

  public int getCountryCode() {
		return countryCode;
	}

    public void setCountryCode(int countryCode) {
		this.countryCode = countryCode;
	}

    public long getPhoneNumber() {
		return phoneNumber;
	}

   public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

   public String getPassCode() {
		return passCode;
	}

   public void setPassCode(String passCode) {
		this.passCode = passCode;
	}
   public int getStatus() {
		return status;
	}
   public void setStatus(int status) {
		this.status = status;
	}

  public Date getCreated() {
		return created;
	}
  public void setCreated(Date created) {
		this.created = created;
	}

   public Date getUpdated() {
		return updated;
	}
  public void setUpdated(Date updated) {
		this.updated = updated;
	}
  	  
	  public Contacts getContacts() { return contacts; }
	 
	 public void setContacts(Contacts contacts) { this.contacts = new Contacts();
	 }
	 

	public OtpDetails getOtpDetails() {
	return otpDetails;
}

public void setOtpDetails(OtpDetails otpDetails) {
	this.otpDetails = otpDetails;
}

	

  @Override
public String toString() {
	return "User [id=" + id + ", name=" + name + ", email=" + email + ", countryCode=" + countryCode + ", phoneNumber="
			+ phoneNumber + ", passCode=" + passCode + ", status=" + status + ", created=" + created + ", updated="
			+ updated + ", otpDetails=" + otpDetails + "]";
}

  
public User(int id, String name, String email, int countryCode, long phoneNumber, String passCode, int status,
		Date created, Date updated, OtpDetails otpDetails) {
	super();
	this.id = id;
	this.name = name;
	this.email = email;
	this.countryCode = countryCode;
	this.phoneNumber = phoneNumber;
	this.passCode = passCode;
	this.status = status;
	this.created = created;
	this.updated = updated;
	this.otpDetails = otpDetails;
}

public User() {}
   
}



    