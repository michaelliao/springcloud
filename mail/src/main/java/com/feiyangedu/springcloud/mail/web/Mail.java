package com.feiyangedu.springcloud.mail.web;

public class Mail {

	private String to;
	private String subject;
	private String body;

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public void validate() {
		this.to = notEmpty(this.to, "Field 'to' is empty.").toLowerCase();
		if (!this.to.matches("^[a-z0-9\\.\\_\\-]+@[a-z0-9\\.\\-]+\\.[a-z]{2,6}$")) {
			throw new RuntimeException("Field 'to' is invalid.");
		}
		this.subject = notEmpty(this.subject, "Field 'subject' is empty.");
		this.body = notEmpty(this.body, "Field 'body' is empty.");
	}

	String notEmpty(String value, String error) {
		if (value == null || value.trim().isEmpty()) {
			throw new RuntimeException(error);
		}
		return value.trim();
	}
}
