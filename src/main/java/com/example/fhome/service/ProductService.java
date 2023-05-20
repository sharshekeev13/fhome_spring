package com.example.fhome.service;

import com.example.fhome.domain.dto.request.ProductCreateDto;
import com.example.fhome.domain.dto.request.ProductUpdateDto;
import com.example.fhome.domain.entity.Category;
import com.example.fhome.domain.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    List<Product> findAll(Integer pageNo, Long reqCategory);

    Product getById(Long id);

    Product createProduct(ProductCreateDto productCreateDto);

    Product updateProduct(Long productId,ProductUpdateDto productUpdateDto);


}
