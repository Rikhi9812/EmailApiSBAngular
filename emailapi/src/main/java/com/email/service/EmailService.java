package com.email.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.email.model.EmailRequest;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender javaMailSender;

	@Value("${spring.mail.username}")
	private String sender;

	public boolean sendEmail(EmailRequest request) {

		boolean result = false;

		try {
			SimpleMailMessage mailMessage = new SimpleMailMessage();

			mailMessage.setFrom(sender);
			mailMessage.setTo(request.getTo());
			mailMessage.setSubject(request.getSubject());
			mailMessage.setText(request.getMessage());

			javaMailSender.send(mailMessage);

			System.out.println("sent successfully");

			result = true;

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return result;
	}

	public boolean sendEmailWithAttachment(EmailRequest request) throws Exception {

		boolean result = false;
		
		File saveFile = new ClassPathResource("/static/image").getFile();
		
		Path path = Paths.get(saveFile + File.separator + request.getFile().getOriginalFilename());
		
		Files.copy(request.getFile().getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
		
		
		

		MimeMessage mimeMessage = javaMailSender.createMimeMessage();

//		MimeMessageHelper mimeMessageHelper;

		try {

//			mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
//			mimeMessageHelper.setTo(request.getTo());
//			mimeMessageHelper.setFrom(sender);
//			mimeMessageHelper.setSubject(request.getSubject());
//			mimeMessageHelper.setText(request.getMessage());
//
//			FileSystemResource file = new FileSystemResource(request.getFile().getBytes().toString());
//			mimeMessageHelper.addAttachment(file.getFilename(), file);
			
			mimeMessage.setFrom(sender);
			mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(request.getTo()));
			mimeMessage.setSubject(request.getSubject());
			
			
			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setText(request.getMessage());
			
			
			MimeBodyPart attachmentPart = new MimeBodyPart();
			attachmentPart.attachFile(saveFile + File.separator + request.getFile().getOriginalFilename());
			
			Multipart multipart = new MimeMultipart();  
		    multipart.addBodyPart(messageBodyPart);  
		    multipart.addBodyPart(attachmentPart);  
			
			mimeMessage.setContent(multipart);
			

			javaMailSender.send(mimeMessage);

			System.out.println("sent successfully");
			

			
			result = true;

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		File file = new File(saveFile + File.separator  + request.getFile().getOriginalFilename());
		
		file.delete();
		return result;
	}
	

}
