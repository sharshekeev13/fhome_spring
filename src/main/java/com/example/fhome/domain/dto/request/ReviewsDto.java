package com.example.fhome.domain.dto.request;

import com.example.fhome.domain.enums.AddDeleteStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ReviewsDto {
    Long productId;
    Long userId;
    Integer rating;
    AddDeleteStatus status;
}
