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
public class AddToCardDto {
    @NotNull
    private Long productId;

    @NotNull(message = "Quantity is missing")
    @Min(value = 1, message = "Quantity at least 1")
    private Long quantity;

    @NotNull(message = "Size is missing")
    private Integer size;

    @NotBlank(message = "Color is missing")
    private String color;
    public AddToCardDto(Long productId, Long quantity, Integer size,String color) {
        this.productId = productId;
        this.quantity = quantity;
        this.color = color;
        this.size = size;
    }
}
