package com.example.DOTSAPI.dto.product;

import com.example.DOTSAPI.model.Product;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseProductDto {
    private Long productId;
    private String productName;
    private String description;
    private String category;
    private String imageUrl;
    private double unitPrice;
    private Long stock;
    private String brand;
    private float overallRating;
    private Integer totalRating;
    private Set<Integer> size;
    private Set<String> color;

    @Temporal(TemporalType.DATE)
    private Date createdAt;

    @Temporal(TemporalType.DATE)
    private Date modifiedAt;

    public ResponseProductDto(Product product) {
        this.productId = product.getId();
        this.productName = product.getName();
        this.category = product.getCategory().getName();
        this.imageUrl = product.getImageUrl();
        this.stock = product.getStock();
        this.size = product.getSize();
        this.unitPrice = product.getPrice();
        this.color = product.getColor();
        this.brand = product.getBrand().getName();
        this.overallRating = product.getOverallRating();
        this.totalRating = product.getTotalRating();
        this.description = product.getDescription();
        this.setCreatedAt(product.getCreatedAt());
        this.setModifiedAt(product.getModifiedAt());
    }
}
