package com.dozenx.web.core.rules;

public abstract class Rule{
	public Object value;
	public String message;
	public static final String charset = "UTF-8";
	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public abstract boolean valid() throws Exception;

	public abstract boolean valid(Object value) throws Exception;
}