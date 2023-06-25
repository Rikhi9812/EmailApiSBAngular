package com.email.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.email.model.EmailRequest;
import com.email.model.EmailResponse;
import com.email.service.EmailService;

@RestController
@CrossOrigin
public class EmailController {

	@Autowired
	private EmailService emailService;

	@GetMapping("/test")
	public String test() {
		return "hello api";
	}

	// API to send email
//	@CrossOrigin(value = "http://localhost:4200/sendemail")
	@PostMapping(value = "/sendmail")
	public ResponseEntity<?> sendEmail(@ModelAttribute EmailRequest request) throws Exception {

		System.out.println(request);
//		System.out.println(file);
		
//		System.out.println(request.getFile().getOriginalFilename());

		boolean result;

		if (request.getFile() == null) {

			result = this.emailService.sendEmail(request);
		} else {
			result = this.emailService.sendEmailWithAttachment(request);
		}

		if (result) {
			return ResponseEntity.ok(new EmailResponse("Email sent successfully"));
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new EmailResponse("Email not sent"));
		}

	}
}
