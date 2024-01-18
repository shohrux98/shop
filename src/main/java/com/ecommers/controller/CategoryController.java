package com.ecommers.controller;

import com.ecommers.dto.CategoryDto;
import com.ecommers.dto.ResponseDto;
import com.ecommers.models.Category;
import com.ecommers.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> addCategory(@RequestBody CategoryDto categoryDto) {
        ResponseDto result = categoryService.addCategory(categoryDto);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Category>> categories() {
        return ResponseEntity.ok(categoryService.listCategory());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategory(@PathVariable Integer id) {
        Category category = categoryService.getCategory(id);
        return ResponseEntity.ok(category);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto> editCategory(@PathVariable Integer id, @RequestBody CategoryDto categoryDto) {
        ResponseDto category = categoryService.editCategory(id, categoryDto);
        return ResponseEntity.ok(category);
    }

}
