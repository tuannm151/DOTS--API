package com.example.DOTSAPI.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String imageUrl;
    private String color;
    private Long price;
    private Long size;
    private Long stock;
    @ManyToOne(fetch = FetchType.EAGER)
    private Category category;
}
