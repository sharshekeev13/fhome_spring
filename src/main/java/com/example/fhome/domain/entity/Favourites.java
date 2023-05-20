package com.example.fhome.domain.entity;


import com.example.fhome.domain.enums.AddDeleteStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Entity
@Table(name = "favourites")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Favourites {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;


    @OneToOne
    Product product;

    @OneToOne
    User user;

    AddDeleteStatus status;
}
