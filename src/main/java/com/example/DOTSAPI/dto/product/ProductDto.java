package com.example.DOTSAPI.dto.product;

import lombok.Getter;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.*;
import java.util.Date;
import java.util.Set;

@Getter
public class ProductDto {
    @NotBlank(message = "NAME_NOT_VALID")
    private String productName;

    @NotBlank(message = "DESCRIPTION_NOT_VALID")
    private String description;

    @NotBlank(message = "CATEGORY_NOT_VALID")
    private String category;

    @NotBlank(message = "IMAGEURL_NOT_VALID")
    @Size(max = 700, message = "IMAGEURL_TOO_LONG")
    private String imageUrl;

    @NotNull(message = "PRICE_MISSING")
    @Min(value = 0, message = "PRICE_NOT_VALID")
    private double unitPrice;

    @NotNull(message = "STOCK_MISSING")
    @Min(value = 0,message = "STOCK_NOT_VALID")
    private Long stock;

    @NotBlank(message = "BRAND_NOT_VALID")
    private String brand;

    @NotNull(message = "OVERALL_RATING_NOT_VALID")
    private float overallRating;

    @NotNull(message = "TOTAL_RATING_NOT_VALID")
    @Min(value = 0, message = "TOTAL_RATING_NOT_VALID")
    private Integer totalRating;

    @NotEmpty(message = "SIZE_NOT_VALID")
    private Set<Integer> size;
    @NotEmpty(message = "COLOR_NOT_VALID")
    private Set<String> color;
}
