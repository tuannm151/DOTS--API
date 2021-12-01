package com.example.DOTSAPI.controller;

import com.example.DOTSAPI.dto.order.ChangePaymentStatusDto;
import com.example.DOTSAPI.dto.order.OrderDto;
import com.example.DOTSAPI.dto.order.RequestOrderDto;
import com.example.DOTSAPI.dto.order.ChangeOrderStatusDto;
import com.example.DOTSAPI.model.User;
import com.example.DOTSAPI.services.appUser.AppUserServices;
import com.example.DOTSAPI.services.order.OrderServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.naming.OperationNotSupportedException;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {
    private final OrderServices orderServices;
    private final AppUserServices appUserServices;
    @PostMapping("/place")
    public ResponseEntity<?> placeOrder(@RequestBody @Valid RequestOrderDto requestOrderDto,
                                        Authentication authentication) throws OperationNotSupportedException {
        User user = appUserServices.findUserByUserName(authentication.getName());
        orderServices.placeOrder(requestOrderDto, user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @PostMapping("/cancel/{id}")
    public ResponseEntity<?> cancelOrder(@PathVariable Long id, Authentication authentication) throws OperationNotSupportedException {
        User user = appUserServices.findUserByUserName(authentication.getName());
        orderServices.cancelOrder(id,user);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/list")
    public ResponseEntity<List<OrderDto>> getUserOrders(Authentication authentication) {
        User user = appUserServices.findUserByUserName(authentication.getName());
        return ResponseEntity.ok().body(orderServices.getUserOrders(user));
    }

    @GetMapping("/admin/list")
    public ResponseEntity<List<OrderDto>> findAllOrders() {
        return ResponseEntity.ok().body(orderServices.findAllOrders());
    }

    @GetMapping("/admin/{id}")
    public ResponseEntity<OrderDto> getOrder(@PathVariable Long id) {
        return ResponseEntity.ok().body(orderServices.getOrder(id));
    }

    @PostMapping("/admin/status")
    public ResponseEntity<OrderDto> changeOrderStatus(@RequestBody @Valid ChangeOrderStatusDto changeOrderStatusDto) {
        orderServices.changeOrderStatus(changeOrderStatusDto.getOrderId(), changeOrderStatusDto.getStatus());
        return ResponseEntity.ok().build();
    }
    @PostMapping("/admin/payment/status")
    public ResponseEntity<OrderDto> changePaymentStatus(@RequestBody @Valid ChangePaymentStatusDto changePaymentStatusDto) {
        orderServices.changePaymentStatus(changePaymentStatusDto.getOrderId(), changePaymentStatusDto.getStatus());
        return ResponseEntity.ok().build();
    }

}
