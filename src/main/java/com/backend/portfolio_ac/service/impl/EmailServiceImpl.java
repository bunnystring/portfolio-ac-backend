package com.backend.portfolio_ac.service.impl;

import com.backend.portfolio_ac.exception.VerificationEmailException;
import com.backend.portfolio_ac.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Override
    public void sendVerificationEmail(String toEmail, String token){
        String verificationUrl = "https://portfolio-ac.com/verifyEmail?token=" + token;
        String subject = "Verifica tu correo electrónico";
        String body = "Hola, por favor verifica tu correo electronico haciendo click: "
                + verificationUrl
                + "Si no creaste cuenta por favor ignora este mensaje";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);

       try{
           mailSender.send(message);
       } catch (MailException ex){
           log.error("Error intentnado enviar correo de verificación a {}: {}", toEmail, ex.getMessage());
           throw new VerificationEmailException("Error intentando enviar correo de verificación", VerificationEmailException.Type.EMAIL_VERIFICATION_FAIL_SEND);
       }
    }
}
