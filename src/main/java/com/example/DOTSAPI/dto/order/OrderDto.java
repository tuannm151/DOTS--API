package com.example.DOTSAPI.dto.order;

import com.example.DOTSAPI.enums.OrderStatus;
import com.example.DOTSAPI.enums.PaymentStatus;
import com.example.DOTSAPI.enums.PaymentType;
import com.example.DOTSAPI.model.Customer;
import com.example.DOTSAPI.model.Order;
import com.example.DOTSAPI.model.OrderItem;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderDto {
    private Long orderId;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedAt;

    private double totalPrice;

    private int itemsNumber;

    private OrderStatus orderStatus;

    private PaymentStatus paymentStatus;

    private PaymentType paymentType;

    private Customer customer;

    List<OrderItemDto> orderItemDtos = new ArrayList<>();
}
