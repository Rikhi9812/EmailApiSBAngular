package com.email.model;

import org.springframework.web.multipart.MultipartFile;

public class EmailRequest {
	public EmailRequest(String to, String subject, String message, MultipartFile file) {
		super();
		this.to = to;
		this.subject = subject;
		this.message = message;
		this.file = file;
	}

	public EmailRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EmailRequest(String to, String subject, String message) {
		super();
		this.to = to;
		this.subject = subject;
		this.message = message;
	}

	private String to;
	private String subject;
	private String message;
	private MultipartFile file;

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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "EmailRequest [to=" + to + ", subject=" + subject + ", message=" + message + ", file=" + file + "]";
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

}
