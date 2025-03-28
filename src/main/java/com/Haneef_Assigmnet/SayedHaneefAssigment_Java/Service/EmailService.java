package com.Haneef_Assigmnet.SayedHaneefAssigment_Java.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendManagerNotification(String managerEmail, String employeeName, String phoneNumber, String emailId) {
        String subject = "New Employee Assigned to You";
        String body = employeeName + " will now work under you.\n" +
                "Mobile number: " + phoneNumber + "\n" +
                "Email: " + emailId + "\n \n Connect with them 🙌";
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(managerEmail);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
        System.out.println("Email sent to Level 1 Manager: " + managerEmail);
    }
}
