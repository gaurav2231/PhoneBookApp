package com.phone.book.entity;


import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.Temporal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties({ "created","updated","expire"})


@Entity
@Table(name="otpDetails")
@EntityListeners(AuditingEntityListener.class)
public class OtpDetails {

	@Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name= "id")
	private int id;
   
	
	@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userid", referencedColumnName = "id")
    private User user;

	
	 @Column(name= "otp")
     private String otp;
    
	

    @CreatedDate
    @Column(name = "created", updatable=false , nullable = false)
	private Date created;
	
	//Date newDate = new Date(created.getTime() + 20*60*1000);
	 
	 @Column(name = "expire") 
     private Date expire = new Date(System.currentTimeMillis()+(1000*60*20));
	 
	 @LastModifiedDate
	 @Column(name = "updated" ,updatable = false , nullable = false)
	 private Date updated;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getExpire() {
		return expire;
	}

	public void setExpire(Date expire) {
		this.expire = expire;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	@Override
	public String toString() {
		return "OtpDetails [id=" + id + ", user_Id=" + user + ", otp=" + otp + ", created=" + created + ", expire="
				+ expire + ", updated=" + updated + "]";
	}

	public OtpDetails(int id, User user, String otp, Date created, Date expire, Date updated) {
		super();
		this.id = id;
		this.otp = otp;
		this.created = created;
		this.expire = expire;
		this.updated = updated;
		this.user=user;
	}


public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

public OtpDetails() {}
	
	
	
	
	
	
	
	
	
	
}