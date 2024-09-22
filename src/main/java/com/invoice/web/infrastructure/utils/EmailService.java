package com.invoice.web.infrastructure.utils;

import com.invoice.web.infrastructure.Constants;
import com.invoice.web.persistence.repositories.SystemConfigRepository;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;


@Service
public class EmailService {
    private final SystemConfigRepository systemConfigRepository;

    public EmailService(SystemConfigRepository systemConfigRepository) {
        this.systemConfigRepository = systemConfigRepository;
    }

    public void sendSimpleMail(String otp) {
        String fromEmail = systemConfigRepository.findBySystemKey(Constants.OTP_SENDING_EMAIL).getSystemValue();
        String password = systemConfigRepository.findBySystemKey(Constants.OTP_SENDING_PASSWORD).getSystemValue();
        String toEmail = systemConfigRepository.findBySystemKey(Constants.ToEMAIL).getSystemValue();
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.office365.com");
        props.put("mail.smtp.port", "587"); // TLS Port for Outlook
        props.put("mail.smtp.auth", "true"); // Enabling SMTP Authentication
        props.put("mail.smtp.starttls.enable", "true"); // Enabling STARTTLS
        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        };

        // Email subject and response body (email template)
        String subject = "Your One-Time Password (OTP)";
        String responseBody = String.format(
                "Dear User,\n\n" +
                        "Your OTP for login is: %s\n\n" +
                        "Please use this OTP within the next 10 minutes.\n\n" +
                        "Thank you,\n" +
                        "Your Company Name", otp
        );


        Session session = Session.getDefaultInstance(props, auth);
        sendEmail(session, fromEmail, toEmail, subject, responseBody);
    }

    public static void sendEmail(Session session, String fromEmail, String toEmail, String subject, String body) {
        try {
            MimeMessage msg = new MimeMessage(session);            //set message headers
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");
            msg.setContent(body, "text/html");
            msg.setFrom(new InternetAddress(fromEmail, "NoReply-JD"));
            msg.setRecipients(Message.RecipientType.TO, fromEmail);
            msg.setSubject(subject, "UTF-8");
            msg.setSentDate(new Date());
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
            Transport.send(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}