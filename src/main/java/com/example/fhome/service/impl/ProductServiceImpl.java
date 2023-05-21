package com.example.fhome.service.impl;

import com.example.fhome.domain.dto.request.ProductCreateDto;
import com.example.fhome.domain.dto.request.ProductUpdateDto;
import com.example.fhome.domain.entity.Category;
import com.example.fhome.domain.entity.Product;
import com.example.fhome.domain.entity.User;
import com.example.fhome.domain.enums.Status;
import com.example.fhome.exception.ApiRequestException;
import com.example.fhome.repository.CategoryRepository;
import com.example.fhome.repository.ProductRepository;
import com.example.fhome.repository.UserRepository;
import com.example.fhome.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final FileUploadServiceImpl fileUploadService;


    public ProductServiceImpl(ProductRepository productRepository, UserRepository userRepository, CategoryRepository categoryRepository, FileUploadServiceImpl fileUploadService) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.fileUploadService = fileUploadService;
    }

    @Override
    public List<Product> findAll(Integer pageNo, Long reqCategory) {
        if(reqCategory == 0){
            Pageable paging = PageRequest.of(pageNo,20);
            Page<Product> pagedResult = productRepository.findAllByStatus(Status.CONFIRM,paging);
            if(pagedResult.hasContent()) {
                return pagedResult.getContent();
            }else{
                return new ArrayList<Product>();
            }
        }else{
            Pageable paging = PageRequest.of(pageNo,20);
            Category category = checkCategory(reqCategory);
            List<Product> pagedResult = productRepository.findAllByCategoryAndStatus(category,Status.CONFIRM,paging);
            if(!pagedResult.isEmpty()) {
                return pagedResult;
            }else{
                return new ArrayList<Product>();
            }
        }
    }

    @Override
    public Product getById(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if(product == null){
            throw new ApiRequestException("Product Not Found");
        }
        return product;
    }

    @Override
    public Product createProduct(ProductCreateDto productCreateDto) {
        User user = userRepository.findById(productCreateDto.getUserId()).orElse(null);
        Category category = categoryRepository.findById(productCreateDto.getCategoryId()).orElse(null);
        if(user == null){
            throw new ApiRequestException("User Not Found");
        }
        if(category == null){
            throw new ApiRequestException("Category Not Found");
        }
        Product product = Product.builder()
                .title(productCreateDto.getTitle())
                .description(productCreateDto.getDescription())
                .category(category)
                .user(user)
                .createdDate(LocalDate.now())
                .status(Status.AWAIT)
                .review((double) 0)
                .photo(getPhotoUrl(productCreateDto.getPhoto()))
                .build();
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Long productId, ProductUpdateDto productUpdateDto) {
        Product product = productRepository.findById(productId).orElse(null);
        if(product == null){
            throw new ApiRequestException("Product Not Found");
        }
        if(productUpdateDto.getTitle() != null){
            product.setTitle(productUpdateDto.getTitle());
        }
        if(productUpdateDto.getDescription() != null){
            product.setDescription(productUpdateDto.getDescription());
        }
        if(productUpdateDto.getCategoryId() != null){
            Category category = checkCategory(productUpdateDto.getCategoryId());
            product.setCategory(category);
        }
        if(productUpdateDto.getPhoto() != null){
            product.setPhoto(getPhotoUrl(productUpdateDto.getPhoto()));
        }
        if(productUpdateDto.getStatus() != null){
            product.setStatus(productUpdateDto.getStatus());
        }
        return productRepository.save(product);
    }

    private String getPhotoUrl(MultipartFile photo) {
        String photoUrl;
        try {
            photoUrl = fileUploadService.uploadFile(photo);
        } catch (IOException e) {
            throw new ApiRequestException("Can Not Upload a Image");
        }
        return photoUrl;
    }


    private Category checkCategory(Long reqCategory) {
        Category category = categoryRepository.findById(reqCategory).orElse(null);
        if(category == null){
            throw new ApiRequestException("Category Not Found or Doesn't Exist");
        }
        return category;
    }


}
