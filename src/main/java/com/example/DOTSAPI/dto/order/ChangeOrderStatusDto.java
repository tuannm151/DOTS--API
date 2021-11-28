package com.example.DOTSAPI.dto.order;

import com.example.DOTSAPI.enums.OrderStatus;
import lombok.Getter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

@Getter
public class ChangeOrderStatusDto {
    @NotNull(message = "Order id is missing")
    private Long orderId;
    @NotNull(message = "Status is missing")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
}
