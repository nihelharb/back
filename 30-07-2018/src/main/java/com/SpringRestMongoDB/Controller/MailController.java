package com.SpringRestMongoDB.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SpringRestMongoDB.model.Mail;
import com.SpringRestMongoDB.service.EmailService;



public class MailController implements ApplicationRunner {
	  


	    private static Logger log = LoggerFactory.getLogger(MailController.class);

	    @Autowired
	    private EmailService emailService;


	    @Override
	    public void run(ApplicationArguments applicationArguments) throws Exception {
	        log.info("Spring Mail - Sending Simple Email with JavaMailSender Example");

	        Mail mail = new Mail();
	        mail.setFrom("harb.nihel55@gmail.com");
	        mail.setTo("maissa1922@gmail.com");
	        mail.setSubject("Sending Simple Email with JavaMailSender Example");
	        mail.setContent("This tutorial demonstrates how to send a simple email using Spring Framework.");

	        emailService.sendSimpleMessage(mail);
	    }
	}


