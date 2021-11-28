package com.example.DOTSAPI.dto.order;

import com.example.DOTSAPI.enums.OrderStatus;
import com.example.DOTSAPI.enums.PaymentStatus;
import lombok.Getter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

@Getter
public class ChangePaymentStatusDto {
    @NotNull(message = "Order id is missing")
    private Long orderId;
    @NotNull(message = "Status is missing")
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
}
