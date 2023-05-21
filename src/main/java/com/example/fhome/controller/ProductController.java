package com.example.fhome.controller;

import com.example.fhome.domain.dto.request.ProductCreateDto;
import com.example.fhome.domain.dto.request.ProductUpdateDto;
import com.example.fhome.domain.entity.Product;
import com.example.fhome.service.impl.ProductServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Tag(name = "PRODUCTS", description = "Конечные точки для работы с продуктами")
@RestController
@RequestMapping("/api/product")
public class ProductController {
    private final ProductServiceImpl productService;

    public ProductController(ProductServiceImpl productService) {
        this.productService = productService;
    }

    @Operation(summary = "Продукт по его идендификатору")
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductByID(@PathVariable Long id){
        return ResponseEntity.ok(productService.getById(id));
    }

    @Operation(summary = "Создание продукта")
    @RequestMapping(path = "",method = POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<Product> createProduct(@ModelAttribute ProductCreateDto productCreateDto){
        return ResponseEntity.ok(productService.createProduct(productCreateDto));
    }

    @Operation(summary = "Все продукты по страницам")
    @GetMapping()
    public ResponseEntity<List<Product>> getProducts(@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "0") Long category){
        return ResponseEntity.ok(productService.findAll(pageNo,category));
    }

    @Operation(summary = "Обновление продукта")
    @RequestMapping(path = "/{id}",method = POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @ModelAttribute ProductUpdateDto productUpdateDto){
        return ResponseEntity.ok(productService.updateProduct(id,productUpdateDto));
    }


}
