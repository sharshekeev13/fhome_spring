package com.example.fhome.service;

import com.example.fhome.domain.entity.Category;

import java.util.List;

public interface CategoryService {

    Category createCategory(Category category);
    List<Category> findAll();
}
