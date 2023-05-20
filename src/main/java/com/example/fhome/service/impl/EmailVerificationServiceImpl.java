package com.example.fhome.service.impl;

import com.example.fhome.domain.entity.User;
import com.example.fhome.domain.enums.Status;
import com.example.fhome.domain.helper_pojo.Mail;
import com.example.fhome.exception.ApiRequestException;
import com.example.fhome.repository.UserRepository;
import com.example.fhome.service.EmailVerificationService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;


@Service
public class EmailVerificationServiceImpl implements EmailVerificationService {


    private final JavaMailSender mailSender;

    private final UserRepository userRepository;

    private final SpringTemplateEngine templateEngine;


    public EmailVerificationServiceImpl(JavaMailSender mailSender, UserRepository userRepository, SpringTemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.userRepository = userRepository;
        this.templateEngine = templateEngine;
    }

    @Override
    public void sendVerificationCode(Mail mail) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message,MimeMessageHelper.MULTIPART_MODE_RELATED, StandardCharsets.UTF_8.name());
            Context context = new Context();
            context.setVariables(mail.getProps());
            String html = templateEngine.process("verify_template", context);
            helper.setTo(mail.getMailTo());
            helper.setText(html, true);
            helper.setSubject(mail.getSubject());
            helper.setFrom(mail.getFrom());
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new ApiRequestException("Can Not Send Verification Code");
        }
    }

    @Override
    public User verify(Long userId, int verificationCode) {
        User user = userRepository.findByVerificationCode(verificationCode);
        if(user == null || user.getStatus() == Status.CONFIRM){
            throw new ApiRequestException("User not Found or already Exist");
        }else{
            user.setVerificationCode(null);
            user.setStatus(Status.CONFIRM);
            return userRepository.save(user);
        }
    }
}
