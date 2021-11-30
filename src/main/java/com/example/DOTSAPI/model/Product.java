package com.example.DOTSAPI.model;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
@Getter
@Setter
@ToString
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Temporal(TemporalType.DATE)
    private Date createdAt;

    @Temporal(TemporalType.DATE)
    private Date modifiedAt;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false,length = 1000)
    private String description;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Long stock;

    @ElementCollection
    private Set<String> color;

    @ElementCollection
    private Set<Integer> size;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    private float overallRating;
    private Integer totalRating;

    public Product(String name, String description, String imageUrl, Double price, Long stock, List<String> color,
                   List<Integer> size, Brand brand) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.price = price;
        this.stock = stock;
        this.color = new HashSet<>(color);
        this.size = new HashSet<>(size);
        this.brand = brand;
        overallRating = 0;
        totalRating = 0;
        this.createdAt = new Date();
        this.modifiedAt = new Date();
    }

    public Product() {
        this.createdAt = new Date();
        this.modifiedAt = new Date();
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
