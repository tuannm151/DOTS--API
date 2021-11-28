package com.example.DOTSAPI.services.order;

import com.example.DOTSAPI.dto.cart.CartItemDto;
import com.example.DOTSAPI.dto.cart.CartSessionDto;
import com.example.DOTSAPI.dto.order.OrderDto;
import com.example.DOTSAPI.dto.order.OrderItemDto;
import com.example.DOTSAPI.dto.order.RequestOrderDto;
import com.example.DOTSAPI.enums.OrderStatus;
import com.example.DOTSAPI.enums.PaymentStatus;
import com.example.DOTSAPI.exception.NotFoundException;
import com.example.DOTSAPI.model.*;
import com.example.DOTSAPI.repository.CustomerRepo;
import com.example.DOTSAPI.repository.OrderItemRepo;
import com.example.DOTSAPI.repository.OrderRepo;
import com.example.DOTSAPI.services.cart.CartSessionServices;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.naming.OperationNotSupportedException;
import javax.transaction.Transactional;
import java.util.*;


@Service
@RequiredArgsConstructor
@Transactional
public class OrderServicesImpl implements OrderServices {
    private final CartSessionServices cartSessionServices;
    private final CustomerRepo customerRepository;
    private final OrderRepo orderRepo;
    private final OrderItemRepo orderItemRepo;
    private final ModelMapper mapper;

    @Override
    public void placeOrder(RequestOrderDto requestOrderDto, User user) throws OperationNotSupportedException {
        Customer customer = customerRepository.findCustomerByIdAndUser(requestOrderDto.getCustomerId(), user);
        if(customer == null) {
            throw new NotFoundException("CUSTOMER_NOT_FOUND");
        }
        CartSessionDto cartSessionDto = cartSessionServices.listCartItems(user);
        if(cartSessionDto.getCartItemsDto().isEmpty()) {
            throw new OperationNotSupportedException("CART_NO_ITEM");
        }
        Order newOrder = new Order();
        newOrder.setCustomer(customer);
        newOrder.setUser(user);
        newOrder.setOrderStatus(OrderStatus.PENDING);
        newOrder.setPaymentType(requestOrderDto.getPaymentType());
        newOrder.setPaymentStatus(PaymentStatus.WAITING);
        newOrder.setTotalPrice(cartSessionDto.getTotalPrice());
        newOrder.setItemsNumber(cartSessionDto.getItemsNumber());
        user.getOrders().add(newOrder);
        for(CartItemDto cartItemDto : cartSessionDto.getCartItemsDto()) {
            OrderItem orderItem = new OrderItem(cartItemDto.getQuantity(), cartItemDto.getSize(), cartItemDto.getColor(),
                    cartItemDto.getProduct());
            Product product = orderItem.getProduct();
            long remainingStock =product.getStock() - orderItem.getQuantity();
            if(remainingStock < 0) {
                throw new OperationNotSupportedException("QUANTITY_EXCEEDED_STOCK");
            }
            orderItem.setOrder(newOrder);
            orderItemRepo.save(orderItem);
        }

        cartSessionServices.deleteUserAllCartItems(user);
    }

    @Override
    public List<OrderDto> getUserOrders(User user) {
        List<OrderDto> orderDtos = new ArrayList<>();
        List<Order> orders = orderRepo.findAllByUserOrderByCreatedAtDesc(user);
        if(orders.isEmpty()) {
            throw new NotFoundException("ORDER_EMPTY");
        }
        for(Order order : orders) {
            OrderDto orderDto = convertOrderToDto(order);
            orderDtos.add(orderDto);
        }
        return orderDtos;
    }

    @Override
    public OrderDto getOrder(Long orderId) {
        Optional<Order> order = orderRepo.findById(orderId);
        if(order.isEmpty()) {
            throw new NotFoundException("ORDER_NOT_FOUND");
        }
        return convertOrderToDto(order.get());
    }

    private OrderDto convertOrderToDto(Order order) {
        OrderDto orderDto = new OrderDto();
        mapper.map(order, orderDto);
        for(OrderItem orderItem : order.getOrderItems()) {
            // Convert orderItem to orderItemDto
            OrderItemDto orderItemDto = new OrderItemDto(orderItem.getProduct());
            orderItemDto.setQuantity(orderItem.getQuantity());
            orderItemDto.setSize(orderItem.getSize());
            orderItemDto.setColor(orderItem.getColor());
            orderItemDto.setUnitPrice(orderItem.getUnitPrice());
            orderDto.getOrderItemDtos().add(orderItemDto);
       }
        orderDto.setOrderId(order.getId());
        return orderDto;
    }

    @Override
    public void cancelOrder(Long orderId, User user) throws OperationNotSupportedException {
        Order order = orderRepo.findByIdAndUser(orderId, user);
        if(order == null) {
            throw new NotFoundException("ORDER_NOT_FOUND");
        }
        OrderStatus currentStatus = order.getOrderStatus();
        if(currentStatus == OrderStatus.PENDING || currentStatus == OrderStatus.CONFIRMED || currentStatus == OrderStatus.READY) {
            order.setOrderStatus(OrderStatus.CANCELED);
            if (order.getPaymentStatus() == PaymentStatus.PAID) {
                order.setPaymentStatus(PaymentStatus.RETURNING);
            }
            order.setModifiedAt(new Date());
            orderRepo.save(order);
        }
        else
            throw new OperationNotSupportedException(String.format("ORDER_IS_%s", currentStatus.name().toUpperCase()));
    }

    @Override
    public void changeOrderStatus(Long orderId, OrderStatus status) {
        Optional<Order> orderOp = orderRepo.findById(orderId);
        if(orderOp.isEmpty()) {
            throw new NotFoundException("ORDER_NOT_FOUND");
        }
        Order order = orderOp.get();
        order.setOrderStatus(status);
        order.setModifiedAt(new Date());
        orderRepo.save(order);
    }

    @Override
    public void changePaymentStatus(Long orderId, PaymentStatus status) {
        Optional<Order> orderOp = orderRepo.findById(orderId);
        if(orderOp.isEmpty()) {
            throw new NotFoundException("ORDER_NOT_FOUND");
        }
        Order order = orderOp.get();
        order.setPaymentStatus(status);
        order.setModifiedAt(new Date());
        orderRepo.save(order);
    }
}
