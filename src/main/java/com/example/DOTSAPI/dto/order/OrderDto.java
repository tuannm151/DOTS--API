package com.example.DOTSAPI.dto.order;

import com.example.DOTSAPI.model.Order;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class OrderDto {
    private Long id;

    public OrderDto(Order order) {
        this.id = order.getId();
    }
}
