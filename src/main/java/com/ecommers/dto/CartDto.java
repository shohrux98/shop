package com.ecommers.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {

    private Integer id;
    private @NotNull Integer productId;
    private @NotNull Integer quantity;
}
