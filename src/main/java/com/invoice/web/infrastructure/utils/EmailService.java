package com.invoice.web.infrastructure.utils;


import com.invoice.web.infrastructure.Constants;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    @Value("${email.from}")
    private String fromAddress;

    private void sendEmailWithTemplate(String templateName, Context context, String to, String subject) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setFrom(fromAddress);

        // Thymeleaf context to inject variables into the template
        /*Context context = new Context();
        context.setVariable("name", name);
        context.setVariable("message", messageBody);*/
        String htmlContent = templateEngine.process(templateName, context);
        helper.setText(htmlContent, true);
        mailSender.send(message);
    }
    @Async
    public void sendOtpEmail(String name, String surname,String otp, String email) throws MessagingException {
        Context context = new Context();
        context.setVariable("name", name);
        context.setVariable("surname", surname);
        context.setVariable("otp", otp);
        sendEmailWithTemplate(Constants.OTP_EMAIL_TEMPLATE,context,email,Constants.OTP_EMAIL_SUBJECT);
    }
}

    /*private final SystemConfigRepository systemConfigRepository;

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
    }*/