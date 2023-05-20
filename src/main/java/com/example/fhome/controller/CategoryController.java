package com.example.fhome.controller;


import com.example.fhome.domain.entity.Category;
import com.example.fhome.service.CategoryService;
import com.example.fhome.service.impl.CategoryServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "CATEGORY", description = "Конечные точки для работы с категориями")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryServiceImpl categoryService;

    @Operation(summary = "Список всех категорий")
    @GetMapping()
    public ResponseEntity<List<Category>> getAllCategory(){
        return ResponseEntity.ok(categoryService.findAll());
    }

    @Operation(summary = "Создание категории")
    @PostMapping()
    public ResponseEntity<Category> createCategory(@RequestBody Category category){
        return ResponseEntity.ok(categoryService.createCategory(category));
    }

}
