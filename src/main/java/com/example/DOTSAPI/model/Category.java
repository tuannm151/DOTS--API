package com.example.DOTSAPI.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 700)
    @Size(max = 700, message="Image url must be smaller than 60 characters long")
    @NotBlank(message = "IMAGEURL_MISSING")
    private String imageUrl;

    @NotBlank(message = "CATEGORY_MISSING")
    @Size(max = 60, message="Category must be smaller than 60 characters long")
    @Column(unique = true, length = 60)
    private String name;

    public Category(String name) {
        this.name = name;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Product> products = new HashSet<>();

}
