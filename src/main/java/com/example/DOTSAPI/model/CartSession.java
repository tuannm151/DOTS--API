package com.example.DOTSAPI.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class CartSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Temporal(TemporalType.DATE)
    private Date createdAt;
    @Temporal(TemporalType.DATE)
    private Date modifiedAt;

    @Transient
    private double totalPrice;
    @Transient
    private int itemsNumber;

    @JsonIgnore
    @OneToOne(targetEntity = AppUser.class, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "user_id")
    private AppUser user;

    @OneToMany(mappedBy = "cartSession", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<CartItem> cartItems;

    public double getTotalPrice() {
        double sum = 0.0;
        for(CartItem item : cartItems) {
            sum += item.getProduct().getPrice() * item.getQuantity();
        }
        return sum;
    }

    public int getItemsNumber() {
        return this.cartItems.size();
    }
}
