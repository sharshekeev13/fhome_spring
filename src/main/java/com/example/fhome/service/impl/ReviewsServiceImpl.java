package com.example.fhome.service.impl;


import com.example.fhome.domain.dto.request.ReviewsDto;
import com.example.fhome.domain.entity.Product;
import com.example.fhome.domain.entity.Reviews;
import com.example.fhome.domain.entity.User;
import com.example.fhome.domain.enums.AddDeleteStatus;
import com.example.fhome.domain.enums.Status;
import com.example.fhome.exception.ApiRequestException;
import com.example.fhome.repository.ProductRepository;
import com.example.fhome.repository.ReviewsRepository;
import com.example.fhome.repository.UserRepository;
import com.example.fhome.service.ReviewsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewsServiceImpl implements ReviewsService {

    private final ReviewsRepository reviewsRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public ReviewsServiceImpl(ReviewsRepository reviewsRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.reviewsRepository = reviewsRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Reviews createReview(ReviewsDto reviewsCreateDto) {
        Product product = checkProduct(reviewsCreateDto.getProductId());
        User user = checkUser(reviewsCreateDto.getUserId());
        Reviews reviews = Reviews.builder()
                .product(product)
                .rating(reviewsCreateDto.getRating())
                .status(AddDeleteStatus.ADDED)
                .user(user)
                .build();
        reviewsRepository.save(reviews);
        product.setReview(getReviewForProduct(reviewsCreateDto.getProductId()));
        productRepository.save(product);
        return reviews;
    }

    @Override
    public Reviews updateReview(ReviewsDto reviewsDto) {
        Product product = checkProduct(reviewsDto.getProductId());
        User user = checkUser(reviewsDto.getUserId());
        Reviews reviews = reviewsRepository.findByUserAndProduct(user,product);
        if(reviews == null){
            return createReview(new ReviewsDto(reviewsDto.getProductId(), reviewsDto.getUserId(), reviewsDto.getRating(),AddDeleteStatus.ADDED));
        }
        if(reviewsDto.getRating() != null){
            reviews.setRating(reviewsDto.getRating());
        }
        if(reviewsDto.getStatus() != null){
            reviews.setStatus(reviewsDto.getStatus());
        }
        return reviewsRepository.save(reviews);
    }

    @Override
    public double getReviewForProduct(Long productId) {
        List<Reviews> reviewsList = reviewsRepository.findAllByProduct(checkProduct(productId));
        int sum = 0;
        for(Reviews reviews : reviewsList){
            sum += reviews.getRating();
        }
        return (double) sum/reviewsList.size();
    }

    private Product checkProduct(Long id){
        Product product = productRepository.findById(id).orElse(null);
        if(product == null || product.getStatus() != Status.CONFIRM){
            throw new ApiRequestException("Product Not Found");
        }
        return product;
    }
    private User checkUser(Long id){
        User user = userRepository.findById(id).orElse(null);
        if(user == null || user.getStatus() != Status.CONFIRM){
            throw new ApiRequestException("User Not Found");
        }
        return user;
    }
}
