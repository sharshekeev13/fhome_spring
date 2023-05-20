package com.example.fhome.domain.dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UserRegistrationDto {
    String email;
    MultipartFile photo;
    String password;
    String fullName;
    String birthday;
    String phone;
    String userInfo;
}
