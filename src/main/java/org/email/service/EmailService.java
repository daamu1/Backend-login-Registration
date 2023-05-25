package org.email.service;

import lombok.AllArgsConstructor;
import org.email.service.imp.EmailSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@AllArgsConstructor
public class EmailService implements EmailSender {
    private final static Logger LOGGER = LoggerFactory.getLogger(EmailService.class);
    private final JavaMailSender mailSender;

    @Override
    @Async
    public void send(String to, String email) throws IllegalAccessException {
        try {
            MimeMessage mimeMailMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMailMessage, "utf-8");
            helper.setText(email, true);
            helper.setTo(to);
            helper.setSubject("Confirm your email ..");
            helper.setFrom("dakaitshady01999@gmail.com");
            mailSender.send(mimeMailMessage);

        } catch (MessagingException messagingException) {
            LOGGER.error("faild to send email ", messagingException);
            throw new IllegalAccessException("faild to send email ..");
        }
    }
}
