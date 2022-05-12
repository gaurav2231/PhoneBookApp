package com.phone.book.entity;


public class RegisterResponse {
	
	//@JsonProperty("message")
	private String message;
	
	//@JsonProperty("code")
	private int code;
	
	
	
	//@JsonProperty("statusCode")
    private int statusCode;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	
	
	

}
