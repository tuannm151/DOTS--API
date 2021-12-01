package com.example.DOTSAPI.services.order;

import com.example.DOTSAPI.dto.order.OrderDto;
import com.example.DOTSAPI.dto.order.RequestOrderDto;
import com.example.DOTSAPI.enums.OrderStatus;
import com.example.DOTSAPI.enums.PaymentStatus;
import com.example.DOTSAPI.model.Order;
import com.example.DOTSAPI.model.User;

import javax.naming.OperationNotSupportedException;
import java.util.List;

public interface OrderServices {
    void placeOrder(RequestOrderDto requestOrderDto, User user) throws OperationNotSupportedException;
    void cancelOrder(Long orderId, User user) throws OperationNotSupportedException;
    List<OrderDto> getUserOrders(User user);
    OrderDto getOrder(Long orderId);
    void changeOrderStatus(Long orderId, OrderStatus status);
    void changePaymentStatus(Long orderId, PaymentStatus status);
    List<OrderDto> findAllOrders();
}
