package com.example.fhome.domain.dto.request;

import com.example.fhome.domain.enums.Status;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProductUpdateDto {

    String title;
    String description;
    MultipartFile photo;
    Long categoryId;
    Status status;

}
