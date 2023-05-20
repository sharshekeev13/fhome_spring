package com.example.fhome.service.impl;


import com.example.fhome.domain.entity.Category;
import com.example.fhome.domain.enums.AddDeleteStatus;
import com.example.fhome.repository.CategoryRepository;
import com.example.fhome.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    @Override
    public Category createCategory(Category category) {
        category.setStatus(AddDeleteStatus.ADDED);
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

}
