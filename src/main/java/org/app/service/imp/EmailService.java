package org.app.service.imp;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.app.constant.Constant;
import org.app.service.EmailSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class EmailService implements EmailSender {
    private final static Logger LOGGER = LoggerFactory.getLogger(EmailService.class);
    private final JavaMailSender mailSender;

    @Async
    @Override
    public void send(String to, String email) {
        try {
            MimeMessage mimeMailMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMailMessage, "utf-8");
            helper.setText(email,true);
            helper.setTo(to);
            helper.setSubject(Constant.EMAIL_SUBJECT);
            helper.setFrom(Constant.EMAIL_FROM);
            mailSender.send(mimeMailMessage);

        } catch (MessagingException messagingException) {
            LOGGER.error("failed to send email ", messagingException);
            throw new MailSendException("failed to send email to " + to);
        }
    }
}
