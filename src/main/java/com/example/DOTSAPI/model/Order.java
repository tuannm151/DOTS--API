package com.example.DOTSAPI.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @NotNull
    @Temporal(TemporalType.DATE)
    private Date createdDate;

    @Min(value = 0, message = "Total can't be negative")
    @NotNull(message = "Total price can't be null")
    private double totalPrice;

    @NotNull
    @Min(value = 1, message = "Item numbers can't lower than 1")
    private double itemsNumber;

    @NotBlank(message = "Status is missing")
    @Column(nullable = false)
    private String status;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<OrderItem> orderItems;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private AppUser user;

    public double caculateTotalPrice() {
        double sum = 0.0;
        for(OrderItem orderItem : orderItems) {
            sum+= orderItem.getUnitPrice() * orderItem.getQuantity();
        }
        return sum;
    }
    public int caculateItemsNumber() {
        return orderItems.size();
    }

}
