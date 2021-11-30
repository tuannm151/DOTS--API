package com.example.DOTSAPI.dto.product;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
public class RateProductDto {
    @NotNull(message = "PRODUCT_ID_MISSING")
    private Long productId;

    @NotNull(message = "RATING_NOT_VALID")
    @Min(value = 0, message = "RATING_NOT_VALID")
    @Max(value = 5, message = "RATING_NOT_VALID")
    private Integer rating;
}
