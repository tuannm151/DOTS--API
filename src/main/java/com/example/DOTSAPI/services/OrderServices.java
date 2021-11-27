package com.example.DOTSAPI.services.order;
import com.example.DOTSAPI.model.Category;
import com.example.DOTSAPI.model.OrderItem;
import com.example.DOTSAPI.model.Order;
import com.example.DOTSAPI.model.Product;
import com.example.DOTSAPI.model.Customer;
import java.util.*;
public interface OrderServices {
    Order saveOrder(Order order);
    Order getOrderId(long id);
    Order updateOrderById(Long id, Order order);
    List<Order> getAllOrders();
    List<Order> searchByCustomer(Customer customer);
    List<Order> searchByDate(Date date);
    void deleteOrder(Long id);
    OrderItem saveOrderItem(Category category);
    List<OrderItem> getAllOrderItems();
}
