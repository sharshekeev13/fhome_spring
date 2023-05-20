package com.example.fhome.repository;

import com.example.fhome.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findUserByEmailAndPassword(String email,String password);
    User findByVerificationCode(int verificationCode);
}
