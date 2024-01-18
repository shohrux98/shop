package com.ecommers.service;

import com.ecommers.dto.CategoryDto;
import com.ecommers.dto.ResponseDto;
import com.ecommers.models.Category;
import com.ecommers.models.Product;
import com.ecommers.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    public ResponseDto addCategory(CategoryDto categoryDto){
        Category category = new Category();
        category.setCategoryName(categoryDto.getCategoryName());
        category.setDescription(categoryDto.getDescription());
        category.setImageUrl(categoryDto.getImageUrl());
        categoryRepository.save(category);
        return new ResponseDto(true, "created");
    }

    public List<Category> listCategory(){
        return categoryRepository.findAll();
    }

    public ResponseDto editCategory(Integer id, CategoryDto categoryDto){
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if (categoryOptional.isPresent()){
            Category category = categoryOptional.get();
            category.setCategoryName(categoryDto.getCategoryName());
            category.setDescription(categoryDto.getDescription());
            category.setImageUrl(categoryDto.getImageUrl());
            categoryRepository.save(category);
            return new ResponseDto(true, "Category updated");
        }
        return new ResponseDto(false, "Category not updated");
    }

    public Category getCategory(Integer id) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if (categoryOptional.isPresent()){
            Category category = categoryOptional.get();
            return category;
        }
        return null;
    }

}
