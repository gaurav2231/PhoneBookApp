package com.phone.book.entity;

public class EditContactResponse {
	private String message;
	private int code;
	private int statuscode;
	private Contacts contact;

	
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
	public int getStatuscode() {
		return statuscode;
	}
	public void setStatuscode(int statuscode) {
		this.statuscode = statuscode;
	}
	public Contacts getContact() {
		return contact;
	}
	public void setContact(Contacts contact) {
		this.contact = contact;
	}
	
}
