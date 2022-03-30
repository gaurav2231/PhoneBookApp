package com.phone.book.entity;

import java.util.List;

public class ErrorResponse {
	private List<ErrorDetails> errors;

    public static class ErrorDetails {
        private String fieldName;
        private String message;
        private int code;
		private int statusCode;
		  
		  public int getCode() { 
			  return code; 
			  } 
		  public void setCode(int string) {
		  this.code = string;
		  }
		  public int getStatusCode() {
			  return statusCode;
			  }
		  public void setStatusCode(int statusCode) {
			  this.statusCode = statusCode; 
			  }
		  
		 
		public ErrorDetails() {
			super();
		}
		public ErrorDetails(String fieldName, String message) {
			super();
			this.fieldName = fieldName;
			this.message = message;
		}
		public String getFieldName() {
			return fieldName;
		}
		public String getMessage() {
			return message;
		}
		public void setFieldName(String field) {
			this.fieldName = field;
		}
		public void setMessage(String defaultMessage) {
			this.message = defaultMessage;
		}
		public void setCode(String code2) {
			// TODO Auto-generated method stub
			
		}
    }

    
	public ErrorResponse() {
		super();
		// TODO Auto-generated constructor stub
	}


	public ErrorResponse(List<ErrorDetails> errors) {
		super();
		this.errors = errors;
	}


	public List<ErrorDetails> getErrors() {
		return errors;
	}


	public void setErrors(List<ErrorDetails> errorDetails) {
		this.errors = errorDetails;
		
	}

}
