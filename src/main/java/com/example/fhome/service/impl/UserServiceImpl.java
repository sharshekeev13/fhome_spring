package com.example.fhome.service.impl;

import com.example.fhome.domain.dto.request.UserLoginDto;
import com.example.fhome.domain.dto.request.UserRegistrationDto;
import com.example.fhome.domain.entity.Product;
import com.example.fhome.domain.entity.User;
import com.example.fhome.domain.enums.Role;
import com.example.fhome.domain.enums.Status;
import com.example.fhome.domain.helper_pojo.Mail;
import com.example.fhome.exception.ApiRequestException;
import com.example.fhome.repository.ProductRepository;
import com.example.fhome.repository.UserRepository;
import com.example.fhome.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final EmailVerificationServiceImpl emailVerificationService;
    private final FileUploadServiceImpl fileUploadService;


    UserServiceImpl(UserRepository userRepository, ProductRepository productRepository, EmailVerificationServiceImpl emailVerificationService, FileUploadServiceImpl fileUploadService){
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.emailVerificationService = emailVerificationService;
        this.fileUploadService = fileUploadService;
    }


    @Override
    public User getUserById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if(user == null) throw new ApiRequestException("User Not Found");
        return user;
    }

    @Override
    public User registration(UserRegistrationDto user) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        LocalDate birthday = LocalDate.parse(user.getBirthday(),formatter);
        User newUser = User.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .photo(getPhotoUrl(user.getPhoto()))
                .fullName(user.getFullName())
                .birthday(birthday)
                .role(Role.USER)
                .phoneNumber(user.getPhone())
                .userInfo(user.getUserInfo())
                .status(Status.AWAIT)
                .verificationCode(generateRandomNumber())
                .createdDate(LocalDate.now())
                .build();
        sendVerifyCode(newUser);
        return userRepository.save(newUser);
    }

    private String getPhotoUrl(MultipartFile photo) {
        String photoUrl;
        try {
            photoUrl = fileUploadService.uploadFile(photo);
        } catch (IOException e) {
            throw new ApiRequestException("Can Not Upload a Image");
        }
        return photoUrl;
    }

    private void sendVerifyCode(User user){
        Map<String, Object> model = new HashMap<>();
        model.put("code",user.getVerificationCode());
        model.put("name",user.getFullName());
        model.put("email",user.getEmail());
        Mail mail = Mail.builder()
                .from("fhomekgz@gmail.com")
                .mailTo(user.getEmail())
                .subject("Подверждение почты From Home")
                .props(model)
                .build();
        emailVerificationService.sendVerificationCode(mail);
    }

    @Override
    public User verifyEmail(Long userId, int verifyCode) {
        return emailVerificationService.verify(userId,verifyCode);
    }

    private int generateRandomNumber() {
        Random random = new Random();
        int randomNumber = random.nextInt(9000) + 1000;
        boolean flag = false;
        while(!flag){
            if(userRepository.findByVerificationCode(randomNumber) == null) flag = true;
            else randomNumber = random.nextInt(9000) + 1000;
        }
        return randomNumber;
    }

    @Override
    public User login(UserLoginDto userLoginDto) {
        User user = userRepository.findUserByEmailAndPassword(userLoginDto.getEmail(), userLoginDto.getPassword());
        if(user == null || user.getStatus() != Status.CONFIRM){
            throw new ApiRequestException("User Not Found");
        }
        return user;
    }

    @Override
    public List<User> findAll(Integer pageNo,String sortBy) {
        Pageable paging = PageRequest.of(pageNo,20, Sort.by(sortBy));
        Page<User> pagedResult = userRepository.findAll(paging);
        if(pagedResult.hasContent()) {
            return pagedResult.getContent();
        }else{
            return new ArrayList<>();
        }
    }

    @Override
    public User updateUserInfo(Long userId,Integer status) {
        User user = userRepository.findById(userId).orElse(null);
        if(user == null){
            throw new ApiRequestException("User Not Found");
        }
        Status newStatus;
        if(status == 1) newStatus = Status.CONFIRM;
        else if(status == 2) newStatus = Status.DELETED;
        else if(status == 3) newStatus = Status.BAN;
        else throw new ApiRequestException("Status Not Found");
        user.setStatus(newStatus);
        return userRepository.save(user);
    }

    @Override
    public List<Product> getUserProduct(Long id) {
        User user = getUserById(id);
        return productRepository.findByUser(user);
    }


}
