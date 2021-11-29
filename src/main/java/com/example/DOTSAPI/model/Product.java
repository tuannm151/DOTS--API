package com.example.DOTSAPI.model;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @NotBlank(message = "Name is missing")
    private String name;

    @NotBlank(message = "Description is missing")
    @Column(length = 1000)
    private String description;

    @NotBlank(message = "Image URL is missing")
    private String imageUrl;

    @NotNull(message = "Price is missing")
    private Double price;

    @NotNull(message = "Stock number is missing")
    @Min(value = 0, message = "Stock number can't not be negative")
    private Long stock;

    @ElementCollection
    @NotEmpty(message = "Color is missing")
    private Set<String> color;

    @ElementCollection
    @NotEmpty(message = "Size is missing")
    private Set<Integer> size;

    @NotNull(message = "Category is missing")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    private float overallRating;
    private Integer totalRating;

    public Product(String name, String description, String imageUrl, Double price, Long stock, List<String> color,
                   List<Integer> size) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.price = price;
        this.stock = stock;
        this.color = new HashSet<>(color);
        this.size = new HashSet<>(size);
        overallRating = 0;
        totalRating = 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id.equals(product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
