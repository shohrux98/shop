package com.ecommers.controller;

import com.ecommers.dto.ProductDto;
import com.ecommers.dto.ResponseDto;
import com.ecommers.models.Product;
import com.ecommers.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("/add")
    public ResponseEntity<ResponseDto> createProduct(@RequestBody ProductDto productDto){
        ResponseDto product = productService.createProduct(productDto);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/")
    public ResponseEntity<List<Product>> allProduct(){
        return ResponseEntity.ok(productService.getProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Integer id){
        Product product = productService.getProduct(id);
        return ResponseEntity.ok(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto> editProduct(@PathVariable Integer id, @RequestBody ProductDto productDto){
        ResponseDto product = productService.editProduct(id, productDto);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<?> getProductsFromCategory(@PathVariable Integer id){
        List<Product> productsCategory = productService.getProductsCategory(id);
        return ResponseEntity.ok(productsCategory);
    }
}
