package com.ecommers.service;


import com.ecommers.dto.ProductDto;
import com.ecommers.dto.ResponseDto;
import com.ecommers.exceptions.ProductNotExistException;
import com.ecommers.models.Category;
import com.ecommers.models.Product;
import com.ecommers.repository.CategoryRepository;
import com.ecommers.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;

    public ResponseDto createProduct(ProductDto productDto) {
        Optional<Category> optionalCategory = categoryRepository.findById(productDto.getCategory_id());
        if (optionalCategory.isEmpty()) {
            return new ResponseDto(false, "Category not found");
        }
        Product product = new Product();
        product.setName(productDto.getName());
        product.setImageURL(productDto.getImageURL());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setCategory(optionalCategory.get());
        productRepository.save(product);
        return new ResponseDto(true, "Product created");
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public ResponseDto editProduct(Integer id, ProductDto productDto) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()) {
            return new ResponseDto(false, "Product not found");
        }
        System.out.println(productDto.getCategory_id());
        Optional<Category> optionalCategory = categoryRepository.findById(productDto.getCategory_id());
        if (optionalCategory.isEmpty()) {
            return new ResponseDto(false, "Category not found");
        }
        Product product = optionalProduct.get();
        product.setName(productDto.getName());
        product.setImageURL(productDto.getImageURL());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setCategory(optionalCategory.get());
        productRepository.save(product);
        return new ResponseDto(true, "Product updated");
    }

    public Product getProduct(Integer id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()){
            Product product1 = product.get();
            return product1;
        }
        return null;
    }

    public List<Product> getProductsCategory(Integer id){
        Optional<List<Product>> allByCategory_id = productRepository.findAllByCategory_Id(id);
        if (allByCategory_id.isPresent()){
            List<Product> products = allByCategory_id.get();
            return products;
        }
        return null;
    }

    public static ProductDto getDtoFromProduct(Product product) {
        ProductDto productDto = new ProductDto(product);
        return productDto;
    }
    public static Product getProductFromDto(ProductDto productDto, Category category) {
        Product product = new Product(productDto, category);
        return product;
    }

    public Product findById(Integer productId) throws ProductNotExistException {
        Optional<Product> product = productRepository.findById(productId);
        if (product.isEmpty())
            throw  new ProductNotExistException("Product id is invalid " + productId);
        return product.get();
    }
}
