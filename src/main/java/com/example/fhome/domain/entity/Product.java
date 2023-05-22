package com.example.fhome.domain.entity;

import com.example.fhome.domain.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
@Table(name = "product")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String title;

    String description;

    @ManyToOne
    User user;

    @ManyToOne
    Category category;

    @Column(name = "created_date")
    LocalDate createdDate;

    Status status;

    String photo;

    Long price;

    double review;

}
