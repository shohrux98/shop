package com.ecommers.controller;


import com.ecommers.dto.ProductDto;
import com.ecommers.dto.ResponseDto;
import com.ecommers.models.Product;
import com.ecommers.models.User;
import com.ecommers.models.WishList;
import com.ecommers.service.AuthenticationService;
import com.ecommers.service.ProductService;
import com.ecommers.service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/wishlist")
public class WishListController {
    @Autowired
    WishListService wishListService;

    @Autowired
    AuthenticationService authenticationService;

    @GetMapping("/{token}")
    public ResponseEntity<List<ProductDto>> getWishList(@PathVariable("token") String token) {
        int user_id = authenticationService.getUser(token).getId();
        List<WishList> body = wishListService.readWishList(user_id);
        List<ProductDto> products = new ArrayList<ProductDto>();
        for (WishList wishList : body) {
            products.add(ProductService.getDtoFromProduct(wishList.getProduct()));
        }

        return new ResponseEntity<List<ProductDto>>(products, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<ResponseDto> addWishList(@RequestBody Product product, @RequestParam("token") String token) {
        authenticationService.authenticate(token);
        User user = authenticationService.getUser(token);
        wishListService.delete(product.getId());
        System.out.println(product.getId());
        WishList wishList = new WishList(user, product);
        wishListService.createWishList(wishList);
        return new ResponseEntity<ResponseDto>(new ResponseDto(true, "Add to wishlist"), HttpStatus.CREATED);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<ResponseDto> delete(@PathVariable Integer id){
        ResponseDto delete = wishListService.delete(id);
        return ResponseEntity.ok(delete);
    }
}
