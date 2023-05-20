package com.example.fhome.service;

import com.example.fhome.domain.entity.User;
import com.example.fhome.domain.helper_pojo.Mail;

public interface EmailVerificationService {

    void sendVerificationCode(Mail mail);
    User verify(Long userId, int verificationCode);

}
