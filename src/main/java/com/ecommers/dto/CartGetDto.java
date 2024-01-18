package com.ecommers.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartGetDto {
    List<CartItemDto> cartItems;
    public Double totalCoast;
}
