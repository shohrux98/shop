package com.ecommers.controller;


import com.ecommers.dto.CartDto;
import com.ecommers.dto.CartGetDto;
import com.ecommers.dto.ResponseDto;
import com.ecommers.exceptions.ProductNotExistException;
import com.ecommers.models.User;
import com.ecommers.service.AuthenticationService;
import com.ecommers.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    CartService cartService;

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/add")
    public ResponseEntity<ResponseDto> addToCart(@RequestBody CartDto cartDto, @RequestParam("token") String token) throws ProductNotExistException {
        authenticationService.authenticate(token);
        User user = authenticationService.getUser(token);

        cartService.addToCart(cartDto, user);
        return new ResponseEntity<>(new ResponseDto(true, "Added to cart"), HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<CartGetDto> getCartItems(@RequestParam("token") String token){
        authenticationService.authenticate(token);
        User user = authenticationService.getUser(token);

        CartGetDto cartGetDto = cartService.listCartItems(user);
        return new ResponseEntity<>(cartGetDto, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{cartItemId}/")
    public ResponseEntity<ResponseDto> deleteCart(@PathVariable Integer cartItemId, @RequestParam("token") String token){
        authenticationService.authenticate(token);
        User user = authenticationService.getUser(token);

        cartService.deleteCartItem(cartItemId, user);
        return new ResponseEntity<>(new ResponseDto(true, "Delete"), HttpStatus.OK);
    }

}
