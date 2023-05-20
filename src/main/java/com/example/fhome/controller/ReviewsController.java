package com.example.fhome.controller;


import com.example.fhome.domain.dto.request.ReviewsDto;
import com.example.fhome.domain.entity.Reviews;
import com.example.fhome.service.impl.ReviewsServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "REVIEWS", description = "Конечные точки для работы с оценками к продуктам")
@RestController
@RequestMapping("/api/reviews")
public class ReviewsController {

    private final ReviewsServiceImpl reviewsService;

    public ReviewsController(ReviewsServiceImpl reviewsService) {
        this.reviewsService = reviewsService;
    }

    @Operation(summary = "Создание и обновление оценки к продуктам")
    @PostMapping()
    public ResponseEntity<Reviews> createReview(@RequestBody ReviewsDto reviewsCreateDto){
        return ResponseEntity.ok(reviewsService.updateReview(reviewsCreateDto));
    }

}
