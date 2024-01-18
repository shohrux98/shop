package com.ecommers.service;

import com.ecommers.dto.CartDto;
import com.ecommers.dto.CartGetDto;
import com.ecommers.dto.CartItemDto;
import com.ecommers.exceptions.CustomException;
import com.ecommers.exceptions.ProductNotExistException;
import com.ecommers.models.Cart;
import com.ecommers.models.Product;
import com.ecommers.models.User;
import com.ecommers.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    ProductService productService;

    @Autowired
    CartRepository cartRepository;
    public void addToCart(CartDto cartDto, User user) throws ProductNotExistException {
        Product product = productService.findById(cartDto.getProductId());

        Cart cart = new Cart();
        cart.setUser(user);
        cart.setProduct(product);
        cart.setQuantity(cartDto.getQuantity());
        cart.setCreatedDate(new Date());
        cartRepository.save(cart);
    }

    public CartGetDto listCartItems(User user) {
        List<Cart> cartList = cartRepository.findAllByUserOrderByCreatedDateDesc(user);
        
        List<CartItemDto> cartItems = new ArrayList<>();
        double totalCost = 0;
        for (Cart cart: cartList) {
            CartItemDto cartItemDto = new CartItemDto();
            cartItemDto.setId(cart.getId());
            cartItemDto.setQuantity(cart.getQuantity());
            cartItemDto.setProduct(cart.getProduct());
            totalCost += cartItemDto.getQuantity() * cart.getProduct().getPrice();
            cartItems.add(cartItemDto);
        }
        CartGetDto cartGetDto = new CartGetDto();
        cartGetDto.setTotalCoast(totalCost);
        cartGetDto.setCartItems(cartItems);
        return cartGetDto;
    }

    public void deleteCartItem(Integer cartItemId, User user) {
        Optional<Cart> cart = cartRepository.findById(cartItemId);
        if (cart.isEmpty())
            throw new CustomException("cart item id is invatid"+cartItemId);

        Cart cart1 = cart.get();
        if (cart1.getUser() != user)
            throw new CustomException("cart item does not belong to user"+ cartItemId);

        cartRepository.delete(cart1);
    }
}
