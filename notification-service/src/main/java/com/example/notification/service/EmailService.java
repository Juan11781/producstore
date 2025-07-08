package com.example.notification.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

@Service
public class EmailService {
	@Value("${sendgrid.api.key}")
	private String apiKey;
	
	@Value("${email.sender}")
	private String email;
	
	public void sendEmail(String to, String subject, String contentTest) throws IOException{
		Email from  = new Email(email);
		Email toEmail = new Email(to);
		Content content = new Content("text/plain",contentTest);
		Mail mail = new Mail(from, subject, toEmail,content);
		
		SendGrid sg = new SendGrid(apiKey);
		Request request = new Request();
		
		request.setMethod(Method.POST);
		request.setEndpoint("mail/send");
		request.setBody(mail.build());
		
		Response response = sg.api(request);
		
		System.out.println("Status: "+response.getStatusCode());
		System.out.println("Body:   "+response.getBody());
		System.out.println("Headers: "+response.getHeaders());
	}
}
