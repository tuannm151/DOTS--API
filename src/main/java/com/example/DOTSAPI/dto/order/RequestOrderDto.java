package com.example.DOTSAPI.dto.order;

import com.example.DOTSAPI.enums.PaymentStatus;
import com.example.DOTSAPI.enums.PaymentType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class RequestOrderDto {
    @NotNull
    private Long customerId;
    @NotNull
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;
}
