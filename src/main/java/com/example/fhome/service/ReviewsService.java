package com.example.fhome.service;

import com.example.fhome.domain.dto.request.ReviewsDto;
import com.example.fhome.domain.dto.request.ReviewsUpdateDto;
import com.example.fhome.domain.entity.Reviews;

public interface ReviewsService {

    Reviews createReview(ReviewsDto reviewsCreateDto);
    Reviews updateReview(ReviewsDto reviewsUpdateDto);
    double getReviewForProduct(Long productId);
}
