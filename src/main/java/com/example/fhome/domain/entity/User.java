package com.example.fhome.domain.entity;


import com.example.fhome.domain.enums.Role;
import com.example.fhome.domain.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique = true)
    String email;

    String photo;

    @JsonIgnore
    String password;

    @Column(name = "full_name")
    String fullName;

    LocalDate birthday;

    @JsonIgnore
    Role role;

    @Column(name = "phone_number")
    String phoneNumber;

    @Column(name = "user_info")
    String userInfo;

    Status status;

    @Column(name = "created_date")
    LocalDate createdDate;

    @JsonIgnore
    @Column(name = "verification_code",length = 4)
    Integer verificationCode;

}
