package com.example.DOTSAPI.dto.cart;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class UpdateCartItemDto {
    private Long cartItemId;

    @NotNull(message = "Quantity is missing")
    @Min(value = 1, message = "Quantity at least 1")
    private Long quantity;

    @NotNull(message = "Size is missing")
    private Integer size;
    @NotBlank(message = "Color is missing")
    private String color;


    public UpdateCartItemDto(Long cartItemId, Long quantity, Integer size,String color) {
        this.cartItemId = cartItemId;
        this.size = size;
        this.quantity = quantity;
        this.color = color;
    }
}
