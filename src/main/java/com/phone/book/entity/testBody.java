package com.phone.book.entity;

public class testBody {

	private String countryCode;
	private String phoneNumber;
	private String otp;
	private String token;
	
	public testBody(String token) {
		super();
		this.token = token;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public testBody() {
		super();
	}
	public testBody(String countryCode, String phoneNumber, String otp) {
		super();
		this.countryCode = countryCode;
		this.phoneNumber = phoneNumber;
		this.otp = otp;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
	
	
}
