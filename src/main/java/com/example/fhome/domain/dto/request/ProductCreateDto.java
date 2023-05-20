package com.example.fhome.domain.dto.request;


import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProductCreateDto {
    String title;
    String description;
    Long categoryId;
    Long userId;
    MultipartFile photo;
}
