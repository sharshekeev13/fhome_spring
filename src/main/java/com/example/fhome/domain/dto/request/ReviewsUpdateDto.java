package com.example.fhome.domain.dto.request;

import com.example.fhome.domain.enums.AddDeleteStatus;
import lombok.Data;

@Data
public class ReviewsUpdateDto {
    Long productId;
    Integer review;
    AddDeleteStatus status;
}
