package com.example.DOTSAPI.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedAt;

    @NotNull(message = "Quantity is missing")
    @Min(value = 1, message = "Quantity at least 1")
    @Column(nullable = false)
    private Long quantity;

    @NotNull(message = "Size is missing")
    @Column(nullable = false)
    private Integer size;

    @NotBlank(message = "Color is missing")
    @Column(nullable = false)
    private String color;

    @OneToOne(targetEntity = Product.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "product_id")
    private Product product;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_session_id")
    private CartSession cartSession;

    public CartItem(Long quantity, Integer size,String color, Product product) {
        this.quantity = quantity;
        this.color = color;
        this.product = product;
        this.size = size;
        this.createdAt = new Date();
        this.modifiedAt = new Date();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItem cartItem = (CartItem) o;
        return id.equals(cartItem.id) && product.equals(cartItem.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, product);
    }
}
