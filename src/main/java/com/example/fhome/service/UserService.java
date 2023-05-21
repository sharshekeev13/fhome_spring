package com.example.fhome.service;

import com.example.fhome.domain.dto.request.UserLoginDto;
import com.example.fhome.domain.dto.request.UserRegistrationDto;
import com.example.fhome.domain.entity.Product;
import com.example.fhome.domain.entity.User;
import com.example.fhome.domain.enums.Status;

import java.util.List;

public interface UserService {

    User getUserById(Long id);

    User registration(UserRegistrationDto user);

    User verifyEmail(Long userId, int verifyCode);
    User login(UserLoginDto userLoginDto);

    List<User> findAll(Integer pageNo,String sortBy);

    User updateUserInfo(Long userId,Integer status);

    List<Product> getUserProduct(Long id);
}
