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

    @NotNull(message = "QUANTITY_MISSING")
    @Min(value = 1, message = "Quantity at least 1")
    private Long quantity;

    public UpdateCartItemDto(Long cartItemId, Long quantity) {
        this.cartItemId = cartItemId;
        this.quantity = quantity;
    }
}
